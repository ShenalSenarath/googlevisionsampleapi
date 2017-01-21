package me.shenalsenarath;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;


/**
 * @author ssenarath
 */
public class Image {
    final Encoder base64Encoder = Base64.getMimeEncoder();
    BufferedImage image;

    public Image(BufferedImage image) {
        this.image = image;
    }

    public Image() {
    }

    public void takeScreenShot() throws AWTException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        this.image = new Robot().createScreenCapture(screenRect);
    }

    public void readFromDisk(File file) throws IOException {
        this.image = ImageIO.read(file);
    }

    /**
     * Crops the iamge defined by a specified rectangular region.
     * The returned <code>BufferedImage</code> shares the same
     * data array as the original image.
     * @param x the X coordinate of the upper-left corner of the
     *          specified rectangular region
     * @param y the Y coordinate of the upper-left corner of the
     *          specified rectangular region
     * @param w the width of the specified rectangular region
     * @param h the height of the specified rectangular region
     * @exception RasterFormatException if the specified
     * area is not contained within this <code>BufferedImage</code>.
     */
    public void cropImage(int x, int y, int w, int h) {
        this.image = this.image.getSubimage(x, y, w, h);
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public String getImageBase64() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", out);
        byte[] bytes = out.toByteArray();
        return base64Encoder.encodeToString(bytes);
    }


}
