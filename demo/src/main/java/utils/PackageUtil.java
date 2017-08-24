package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/8/20
 * Time: 23:42
 */
public class PackageUtil {
    /**
     * 返回包下所有的类
     *
     * @param packagePath 包名
     * @return List<String> 包下所有的类
     */
    public static List<String> getPackageClasses(String packagePath) {
        return getPackageClasses(packagePath, false);
    }

    /**
     * 返回包下所有的类
     *
     * @param packagePath   包名全路径
     * @param classWithPath 返回全路径开关 true 自动带上包名
     * @return List<String> 包下所有的类
     */
    public static List<String> getPackageClasses(String packagePath, boolean classWithPath) {
        List<String> classNames = getClassName(packagePath);
        List<String> result = new ArrayList<>(classNames.size());
        String path = classWithPath ? packagePath + "." : "";
        for (String className : classNames) {
            result.add(path + className.substring(className.lastIndexOf(".") + 1));
        }
        return result;
    }

    private static List<String> getClassName(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
        return getClassName(filePath, null);
    }

    private static List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("\\", ".");
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }
}
