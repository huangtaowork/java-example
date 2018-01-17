import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class QrcodeUtil {

    /**
     * 常规生成二维码
     */
    private static void writeQrcode() throws Exception{
        QRCode code = QRCode.from("Hello World");
        code.withSize(430, 430);
        code.to(ImageType.JPG);
        try(ByteArrayOutputStream out = code.stream()){
            try(FileOutputStream fout = new FileOutputStream(new File("/Users/taohuang/Downloads/qrcode1.jpg"))){
                fout.write(out.toByteArray());
            }
        }
    }

    /**
     * 去白边的二维码
     */
    private static void writeMatrixQrcode() throws Exception{
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置图片的最大值
        // hints.put(EncodeHintType.MAX_SIZE, 350);
        // 设置图片的最小值
        // hints.put(EncodeHintType.MIN_SIZE, 100);
        // 设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 0);
        BitMatrix matrix = new QRCodeWriter().encode("Hello World", BarcodeFormat.QR_CODE, 430, 430, hints);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "jpg", out);
        try(FileOutputStream fout=new FileOutputStream(new File("/Users/taohuang/Downloads/qrcode2.jpg"))){
            fout.write(out.toByteArray());
        }
    }


    /**
     * 生成带Logo二维码
     */
    private static void writeLogoQrcode() throws Exception{
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置图片的最大值
        // hints.put(EncodeHintType.MAX_SIZE, 350);
        // 设置图片的最小值
        // hints.put(EncodeHintType.MIN_SIZE, 100);
        // 设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix matrix = new QRCodeWriter().encode("Hello World", BarcodeFormat.QR_CODE, 430, 430, hints);

        BufferedImage qrcodeImage = toBufferedImage(matrix);
        int qrcodeWidth = qrcodeImage.getWidth();
        int qrcodeHeigh = qrcodeImage.getHeight();

        Graphics2D g2 = qrcodeImage.createGraphics();

        // 读取logo图片
        BufferedImage logoImage = ImageIO.read(new File("/Users/taohuang/Downloads/11.jpg"));

        double logoWidth = qrcodeWidth / 5;
        double logoHeigh = qrcodeHeigh / 5;
        double logoX = qrcodeWidth / 5 * 2;
        double logoY = qrcodeHeigh / 5 * 2;

        Shape shape = new java.awt.geom.Ellipse2D.Double(logoX, logoY, logoWidth, logoHeigh);
        g2.setClip(shape);

        g2.drawImage(logoImage, (int)logoX, (int)logoY, (int)logoWidth, (int)logoHeigh,null);

        g2.setClip(null);

        // 设置笔画对象
        BasicStroke stroke = new BasicStroke(4);
        g2.setStroke(stroke);

        // 指定弧度的圆角矩形
        RoundRectangle2D.Double round = new RoundRectangle2D.Double(logoX, logoY, logoWidth, logoHeigh,100,100);
        g2.setColor(Color.white);
        // 绘制圆弧矩形
        g2.draw(round);

        g2.dispose();
        qrcodeImage.flush();

        ImageIO.write(qrcodeImage, "jpg", new File("/Users/taohuang/Downloads/qrcode3.jpg"));
    }


    private static BufferedImage toBufferedImage(BitMatrix matrix){
        int black = 0xFF000000;  // 用于设置图案的颜色
        int white = 0xFFFFFFFF;  // 用于背景色
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? black : white));
            }
        }
        return image;
    }

}
