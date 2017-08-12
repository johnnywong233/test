package com.johnny.common;

import javax.swing.JTextArea;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Console {
    public static JTextArea consoleArea;
    public static Long count = Long.valueOf(0L);
    private static Integer MAX_LINE = Integer.valueOf(1000);
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private static boolean isDebug = true;

    public static void print(String text) {
        if (isDebug) {
            System.out.println(text);
        }
        if (consoleArea != null)
            synchronized (consoleArea) {
                int lineCount = consoleArea.getLineCount();
                if (lineCount >= MAX_LINE.intValue()) {
                    int end = 0;
                    try {
                        end = consoleArea.getLineEndOffset(MAX_LINE.intValue());
                    } catch (Exception localException) {
                    }
                    consoleArea.replaceRange("", 0, end);
                }
                consoleArea.append(sdf.format(new Date()) + " | " + text + "\r\n");
                consoleArea.setCaretPosition(consoleArea.getText().length());
                count = Long.valueOf(count.longValue() + 1L);
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