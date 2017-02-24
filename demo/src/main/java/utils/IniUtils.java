package utils;

import org.ini4j.Ini;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility for ini files
 */
public class IniUtils {

    /**
     * Loads the entire ini file
     *
     * @param fileName The file name
     * @return The ini properties
     * @throws IOException IO exception
     */
    public static Map<String, String> load(String fileName)
            throws IOException {
        Map<String, String> result = new HashMap<>();
        Ini ini = loadIniFile(fileName);
        ini.values().forEach(result::putAll);
        return result;
    }

    /**
     * Stores the given section
     *
     * @param properties  The ini properties
     * @param fileName    The file name
     * @param sectionName The section name
     * @throws IOException IO exception
     */
    public static void store(Map<String, String> properties, String fileName, String sectionName)
            throws IOException {
        Ini iniFile = loadIniFile(fileName);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            iniFile.put(sectionName, entry.getKey(), entry.getValue());
        }
        PrintWriter writer = new PrintWriter(fileName);
        iniFile.store(writer);
        writer.flush();
        writer.close();
    }

    /**
     * Loads an ini file. If the file does not exist, returns an empty ini file
     *
     * @param fileName The file name
     * @return The ini file
     * @throws IOException IO exception
     */
    private static Ini loadIniFile(String fileName) throws IOException {
        Ini iniFile = new Wini();
        File file = new File(fileName);
        if (file.exists()) {
            iniFile.load(file);
        }
        return iniFile;
    }

    /**
     * Returns the value of the key with the given name
     *
     * @param fileName The ini file name
     * @param section  The section name
     * @param key      The key name
     * @return The value
     * @throws IOException IO exception
     */
    public static String getKey(String fileName, String section, String key) throws IOException {
        Ini iniFile = new Wini();
        iniFile.load(new FileReader(fileName));
        return iniFile.get(section).get(key);
    }

    /**
     * Sets the value of the key with the given name to the given value
     *
     * @param fileName The ini file name
     * @param section  The section name
     * @param key      The key name
     * @param value    The value
     * @throws IOException IO exception
     */
    public static void setKey(String fileName, String section, String key, String value) throws IOException {
        Ini iniFile = new Wini();
        iniFile.load(new FileReader(fileName));
        iniFile.get(section).put(key, value);
        PrintWriter writer = new PrintWriter(fileName);
        iniFile.store(writer);
        writer.flush();
        writer.close();
    }

    /**
     * Returns the value of the key with the given name
     *
     * @param fileName The ini file name
     * @param section  The section name
     * @param key      The key name
     * @return The value
     * @throws IOException IO exception
     */
    public static String getQuotedKey(String fileName, String section, String key) throws IOException {
        String value = getKey(fileName, section, key);
        if (value != null && value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
    }

    /**
     * Sets the value of the key with the given name to the given value
     *
     * @param fileName The ini file name
     * @param section  The section name
     * @param key      The key name
     * @param value    The value
     * @throws IOException IO exception
     */
    public static void setQuotedKey(String fileName, String section, String key, String value) throws IOException {
        setKey(fileName, section, key, "\"" + value + "\"");
    }
}
