import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PosterTest {
    private final static int width = 750;
    private final static int height = 1334;

    public static void main(String[] args) throws Exception{
        BufferedImage posterImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = posterImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BufferedImage backgroundImg = ImageIO.read(new File("/Users/taohuang/Downloads/background.jpg"));
        g2.drawImage(backgroundImg, 0, 0, posterImg.getWidth(), posterImg.getHeight(), null);

        //图片是一个圆型
        java.awt.geom.Ellipse2D.Double shape = new java.awt.geom.Ellipse2D.Double(300, 160, 110, 110);
        g2.setClip(shape);
        BufferedImage headImg = ImageIO.read(new File("/Users/taohuang/Downloads/my.jpg"));
        g2.drawImage(headImg, 300, 160, 110, 110, null);
        g2.setClip(null);

        Stroke s = new BasicStroke(4.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(s);
        g2.setColor(Color.WHITE);
        g2.drawOval(300, 160, 110, 110);

        ImageIO.write(posterImg, "png", new File("/Users/taohuang/Downloads/poster.png"));
    }

    private static BufferedImage readImage(String path) throws Exception{
        BufferedImage image = null;
        URL url = new URL(path);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(6000);
        con.setReadTimeout(6000);
        InputStream is = null;
        try{
            is = con.getInputStream();
            image = ImageIO.read(is);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(is != null){
                is.close();
            }
        }
        return image;
    }
}
