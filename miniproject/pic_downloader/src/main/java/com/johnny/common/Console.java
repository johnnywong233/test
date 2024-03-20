package com.johnny.common;

import javax.swing.JTextArea;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Console {
    public static JTextArea consoleArea;
    public static Long count = 0L;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void print(String text) {
        System.out.println(text);
        if (consoleArea != null) {
            synchronized (consoleArea) {
                int lineCount = consoleArea.getLineCount();
                int MAX_LINE = 1000;
                if (lineCount >= MAX_LINE) {
                    int end = 0;
                    try {
                        end = consoleArea.getLineEndOffset(MAX_LINE);
                    } catch (Exception localException) {
                        localException.printStackTrace();
                    }
                    consoleArea.replaceRange("", 0, end);
                }
                consoleArea.append(sdf.format(new Date()) + " | " + text + "\r\n");
                consoleArea.setCaretPosition(consoleArea.getText().length());
                count = count + 1L;
            }
        }
    }

    public static void setArea(JTextArea consoleArea) {
        Console.consoleArea = consoleArea;
    }

    public static void print(Logger log, String msg) {
        log.info(msg);
        print(msg);
    }
}