package file.pic.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.Data;

//Java library for extracting EXIF, IPTC, XMP, ICC and other metadata from image files.

import java.io.File;
import java.io.IOException;

/**
 * Created by johnny on 2016/9/15
 * demo of metadata usage
 */
public class SampleUsage {
    /**
     * 图片信息获取metadata元数据信息
     *
     * @param fileName 需要解析的文件
     */
    private ImgInfoBean parseImgInfo(String fileName) {
        File file = new File(fileName);
        ImgInfoBean imgInfoBean = null;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            imgInfoBean = printImageTags(file, metadata);
        } catch (ImageProcessingException e) {
            System.err.println("error 1a: " + e);
        } catch (IOException e) {
            System.err.println("error 1b: " + e);
        }
        return imgInfoBean;
    }

    /**
     * 读取metadata里面的信息
     *
     * @param sourceFile 源文件
     * @param metadata   metadata元数据信息
     * @return image info bean
     */
    private ImgInfoBean printImageTags(File sourceFile, Metadata metadata) {
        ImgInfoBean imgInfoBean = new ImgInfoBean();
        imgInfoBean.setImgSize(sourceFile.getTotalSpace());
        imgInfoBean.setImgName(sourceFile.getName());
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();
                String desc = tag.getDescription();
                if ("Image Height".equals(tagName)) {
                    //图片高度
                    imgInfoBean.setImgHeight(desc);
                } else if ("Image Width".equals(tagName)) {
                    //图片宽度
                    imgInfoBean.setImgWidth(desc);
                } else if ("Date/Time Original".equals(tagName)) {
                    //拍摄时间
                    imgInfoBean.setDateTime(desc);
                } else if ("GPS Altitude".equals(tagName)) {
                    //海拔
                    imgInfoBean.setAltitude(desc);
                } else if ("GPS Latitude".equals(tagName)) {
                    //纬度
                    imgInfoBean.setLatitude(pointToLatlong(desc));
                } else if ("GPS Longitude".equals(tagName)) {
                    //经度
                    imgInfoBean.setLongitude(pointToLatlong(desc));
                }
            }
            for (String error : directory.getErrors()) {
                System.err.println("ERROR: " + error);
            }
        }
        return imgInfoBean;
    }

    /**
     * 经纬度转换  度分秒转换
     *
     * @param point 坐标点
     */
    private String pointToLatlong(String point) {
        double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        double duStr = du + fen / 60 + miao / 60 / 60;
        return Double.toString(duStr);
    }

    //http://www.phpxs.com/code/1001549/
    public static void main(String[] args) {
        ImgInfoBean imgInfoBean = new SampleUsage().parseImgInfo("D:\\Java_ex\\test\\src\\test\\resources\\DSC03208.JPG");
        System.out.println(imgInfoBean.toString());
    }
}

@Data
class ImgInfoBean {
    private String imgHeight;
    private String imgWidth;
    private String dateTime;
    private String altitude;
    private String latitude;
    private String longitude;
    private Long imgSize;
    private String imgName;
}