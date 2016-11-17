package jbarcode;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.CodabarEncoder;
import org.jbarcode.encode.Code11Encoder;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.Code39ExtEncoder;
import org.jbarcode.encode.Code93Encoder;
import org.jbarcode.encode.Code93ExtEncoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.encode.EAN8Encoder;
import org.jbarcode.encode.Interleaved2of5Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.encode.MSIPlesseyEncoder;
import org.jbarcode.encode.PostNetEncoder;
import org.jbarcode.encode.Standard2of5Encoder;
import org.jbarcode.encode.UPCAEncoder;
import org.jbarcode.encode.UPCEEncoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.EAN8TextPainter;
import org.jbarcode.paint.HeightCodedPainter;
import org.jbarcode.paint.UPCATextPainter;
import org.jbarcode.paint.UPCETextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by wajian on 2016/8/31.
 *
 */
public class BarcodeCreator {

	//http://blog.csdn.net/jianggujin/article/details/50527286
    public static void main(String[] args) throws InvalidAtributeException, IOException {
        BarcodeCreator test = new BarcodeCreator();
        JBarcode jBarcode = new JBarcode(EAN13Encoder.getInstance(), WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
        //TODO
        test.setEncoder(BarcodeEncoder.Code128);
        test.barcode = jBarcode;
//        test.toBufferedImage("johnny");
        test.write(BarcodeEncoder.Code11.toString(), new File("D:\\Java_ex\\test\\src\\test\\resources\\bar.png"));
    }

    /** 用于生成条形码的对象 **/
    private JBarcode barcode = null;

    /**
     * constructor
     */
    private BarcodeCreator() {
        barcode = new JBarcode(EAN13Encoder.getInstance(),
                WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());
        barcode.setBarHeight(17);
        barcode.setShowText(true);
        barcode.setCheckDigit(true);
        barcode.setShowCheckDigit(true);
    }

     /*生成条形码文件*/
    public void write(String code, File file) throws IOException,
            InvalidAtributeException {
        ImageIO.write(toBufferedImage(code), "JPEG", file);
    }

    /*生成条形码并写入指定输出流 */
    public void write(String code, OutputStream os) throws IOException,
            InvalidAtributeException {
        ImageIO.write(toBufferedImage(code), "JPEG", os);
    }

    /*创建条形码的BufferedImage图像*/
    private BufferedImage toBufferedImage(String code)
            throws InvalidAtributeException {
        return barcode.createBarcode(code);
    }

    /*set the designed encoder */
    private void setEncoder(BarcodeEncoder encoder) {
        //change enum to int
        int val = encoder.ordinal();
        switch (val) {
            case 0:
                barcode.setEncoder(EAN13Encoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(EAN13TextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 1:
                barcode.setEncoder(UPCAEncoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(UPCATextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 2:
                barcode.setEncoder(EAN8Encoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(EAN8TextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 3:
                barcode.setEncoder(UPCEEncoder.getInstance());
                barcode.setTextPainter(UPCETextPainter.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 4:
                barcode.setEncoder(CodabarEncoder.getInstance());
                barcode.setPainter(WideRatioCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 5:
                barcode.setEncoder(Code11Encoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 6:
                barcode.setEncoder(Code39Encoder.getInstance());
                barcode.setPainter(WideRatioCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(false);
                barcode.setShowCheckDigit(false);
                break;
            case 7:
                barcode.setEncoder(Code39ExtEncoder.getInstance());
                barcode.setPainter(WideRatioCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(false);
                barcode.setShowCheckDigit(false);
                break;
            case 8:
                barcode.setEncoder(Code93Encoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(false);
                break;
            case 9:
                barcode.setEncoder(Code93ExtEncoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(false);
                break;
            case 10:
                barcode.setEncoder(Code128Encoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(false);
                break;
            case 11:
                barcode.setEncoder(MSIPlesseyEncoder.getInstance());
                barcode.setPainter(WidthCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 12:
                barcode.setEncoder(Standard2of5Encoder.getInstance());
                barcode.setPainter(WideRatioCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(false);
                break;
            case 13:
                barcode.setEncoder(Interleaved2of5Encoder.getInstance());
                barcode.setPainter(WideRatioCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(17);
                barcode.setShowText(true);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(true);
                break;
            case 14:
                barcode.setEncoder(PostNetEncoder.getInstance());
                barcode.setPainter(HeightCodedPainter.getInstance());
                barcode.setTextPainter(BaseLineTextPainter.getInstance());
                barcode.setBarHeight(6);
                barcode.setShowText(false);
                barcode.setCheckDigit(true);
                barcode.setShowCheckDigit(false);
                break;
        }
    }

    /*条形码编码方式*/
    private enum BarcodeEncoder {
        EAN13, UPCA, EAN8, UPCE, Codabar, Code11, Code39, Code39Ext, Code93, Code93Ext, Code128, MSIPlessey, Standard2of5, Interleaved2of5, PostNet
    }
}
