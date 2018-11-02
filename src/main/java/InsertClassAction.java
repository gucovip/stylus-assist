import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.psi.PsiFile;


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
        String currentPsiFileName = currentPsiFile.getName();
        //获取文件名
        String currentFileName = currentPsiFileName.split("\\.")[0];
        //获取文件类型
        String currentFileType = currentPsiFileName.split("\\.")[1].toLowerCase();
        //获取当前选择的文本
        final SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();

        //获取当前全文
        String allContent = editor.getDocument().getText();

        if (selectedText == null) {
            selectionModel.selectWordAtCaret(true);
            selectedText = selectionModel.getSelectedText();
        }

        String insertClass = "." + JumpAction.HumpToMiddleline(selectedText);
        String insertString = "\n  " + insertClass + "\n    \n";
        Document document = editor.getDocument();
        int endTag = allContent.indexOf("</style>");
        if (endTag == -1) {
            endTag = allContent.length() + 21;
            WriteCommandAction.runWriteCommandAction(editor.getProject(), () -> document.insertString(allContent.length(), "<style lang=\"stylus\"></style>"));

        }
        int newEndTag = endTag;
        WriteCommandAction.runWriteCommandAction(editor.getProject(),
                () -> document.insertString(newEndTag, insertString));
        PsiFile targetPsiFile = currentPsiFile.getParent().findFile(currentFileName + ".vue");
        OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(
                anActionEvent.getProject(),
                targetPsiFile.getVirtualFile(),
                document.getLineNumber(endTag - 1));
        Editor targetEdit = FileEditorManager.getInstance(anActionEvent.getProject())
                .openTextEditor(openFileDescriptor, true);
        ScrollingModel scrollingModel = targetEdit.getScrollingModel();
        scrollingModel.scrollTo(targetEdit.offsetToLogicalPosition(endTag + insertString.length() - 1), ScrollType.MAKE_VISIBLE);
        CaretModel caretModel = targetEdit.getCaretModel();
        caretModel.moveToOffset(endTag + insertString.length() - 1);
    }
}
