package me.shenalsenarath;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
//        System.out.println("Hello World!");
//        Image testImage = new Image(ImageIO.read(new File("C:\\projects\\googlevisionapisample\\src\\main\\resources\\Error3.JPG")));
//        //testImage.cropImage(1,1,50,5);
//        GoogleVisionClient client = GoogleVisionClient.getInstance();
//        System.out.println(client.ocrImage(testImage.getImageBase64()));
        File pdfFile=new File("C:\\Users\\ssenarath\\Documents\\SLD August 1 2016 (3).pdf");
        PDDocument pdfDocument = PDDocument.load(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

        for(int i=0;i<pdfDocument.getNumberOfPages();i++) {
            BufferedImage image = pdfRenderer.renderImage(i, 3);
            ImageIO.write(image, "png", new File("C:\\Users\\ssenarath\\Documents\\test"+i+".png"));
            GoogleVisionClient client = GoogleVisionClient.getInstance();
            Image testImage = new Image(image);
            String rtString= client.ocrImage(testImage.getImageBase64());
            System.out.println(rtString);
            try(PrintWriter out = new PrintWriter("C:\\Users\\ssenarath\\Documents\\page"+i+".txt" )){
                out.println( rtString );
            }
        }

    }
}
