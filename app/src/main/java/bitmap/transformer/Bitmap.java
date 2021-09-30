package bitmap.transformer;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Bitmap {
    private String inputPath;
    private String outputPath;
    private String newFileName;
    private BufferedImage image = null;

    public Bitmap(String inputPath, String outputPath, String newFileName) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.newFileName = newFileName;
    }

    public boolean readFile() {
        try {
            File file = new File(this.inputPath);
            this.image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveFile() {
        try {
            File outputFile = new File(this.outputPath + this.newFileName);
            ImageIO.write(this.image, "bmp", outputFile);
        } catch (IOException e) {
            System.out.println("File not found");
            return false;
        }
        return true;
    }

    //convert to grayScale
    public boolean BlackAndWhite() {
        int height = this.image.getHeight();
        int width = this.image.getWidth();

        for (int h = 1; h < height; h++) {
            for (int w = 1; w < width; w++) {
                int p = this.image.getRGB(w,h);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //      calculate avg color
                int avg = (r+g+b)/3;

                //    replace RGB value with avg

                p = (avg<<24) | (avg<<16) | (avg<<8) | avg;

                this.image.setRGB(w, h, p);
            }
        }
        return true;
    }



    public int imageFlipVertical() {
        int lastRGBVal = 0;
        int height = this.image.getHeight();
        int width = this.image.getWidth();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.image.setRGB(x, (height - 1) - y, this.image.getRGB(x, y));
                lastRGBVal = this.image.getRGB(x, height - 1 - y);
            }
        }
        return lastRGBVal;
    }
    public void addTextWatermark(String text) {

        Graphics2D g2d = (Graphics2D) this.image.getGraphics();

        // initializes necessary graphic properties
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
        g2d.setComposite(alphaChannel);
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 64));
        FontMetrics fontMetrics = g2d.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

        // calculates the coordinate where the String is painted
        int centerX = (this.image.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = this.image.getHeight() / 2;

        // paints the textual watermark
        g2d.drawString(text, centerX, centerY);


        System.out.println("The tex watermark is added to the image.");

    }
}
