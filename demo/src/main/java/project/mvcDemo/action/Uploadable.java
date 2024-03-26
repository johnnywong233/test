package project.mvcDemo.action;

import jakarta.servlet.http.Part;

public interface Uploadable {
    /**
     * 设置文件名
     *
     * @param filenames 文件名的数组
     */
    void setFilenames(String[] filenames);

    /**
     * 设置上传的附件
     *
     * @param parts 附件的数组
     */
    void setParts(Part[] parts);
}
