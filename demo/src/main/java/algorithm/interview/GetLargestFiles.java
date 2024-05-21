package algorithm.interview;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Johnny
 * Date: 2018/5/23
 * Time: 21:25
 * 需求：给定一个目录，求这个目录下面文件大小最大的n个文件的全路径名
 */
public class GetLargestFiles {
    public static void main(String[] args) throws IOException {
        String dir = "F:\\死亡笔记";
//        System.out.println(getAllFile(dir));
        System.out.println(getNameAndSize(dir));
        System.out.println(getLargestFilename(dir, 10));
    }

    public static List<String> getLargestFilename(String dir, int n) throws IOException {
        List<NameAndSize> nameAndSizeList = getNameAndSize(dir);
        return nameAndSizeList.stream().sorted((o1, o2) -> (int) (o1.getFileSize() - o2.getFileSize())).limit(n).map(NameAndSize::getFullName).collect(Collectors.toList());
    }

    public static List<String> getAllFile(String pathName) throws IOException {
        return getAllFile(pathName, null);
    }

    private static List<String> getAllFile(String pathName, Integer depth) throws IOException {
        //获取pathName的File对象
        File dirFile = new File(pathName);
        List<String> allFileName = Lists.newArrayList();
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("do not exit");
            return null;
        }
        //判断如果不是一个目录，就判断是不是一个文件，是文件则输出文件路径
        BasicFileAttributes attributes = Files.readAttributes(dirFile.toPath(), BasicFileAttributes.class);
        if (!attributes.isDirectory()) {
            if (attributes.isRegularFile()) {
                allFileName.add(dirFile.getCanonicalPath());
            }
            return allFileName;
        }

        if (depth == null) {
            depth = 0;
        }
        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        int currentDepth = depth + 1;
        for (String string : fileList) {
            //遍历文件目录
            //File("documentName","fileName")是File的另一个构造器
            File file = new File(dirFile.getPath(), string);
            String name = file.getCanonicalPath();
            //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
            if (file.isDirectory()) {
                allFileName.addAll(Objects.requireNonNull(getAllFile(file.getCanonicalPath(), currentDepth)));
            } else {
                allFileName.add(name);
            }
        }
        return allFileName;
    }

    public static List<NameAndSize> getNameAndSize(String pathName) throws IOException {
        return getNameAndSize(pathName, null);
    }

    private static List<NameAndSize> getNameAndSize(String pathName, Integer depth) throws IOException {
        //获取pathName的File对象
        File dirFile = new File(pathName);
        List<NameAndSize> allFileName = Lists.newArrayList();
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("do not exit");
            return null;
        }
        //判断如果不是一个目录，就判断是不是一个文件，是文件则输出文件路径
        BasicFileAttributes attributes = Files.readAttributes(dirFile.toPath(), BasicFileAttributes.class);
        if (!attributes.isDirectory()) {
            if (attributes.isRegularFile()) {
                NameAndSize nameAndSize = NameAndSize.builder().
                        fileSize(dirFile.length()).fullName(dirFile.getCanonicalPath()).build();
                allFileName.add(nameAndSize);
            }
            return allFileName;
        }

        if (depth == null) {
            depth = 0;
        }
        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        int currentDepth = depth + 1;
        for (String string : fileList) {
            //遍历文件目录
            //File("documentName","fileName")是File的另一个构造器
            File file = new File(dirFile.getPath(), string);
            BasicFileAttributes fileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            NameAndSize nameAndSize = NameAndSize.builder().
                    fileSize(fileAttributes.size()).fullName(file.getCanonicalPath()).build();
            //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
            if (fileAttributes.isDirectory()) {
                allFileName.addAll(Objects.requireNonNull(getNameAndSize(file.getCanonicalPath(), currentDepth)));
            } else {
                allFileName.add(nameAndSize);
            }
        }
        return allFileName;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameAndSize {
        private String fullName;
        private Long fileSize;

        //使得输出更简洁
        @Override
        public String toString() {
            return "fullName: " + fullName + ", with fileSize: " + fileSize;
        }
    }
}
