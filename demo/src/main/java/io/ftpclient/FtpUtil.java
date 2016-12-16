package io.ftpclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import utils.CommonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2016/12/3
 * Time: 14:38
 */
public class FtpUtil {
    private static Log logger = LogFactory.getLog(FtpUtil.class);

    private FTPClient ftp;

    private String ftpServer;

    private String ftpPort;

    private String userName;

    private String password;

    public void setFtpServer(String ftpServer) {
        this.ftpServer = ftpServer;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FtpUtil(String ftpServer, String ftpPort, String userName, String password) {
        this.ftpServer = ftpServer;
        this.ftpPort = ftpPort;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 连接Ftp服务器
     *
     * @return SUCCESS 其他:失败信息
     */
    public String loginToFtpServer() {
        if (null == ftp) {
            try {
                ftp = new FTPClient();

                // 不设端口 默认使用默认端口登录21
                if (CommonUtil.isEmpty(ftpPort)) {
                    ftp.connect(this.ftpServer);
                } else {
                    ftp.connect(this.ftpServer, Integer.parseInt(ftpPort));
                }

                //下面三行代码必须要有,而且不能改变编码格式否则不能正确下载中文文件
                ftp.setControlEncoding("GBK");
                FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
                conf.setServerLanguageCode("zh");

                //验证通道连接是否建立成功(回传响应码)
                if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                    ftp.disconnect();
                    logger.error("FTP连接失败，ip:" + this.ftpServer);
                    return "FTP连接失败，ip:" + this.ftpServer;
                }

                if (!ftp.login(this.userName, this.password)) {
                    logger.error("FTP登录失败，ip:" + this.ftpServer + ";userName:" + userName + ";port:" + ftpPort);
                    return "FTP登录失败，ip:" + this.ftpServer + ";userName:" + userName + ";port:" + ftpPort;
                }
            } catch (Exception e) {
                logger.error("FTP连接失败", e);
                return "FTP连接失败" + e;
            }
        }
        logger.info("Ftp" + this.ftpServer + "登录成功!;port:" + ftpPort);
        return "SUCCESS";
    }

    /**
     * 关闭ftp连接 <功能详细描述> void
     */
    public void closeConnection() {
        if (null != ftp && ftp.isConnected()) {
            try {
                ftp.disconnect();
                logger.info("FTP" + this.ftpServer + "连接关闭");
            } catch (Exception e) {
                logger.error("FTP连接关闭异常", e);
            }
        }
    }

    /**
     * 上传文件到ftp <功能详细描述>
     *
     * @param remotePath    ftp路径
     * @param remotePath    文件名
     * @param localFileName 要上传的本地文件
     * @return SECCESS:上传成功 其他:上传失败信息
     */
    public String uploadFile(String remotePath, String localFileName) {
        FileInputStream in = null;
        try {
            // 存放在Ftp上的文件名称
            String remoteFileName = "";
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            if (CommonUtil.isEmpty(localFileName) || CommonUtil.isEmpty(remotePath)) {
                logger.error("文件上传Ftp失败目标路径或源路径错误");
                return "文件上传Ftp失败目标路径或源路径错误";
            }
            remoteFileName = localFileName.substring(localFileName.lastIndexOf("/") + 1, localFileName.length());

            // 确保文件路径存在
            ftp.makeDirectory(remotePath);

            if (!ftp.changeWorkingDirectory(remotePath)) {
                logger.error("转至目录[" + remotePath + "]失败");
                return "转至目录[" + remotePath + "]失败";
            }

            // 上传之前先删除原来文件,防止重复对账(文件不存不报异常)
            ftp.deleteFile(remoteFileName);

            in = new FileInputStream(new File(localFileName));
            ftp.storeFile(new String(remoteFileName.getBytes("GBK"), "iso-8859-1"), in);
        } catch (Exception e) {
            logger.error("文件上传Ftp失败:", e);
            return "文件上传Ftp失败:" + e;
        } finally {
            CommonUtil.closeStream(in);
        }
        return "SUCCESS";
    }

    /**
     * 获取文件夹下文件名称列表 <功能详细描述>
     *
     * @param remotePath 文件夹路径
     * @return List<String> 文件名称列表
     */
    public List<String> getFileList(String remotePath) {
        try {
            if (ftp.changeWorkingDirectory(remotePath)) {
                String[] str = ftp.listNames();
                if (null == str || str.length < 0) {
                    return null;
                }
                return Arrays.asList(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从Ftp下载文件 <功能详细描述>
     *
     * @param remotePath     Ftp上文件路径
     * @param remoteFileName 远程文件名
     * @param localFileName  下载到本地文件路径(带文件名)
     * @return SUCCESS:成功 其他:失败信息
     */
    public String download(String remotePath, String remoteFileName, String localFileName) {
        FileOutputStream oStream = null;
        try {
            // 切换到指定目录下
            if (ftp.changeWorkingDirectory(remotePath)) {
                oStream = new FileOutputStream(new File(localFileName));

                if (!ftp.retrieveFile(remoteFileName, oStream)) {
                    logger.info("从Ftp上下载文件失败！" + remoteFileName);
                    return "从Ftp上下载文件失败！" + remoteFileName;
                }
            } else {
                logger.info("对账文件下载失败，不能正常切换至目录" + remotePath + ";目录不存在！");
                return "对账文件下载失败，不能正常切换至目录" + remotePath + ";目录不存在！";
            }
        } catch (Exception e) {
            logger.info("Ftp上文件" + remoteFileName + "下载失败!", e);
            return "Ftp上文件" + remoteFileName + "下载失败!" + e;
        } finally {
            CommonUtil.closeStream(oStream);
        }
        return "SUCCESS";
    }

    /**
     * 删除Ftp上的文件夹 包括其中的文件 <功能详细描述>
     *
     * @param pathName 文件夹路径
     * @return SUCCESS:成功 其他:失败
     */
    public String removeDirectoryALLFile(String pathName) {
        try {
            FTPFile[] files = ftp.listFiles(pathName);
            if (null != files && files.length > 0) {
                for (FTPFile file : files) {
                    if (file.isDirectory()) {
                        removeDirectoryALLFile(pathName + "/" + file.getName());

                        // 切换到父目录，不然删不掉文件夹
                        ftp.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
                        ftp.removeDirectory(pathName);
                    } else {
                        if (!ftp.deleteFile(pathName + "/" + file.getName())) {
                            return "删除指定文件" + pathName + "/" + file.getName() + "失败!";
                        }
                    }
                }
            }
            // 切换到父目录，不然删不掉文件夹
            ftp.changeWorkingDirectory(pathName.substring(0, pathName.lastIndexOf("/")));
            ftp.removeDirectory(pathName);
        } catch (IOException e) {
            logger.error("删除指定文件夹" + pathName + "失败：" + e);
            e.printStackTrace();
            return "删除指定文件夹" + pathName + "失败：" + e;
        }
        return "SUCCESS";
    }

    /**
     * 删除指定文件
     *
     * @param filePath 文件路径(含文件名)
     * @return SUCCESS:成功 其他:失败信息
     */
    public String removeFile(String filePath) {
        try {
            if (StringUtils.isNotEmpty(filePath)) {
                if (!ftp.deleteFile(filePath)) {
                    return "删除文件" + filePath + "失败！";
                }
            }
        } catch (IOException e) {
            logger.error("删除文件失败：", e);
            e.printStackTrace();
            return "删除文件" + filePath + "失败！" + e;
        }
        return "SUCCESS";
    }

    /**
     * 向文件头添加合计信息
     *
     * @param localPath 目标文件所在文件夹路径
     * @param desFile   目标文件名
     * @param mergeStr  插入字符串信息
     * @return SUCCESS:成功 其他:失败信息
     */
    public static String mergeFile(String localPath, String desFile, String mergeStr) {
        // 向目标文件头中添加合计信息
        FileInputStream inputStream = null;
        FileOutputStream fileOutStream = null;
        try {
            inputStream = new FileInputStream(localPath + "/" + desFile);
            byte allBytes[] = new byte[inputStream.available()];
            inputStream.read(allBytes);

            fileOutStream = new FileOutputStream(localPath + "/" + desFile);
            fileOutStream.write(mergeStr.getBytes());
            fileOutStream.write(allBytes);
        } catch (IOException e) {
            return e.getMessage();
        } finally {
            CommonUtil.closeStream(fileOutStream);
            CommonUtil.closeStream(inputStream);
        }
        return "SUCCESS";
    }

    /**
     * 删除备份目录下不符合时间的文件 <功能详细描述>
     *
     * @param dailyBakPath 备份目录
     * @param dailyBakDate 有效时间 1、5、9等
     * @return SUCCESS:成功 其他:失败信息
     */
    public static String deleteFile(String dailyBakPath, String dailyBakDate) {
        try {
            // 获取符合规则的日期
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

            // 当天时间
            Calendar theday = Calendar.getInstance();

            // 存放符合备份的日期时间 数组长度为备份的天数+1
            String[] dates = new String[Integer.valueOf(dailyBakDate) + 1];
            for (int i = 0; i < dates.length; i++) {
                dates[i] = df.format(theday.getTime());

                // 获取上一天的时间
                theday.add(Calendar.DATE, -1);
            }

            File a = new File(dailyBakPath);
            if (!a.exists()) {
                return dailyBakPath + "目录不存在!";
            }

            // 获取目录下所有文件
            String[] fileArr = a.list();

            // 遍历文件名称，查看是否在保留日期dates内,不在则删除
            for (int i = 0; i < fileArr.length; i++) {
                boolean canDele = true;
                for (int j = 0; j < dates.length; j++) {
                    // 不删除dates内开头的文件 和 文件名包含error的文件
                    if (fileArr[i].startsWith(dates[j]) || fileArr[i].contains("error")) {
                        canDele = false;
                        break;
                    }
                }
                if (canDele) {
                    deletefile(dailyBakPath + "/" + fileArr[i]);
                }
            }
        } catch (Exception e) {
            logger.info("删除文件夹内容操作出错,请查看配置路径或保留时间是否正确！");
            return "删除文件夹内容操作出错,请查看配置路径或保留时间是否正确！";
        }
        return "SUCCESS";
    }

    /**
     * 根据入参,删除文件夹(下文件及文件夹)或文件
     *
     * @param delpath 文件夹路径或文件路径
     * @return boolean true:成功 false:失败
     */
    public static boolean deletefile(String delpath) {
        try {
            File file = new File(delpath);
            if (file.isDirectory()) {
                String[] fileList = file.list();
                for (String fileName : fileList) {
                    deletefile(delpath + "\\" + fileName);
                }
            }
            file.delete();
        } catch (Exception e) {
            logger.error("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }

    //http://joudi.blog.51cto.com/4686277/1546891
    public static void main(String[] args) throws IOException {
        //System.out.println(deletefile("G:/Q"));

        FtpUtil ftpUtil = new FtpUtil("192.168.132.110", "21", "a", "a");
        System.out.println(ftpUtil.loginToFtpServer());

        // System.out.println(ftpUtil.removeDirectoryALLFile("/home/tbcs/zhangvb/epay/20140820"));

        // FTPClient client=new FTPClient();
        // client.connect("192.168.132.110");
        // client.login("a","a");
        // System.out.println(client.removeDirectory("/home/tbcs/zhangvb/epay/test"));
    }
}
