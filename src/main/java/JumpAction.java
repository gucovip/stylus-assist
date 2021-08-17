import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JumpAction extends AnAction {
    enum FILE_TYPE {
        VUE("vue"), PUG("pug"), STYL("styl");
        private String name;

        private FILE_TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

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

        if (FILE_TYPE.VUE.getName().equals(currentFileType)) {

            //匹配类型1
            String matchStr = "\\s{1}[\\.#]" + selectedText + "[\\r\\n,]";
            //匹配类型2
            String matchStr2 = Utils.formatClass(matchStr);
            //获取匹配结果
            List<Integer> x = findIndex(matchStr, allContent);

            if (x.size() <= 0) {
                //未匹配到类型1继续匹配类型2
                x = findIndex(matchStr2, allContent);
            }

            if (x.size() <= 0) {
                //未匹配到类型2识别为新增样式，生成格式化好的css类名并插入剪切板
                CopyToClip("." + Utils.formatClass(selectedText));
                return;
            }

            // 新的实现逻辑，支持 Navigate Back & Forward
            PsiFile targetPsiFile = currentPsiFile.getParent().findFile(currentFileName + ".vue");
            OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(
                    anActionEvent.getProject(),
                    targetPsiFile.getVirtualFile(),
                    x.get(0));
            Editor targetEdit = FileEditorManager.getInstance(anActionEvent.getProject())
                    .openTextEditor(openFileDescriptor, true);
            JumpToEditorAndSelectIt(x, targetEdit);

        } else if (FILE_TYPE.PUG.getName().equals(currentFileType)) {
            if (currentPsiFile.getParent() == null) return;
            //识别为pug文件
            //获取对应父亲文件夹的styl文件
            PsiFile targetPsiFile = currentPsiFile.getParent().findFile(currentFileName + ".styl");
            //没有找到对应的文件结束  TODO 加入新增styl文件
            if (targetPsiFile == null) return;

            //匹配类型1
            String matchStr = "\\." + selectedText + "[\\r\\n,]";
            //匹配类型2
            String matchStr2 = Utils.formatClass(matchStr);
            //获取目标匹配文本
            String targetText = PsiDocumentManager.getInstance(anActionEvent.getProject())
                    .getDocument(targetPsiFile).getText();

            List<Integer> x = findIndex(matchStr, targetText);
            if (x.size() <= 0) x = findIndex(matchStr2, targetText);

            if (x.size() <= 0) {
                CopyToClip("." + Utils.formatClass(selectedText));
                return;
            }
            //匹配到结果，打开文件并跳转位置
            OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(
                    anActionEvent.getProject(),
                    targetPsiFile.getVirtualFile(),
                    x.get(0));

            Editor targetEdit = FileEditorManager.getInstance(anActionEvent.getProject())
                    .openTextEditor(openFileDescriptor, true);
            //无需手动跳转，打开文件时传入了位置，直接跳转
//            LogicalPosition logicalPosition = targetEdit.offsetToLogicalPosition(x.get(0));
//            ScrollingModel scrollingModel = targetEdit.getScrollingModel();
//            scrollingModel.scrollTo(logicalPosition, ScrollType.MAKE_VISIBLE);
            //选中匹配到的结果，引起注意
            SelectionModel targetSelectionModel = targetEdit.getSelectionModel();
            targetSelectionModel.setSelection(x.get(0), x.get(1) - 1);
            CaretModel caretModel = targetEdit.getCaretModel();
            caretModel.moveToOffset(x.get(1) - 1);

        }
    }

    public static List<Integer> findIndex(String matchStr, String allContent) {
        Pattern pattern = Pattern.compile(matchStr);
        Matcher matcher = pattern.matcher(allContent);
        List<Integer> x = new ArrayList<>();

        if (matcher.find()) {
            x.add(matcher.start());
            x.add(matcher.end());
        }
        return x;
    }

    @Override
    public void update(final AnActionEvent anActionEvent) {
    }

    public static void CopyToClip(String str) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪切板
        StringSelection selection = new StringSelection(str);//构建String数据类型
        clipboard.setContents(selection, selection);//添加文本到系统剪切板
    }

    private static void JumpToEditorAndSelectIt(List<Integer> x, Editor editor) {
        // 获取位置对象
        LogicalPosition logicalPosition = editor.offsetToLogicalPosition(x.get(0));
        // 获取滚动器
        ScrollingModel scrollingModel = editor.getScrollingModel();
        // 滚动到指定位置
        scrollingModel.scrollTo(logicalPosition, ScrollType.MAKE_VISIBLE);
        // 获取选择器
        SelectionModel selectionModel = editor.getSelectionModel();
        // 选择指定内容
        selectionModel.setSelection(x.get(0), x.get(1) - 1);
        // 获取光标器
        CaretModel caretModel = editor.getCaretModel();
        // 光标位置移动到
        caretModel.moveToOffset(x.get(1) - 1, true);
    }
}
