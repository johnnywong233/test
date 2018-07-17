package hadoop.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;

/**
 * Created by Johnny on 2018/4/7.
 */
@Slf4j
public class HDFSUtil {
    /**
     * 新建文件
     *
     * @param configuration config
     * @param filePath      path
     * @param data          data
     * @throws IOException ex
     */
    public static void createFile(Configuration configuration, String filePath, byte[] data) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(filePath));
        fsDataOutputStream.write(data);
        fsDataOutputStream.close();
        fileSystem.close();
    }

    /**
     * 创建文件夹
     */
    public static void mkDirs(Configuration configuration, String filePath) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.mkdirs(new Path(filePath));
        fileSystem.close();
    }

    /**
     * 删除文佳 true 为递归删除 否则为非递归
     */
    public static void deleteFile(Configuration configuration, String filePath, boolean isReturn) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        boolean delete = fileSystem.delete(new Path(filePath), isReturn);
        if (!delete) {
            throw new RuntimeException("删除失败");
        }
        log.error("shit");
        fileSystem.close();
    }

    /**
     * 读取文件内容
     */
    public static String readFile(Configuration conf, String filePath)
            throws IOException {
        String res;
        FileSystem fs;
        FSDataInputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            fs = FileSystem.get(conf);
            inputStream = fs.open(new Path(filePath));
            outputStream = new ByteArrayOutputStream(inputStream.available());
            IOUtils.copyBytes(inputStream, outputStream, conf);
            res = outputStream.toString();
        } finally {
            if (inputStream != null) {
                IOUtils.closeStream(inputStream);
            }
            if (outputStream != null) {
                IOUtils.closeStream(outputStream);
            }
        }
        return res;
    }


    /**
     * 从本地上传文件到HDFS
     *
     * @param configuration config
     * @throws IOException ex
     */
    public static void uploadFile(Configuration configuration, String localFilePath, String remoteFilePath) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        fileSystem.copyFromLocalFile(new Path(localFilePath), new Path(remoteFilePath));
        fileSystem.close();
    }

    /**
     * 判断目录是否存在
     */
    public static boolean fileExists(Configuration configuration, String filePath) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        return fileSystem.exists(new Path(filePath));
    }
}
