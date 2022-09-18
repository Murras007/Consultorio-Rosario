package util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    public static boolean saveImage(Image image, File outputFile ) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", outputFile);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean saveImage(File inputFile, File outputFile ) {
        Image image = new Image(inputFile.toURI().toString());
        return saveImage(image, outputFile);
    }

    public static boolean saveFile(File origen, File dest) {
        try {
            FileInputStream fileInputStream = new FileInputStream(origen);
            FileOutputStream outputStream = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            fileInputStream.close();
            return  true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }
    
    public static void openTempPDF(byte[] data){
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File("entrada.pdf");
                Files.write(Paths.get(file.getAbsolutePath()), data);
                file.deleteOnExit();
                Desktop.getDesktop().open(file);
            } catch (Exception ex) {
                // no application registered for PDFs
                Alert.Toast.showToast("Impossível abrir o documento", Alert.Type.ERROR);
            }
        }
    }
}
