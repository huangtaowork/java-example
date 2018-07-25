import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class App {

    public static void main(String[] args) throws Exception{
        //drawFont();
        test();
    }

    private static void drawFont() throws Exception{
        // https://blog.csdn.net/qq_28600665/article/details/79640118

        // 创建画布
        BufferedImage image = new BufferedImage(750,1334, BufferedImage.TYPE_INT_RGB);
        // 背景图
        BufferedImage background = ImageIO.read(new File("/Users/taohuang/Downloads/background.jpg"));
        // 开启画图
        Graphics2D graphics = image.createGraphics();
        // 把背景图添加到画布中
        graphics.drawImage(background.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT),0,0,null);

        // 写入名字，引入自定义字体
        Font font = getFont(25);
        //graphics.setColor(Color.decode("#333333"));
        graphics.setColor(Color.black);
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawString("黄涛", 206, 306);

        // 关闭画布
        graphics.dispose();

        ImageIO.write(image, "png", new File("/Users/taohuang/Downloads/poster.png"));
    }

    private static Font getFont(float fontSize) throws Exception{
        Font font = null;
        String url = "/Users/taohuang/Downloads/simsun.ttc";
        File file = new File(url);
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            Font tempFont = Font.createFont(Font.TRUETYPE_FONT, fileInputStream);
            font = tempFont.deriveFont(fontSize);
        }
        return font;
    }


    private static void test(){
        InputStream is = null;
        String path = "http://wechatwork.dmhub.cn/referplan/poster/0f15c5edbf6a4f08a83e86cada13c21e?x_tenant_id=26";
        try{
            URL url = new URL(path);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(6000);
            con.setReadTimeout(6000);
            is = con.getInputStream();
            BufferedImage image = ImageIO.read(is);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(is != null){
                    is.close();
                }
            }catch (Exception e){

            }
        }
    }
}
