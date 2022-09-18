package util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ComponentUtil {

    public static class Pointer { double x = 0, y = 0; }
    public static void makeNodeDraggable(Node node) {
        Pointer dragMeasure  = new Pointer();
        Pointer position  = new Pointer();
        node.setOnMousePressed(event -> {
            dragMeasure.x = event.getSceneX() - position.x;
            dragMeasure.y = event.getSceneY() - position.y;
        });
        node.setOnMouseDragged(event -> {
            position.x = event.getSceneX() - dragMeasure.x;
            position.y = event.getSceneY() - dragMeasure.y;
            node.setTranslateX(position.x);
            node.setTranslateY(position.y);
        });

    }
    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
    public static Image createAvatar(Image image) throws Exception{
        BufferedImage master = SwingFXUtils.fromFXImage(image, null);
        return createAvatar(master);
    }
    public static Image createAvatar(File file) throws Exception{
        BufferedImage master = ImageIO.read(file);
        return createAvatar(master);

    }
    public static Image createAvatar(BufferedImage master) throws Exception{


        int diameter = Math.min(master.getWidth(), master.getHeight());
        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();
        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = masked.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - master.getWidth()) / 2;
        int y = (diameter - master.getHeight()) / 2;
        g2d.drawImage(master, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0, null);
        g2d.dispose();
        return SwingFXUtils.toFXImage(masked, null);
    }
    public static void onlyNumericTextField(final TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d{1,3}(,\\d{3})*(\\.\\d+)?$")) {
                    textField.setText(newValue.replaceAll("[^\\d{1,3}(,\\d{3})*(\\.\\d+)?$]", ""));
                }
            }
        });
    }
    public static class ComboBoxUtil<T> {

        public void load(List<T> data, ComboBox<T> cbo){

            ObservableList<T> obsList = FXCollections.observableArrayList(data);
            cbo.setItems(obsList);
        }
        public void load(ComboBox<T> cbo, T... data){

            ObservableList<T> obsList = FXCollections.observableArrayList(data);
            cbo.setItems(obsList);
        }
        public void load(List<T> data, TableView<T> tb){

            ObservableList<T> obsList = FXCollections.observableArrayList(data);
            tb.setItems(obsList);
        }
        public T getSelect(TableView<T> tb){

            return tb.selectionModelProperty().getValue().getSelectedItem();
        }
    }

}
