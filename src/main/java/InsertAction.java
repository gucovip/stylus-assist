import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InsertAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // TODO: insert action logic here
        final Editor editor = anActionEvent.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable content = clipboard.getContents(null);//从系统剪切板中获取数据
        if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {//判断是否为文本类型
            String text = null;//从数据中获取文本值
            try {
                text = (String) content.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CaretModel caretModel = editor.getCaretModel();
            int offset = caretModel.getOffset();
            String space = StringUtils.leftPad("", caretModel.getLogicalPosition().column, " ");
            if (text == null) {
                return;
            }
            text = text.replaceAll(":", "");
            text = text.replaceAll(";", "");
            String[] strings = text.split("\n");

            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < strings.length; i++) {
                if (i > 0) {
                    resultList.add(space + strings[i].trim() + "\n");
                    continue;
                }
                resultList.add(strings[i].trim() + "\n");
            }
            //Making the replacement
            WriteCommandAction.runWriteCommandAction(
                    editor.getProject(),
                    () -> document.insertString(offset, StringUtils.join(resultList, "")));
        }

    }
}
