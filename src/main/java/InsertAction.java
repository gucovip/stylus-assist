import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

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
            //获取位置
            final SelectionModel selectionModel = editor.getSelectionModel();
            final int start = selectionModel.getSelectionStart();
            final int end = selectionModel.getSelectionEnd();

            //获取空格数
            CaretModel caretModel = editor.getCaretModel();
            caretModel.moveToOffset(start);
            String space = "";
            for (int i = 0; i < caretModel.getLogicalPosition().column; i++) {
                space += " ";
            }
            if (text == null) {
                return;
            }
            //格式化代码
            final String resultText = formatCode(text, space);
            //插入内容
            WriteCommandAction.runWriteCommandAction(editor.getProject(),
                    () -> document.replaceString(start, end, resultText));
        }

    }

    private String formatCode(String codeText, String space) {
        //去除分号
        String text = codeText.replaceAll(";", "");
        //按行分割
        String[] stringEnters = text.split("\n");

        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < stringEnters.length; i++) {
            //去除两端空格
            String middleString = stringEnters[i].trim();
            //处理有冒号
            if (middleString.contains(":")) {
                String[] split = middleString.split(":");
                for (int j = 0; j < split.length; j++) {
                    split[j] = split[j].trim();
                }
                middleString = String.join(" ", split);
            }
            //最后一行不换行
            String enterSymbol = (i == stringEnters.length - 1 ? "" : "\n");
            if (i > 0) {
                resultList.add(space + middleString + enterSymbol);
                continue;
            }
            resultList.add(middleString + enterSymbol);
        }
        //全部拼接
        return String.join("", resultList);
    }
}
