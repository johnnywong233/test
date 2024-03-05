package utils;

import lombok.NoArgsConstructor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/12/9
 * Time: 21:18
 * Class copied from {@link org.jasypt.commons.CommonUtils}
 */
@NoArgsConstructor
public class CommonUtil {
    public static final String STRING_OUTPUT_TYPE_BASE64 = "base64";
    public static final String STRING_OUTPUT_TYPE_HEXADECIMAL = "hexadecimal";
    private static final List<String> STRING_OUTPUT_TYPE_HEXADECIMAL_NAMES = Arrays.asList("HEXADECIMAL", "HEXA", "0X", "HEX", "HEXADEC");
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static Boolean getStandardBooleanValue(String valueStr) {
        if (valueStr == null) {
            return null;
        } else {
            String upperValue = valueStr.toUpperCase();
            return !"TRUE".equals(upperValue) && !"ON".equals(upperValue) && !"YES".equals(upperValue) ? (!"FALSE".equals(upperValue) && !"OFF".equals(upperValue) && !"NO".equals(upperValue) ? null : Boolean.FALSE) : Boolean.TRUE;
        }
    }

    public static String getStandardStringOutputType(String valueStr) {
        return valueStr == null ? null : (STRING_OUTPUT_TYPE_HEXADECIMAL_NAMES.contains(valueStr.toUpperCase()) ? "hexadecimal" : "base64");
    }

    public static String toHexadecimal(byte[] message) {
        if (message == null) {
            return null;
        } else {
            StringBuilder buffer = new StringBuilder();

            for (byte b : message) {
                int curByte = b & 255;
                buffer.append(hexDigits[curByte >> 4]);
                buffer.append(hexDigits[curByte & 15]);
            }
            return buffer.toString();
        }
    }

    public static byte[] fromHexadecimal(String message) {
        if (message == null) {
            return null;
        } else if (message.length() % 2 != 0) {
            throw new EncryptionOperationNotPossibleException();
        } else {
            try {
                byte[] e = new byte[message.length() / 2];

                for (int i = 0; i < message.length(); i += 2) {
                    int first = Integer.parseInt("" + message.charAt(i), 16);
                    int second = Integer.parseInt("" + message.charAt(i + 1), 16);
                    e[i / 2] = (byte) (((first & 255) << 4) + (second & 255));
                }
                return e;
            } catch (Exception var5) {
                throw new EncryptionOperationNotPossibleException();
            }
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.length() != 0;
    }

    public static void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateNotEmpty(String string, String message) {
        if (isEmpty(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateIsTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static String[] split(String string) {
        return split(string, null);
    }

    public static String[] split(String string, String separators) {
        if (string == null) {
            return null;
        } else {
            int length = string.length();
            if (length == 0) {
                return new String[0];
            } else {
                ArrayList<String> results = new ArrayList<>();
                int i = 0;
                int start = 0;
                boolean tokenInProgress = false;
                if (separators == null) {
                    while (i < length) {
                        if (Character.isWhitespace(string.charAt(i))) {
                            if (tokenInProgress) {
                                results.add(string.substring(start, i));
                                tokenInProgress = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            tokenInProgress = true;
                            ++i;
                        }
                    }
                } else if (separators.length() == 1) {
                    char separator = separators.charAt(0);

                    while (i < length) {
                        if (string.charAt(i) == separator) {
                            if (tokenInProgress) {
                                results.add(string.substring(start, i));
                                tokenInProgress = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            tokenInProgress = true;
                            ++i;
                        }
                    }
                } else {
                    while (i < length) {
                        if (separators.indexOf(string.charAt(i)) >= 0) {
                            if (tokenInProgress) {
                                results.add(string.substring(start, i));
                                tokenInProgress = false;
                            }

                            ++i;
                            start = i;
                        } else {
                            tokenInProgress = true;
                            ++i;
                        }
                    }
                }

                if (tokenInProgress) {
                    results.add(string.substring(start, i));
                }

                return results.toArray(new String[0]);
            }
        }
    }

    public static String substringBefore(String string, String separator) {
        if (!isEmpty(string) && separator != null) {
            if (separator.length() == 0) {
                return "";
            } else {
                int pos = string.indexOf(separator);
                return pos == -1 ? string : string.substring(0, pos);
            }
        } else {
            return string;
        }
    }

    public static String substringAfter(String string, String separator) {
        if (isEmpty(string)) {
            return string;
        } else if (separator == null) {
            return "";
        } else {
            int pos = string.indexOf(separator);
            return pos == -1 ? "" : string.substring(pos + separator.length());
        }
    }

    public static int nextRandomInt() {
        return (int) (Math.random() * 2.147483647E9D);
    }

    public static byte[] appendArrays(byte[] firstArray, byte[] secondArray) {
        validateNotNull(firstArray, "Appended array cannot be null");
        validateNotNull(secondArray, "Appended array cannot be null");
        byte[] result = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, result, 0, firstArray.length);
        System.arraycopy(secondArray, 0, result, firstArray.length, secondArray.length);
        return result;
    }

    public static void closeStream(FileOutputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeStream(FileInputStream in) {
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
