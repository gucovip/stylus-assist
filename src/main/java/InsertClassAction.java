import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.psi.PsiFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InsertClassAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        if (anActionEvent.getProject() == null) return;
        // TODO: insert action logic here
        //获取当前的编辑者

        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);

        //获取当前的文件
        PsiFile currentPsiFile = anActionEvent.getData(LangDataKeys.PSI_FILE);
        if (currentPsiFile == null) return;

        //分割文件名 获取文件名
        String currentFileName = currentPsiFile.getName().split("\\.")[0];

        //获取当前选择的文本
        final SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();

        //获取当前全文
        String allContent = editor.getDocument().getText();

        if (selectedText == null) {
            selectionModel.selectWordAtCaret(true);
            selectedText = selectionModel.getSelectedText();
        }

        String insertClass = "." + Utils.formatClass(selectedText);
        String preInsertString = "\n" + insertClass + "\n  \n";
        Document document = editor.getDocument();
        int endTag = allContent.indexOf("</style>");
        if (endTag == -1) {
            endTag = allContent.length() + 21;
            WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> document.insertString(allContent.length(), "<style lang=\"stylus\"></style>"));
        } else {
            Pattern pattern = Pattern.compile("<style[\\s\\S]*>([\\s\\S]*?)</style>");
            Matcher matcher = pattern.matcher(allContent);
            if (matcher.find() && matcher.group(1).trim().length() <= 0) {
                preInsertString = preInsertString.substring(1);
            }
        }
        int newEndTag = endTag;
        String insertString = preInsertString;
        WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> document.insertString(newEndTag, insertString));
        assert currentPsiFile.getParent() != null;
        PsiFile targetPsiFile = currentPsiFile.getParent().findFile(currentFileName + ".vue");
        assert targetPsiFile != null;
        OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(
                anActionEvent.getProject(),
                targetPsiFile.getVirtualFile(),
                document.getLineNumber(endTag - 1));
        Editor targetEdit = FileEditorManager.getInstance(anActionEvent.getProject())
                .openTextEditor(openFileDescriptor, true);
        assert targetEdit != null;
        ScrollingModel scrollingModel = targetEdit.getScrollingModel();
        scrollingModel.scrollTo(targetEdit.offsetToLogicalPosition(endTag + insertString.length() - 1), ScrollType.MAKE_VISIBLE);
        CaretModel caretModel = targetEdit.getCaretModel();
        caretModel.moveToOffset(endTag + insertString.length() - 1);
    }
}
