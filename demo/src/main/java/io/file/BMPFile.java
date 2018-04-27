package io.file;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.FileOutputStream;

public class BMPFile extends Component {

    private static final long serialVersionUID = 6432428347340464906L;
    private final static int BITMAPFILEHEADER_SIZE = 14;
    private final static int BITMAPINFOHEADER_SIZE = 40;

    private byte bfType[] = {'B', 'M'};
    private int bfSize = 0;
    private int bfReserved1 = 0;
    private int bfReserved2 = 0;
    private int bfOffBits = BITMAPFILEHEADER_SIZE + BITMAPINFOHEADER_SIZE;

    private int biSize = BITMAPINFOHEADER_SIZE;
    private int biWidth = 0;
    private int biHeight = 0;
    private int biPlanes = 1;
    private int biBitCount = 24;
    private int biCompression = 0;
    private int biSizeImage = 0x030000;
    private int biXPelsPerMeter = 0x0;
    private int biYPelsPerMeter = 0x0;
    private int biClrUsed = 0;
    private int biClrImportant = 0;
    private int bitmap[];
    private FileOutputStream fo;

    public BMPFile() {
    }


    public void saveBitmap(String parFilename, Image parImage, int
            parWidth, int parHeight) {
        try {
            fo = new FileOutputStream(parFilename);
            save(parImage, parWidth, parHeight);
            fo.close();
        } catch (Exception saveEx) {
            saveEx.printStackTrace();
        }
    }

    private void save(Image parImage, int parWidth, int parHeight) {
        try {
            convertImage(parImage, parWidth, parHeight);
            writeBitmapFileHeader();
            writeBitmapInfoHeader();
            writeBitmap();
        } catch (Exception saveEx) {
            saveEx.printStackTrace();
        }
    }

    public static void main(String args[]) {
//        saveBitmap("C:\\work\\test_git\\test\\demo\\src\\main\\resources", aImage, 32, 48);
    }

    private boolean convertImage(Image parImage, int parWidth, int parHeight) {
        int pad;
        bitmap = new int[parWidth * parHeight];
        PixelGrabber pg = new PixelGrabber(parImage, 0, 0, parWidth, parHeight,
                bitmap, 0, parWidth);

        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return (false);
        }
        pad = (4 - ((parWidth * 3) % 4)) * parHeight;
        biSizeImage = ((parWidth * parHeight) * 3) + pad;
        bfSize = biSizeImage + BITMAPFILEHEADER_SIZE +
                BITMAPINFOHEADER_SIZE;
        biWidth = parWidth;
        biHeight = parHeight;
        return (true);
    }

    private void writeBitmap() {
        int size;
        int value;
        int j;
        int i;
        int rowCount;
        int rowIndex;
        int lastRowIndex;
        int pad;
        int padCount;
        byte rgb[] = new byte[3];
        size = (biWidth * biHeight) - 1;
        pad = 4 - ((biWidth * 3) % 4);
        if (pad == 4)
            pad = 0;
        rowCount = 1;
        padCount = 0;
        rowIndex = size - biWidth;
        lastRowIndex = rowIndex;
        try {
            for (j = 0; j < size; j++) {
                value = bitmap[rowIndex];
                rgb[0] = (byte) (value & 0xFF);
                rgb[1] = (byte) ((value >> 8) & 0xFF);
                rgb[2] = (byte) ((value >> 16) & 0xFF);
                fo.write(rgb);
                if (rowCount == biWidth) {
                    padCount += pad;
                    for (i = 1; i <= pad; i++) {
                        fo.write(0x00);
                    }
                    rowCount = 1;
                    rowIndex = lastRowIndex - biWidth;
                    lastRowIndex = rowIndex;
                } else
                    rowCount++;
                rowIndex++;
            }
            bfSize += padCount - pad;
            biSizeImage += padCount - pad;
        } catch (Exception wb) {
            wb.printStackTrace();
        }
    }

    private void writeBitmapFileHeader() {
        try {
            fo.write(bfType);
            fo.write(intToDWord(bfSize));
            fo.write(intToWord(bfReserved1));
            fo.write(intToWord(bfReserved2));
            fo.write(intToDWord(bfOffBits));
        } catch (Exception wbfh) {
            wbfh.printStackTrace();
        }
    }

    private void writeBitmapInfoHeader() {
        try {
            fo.write(intToDWord(biSize));
            fo.write(intToDWord(biWidth));
            fo.write(intToDWord(biHeight));
            fo.write(intToWord(biPlanes));
            fo.write(intToWord(biBitCount));
            fo.write(intToDWord(biCompression));
            fo.write(intToDWord(biSizeImage));
            fo.write(intToDWord(biXPelsPerMeter));
            fo.write(intToDWord(biYPelsPerMeter));
            fo.write(intToDWord(biClrUsed));
            fo.write(intToDWord(biClrImportant));
        } catch (Exception wbih) {
            wbih.printStackTrace();
        }
    }

    private byte[] intToWord(int parValue) {
        byte retValue[] = new byte[2];
        retValue[0] = (byte) (parValue & 0x00FF);
        retValue[1] = (byte) ((parValue >> 8) & 0x00FF);
        return (retValue);
    }

    private byte[] intToDWord(int parValue) {
        byte retValue[] = new byte[4];
        retValue[0] = (byte) (parValue & 0x00FF);
        retValue[1] = (byte) ((parValue >> 8) & 0x000000FF);
        retValue[2] = (byte) ((parValue >> 16) & 0x000000FF);
        retValue[3] = (byte) ((parValue >> 24) & 0x000000FF);
        return (retValue);
    }
}
