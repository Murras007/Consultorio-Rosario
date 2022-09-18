package views.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import util.Routes;
import util.ViewManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private BorderPane mainPage;

    private final JFXProgressBar progressBar = new JFXProgressBar();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    void initComponents(){
        ViewManager.setRootPage(content);
    }

    @FXML
    void toProduto() throws Exception {
        to(Routes.Page.Produto.MAIN);
    }

    void to(String path){
        mainPage.setTop(progressBar);
        Platform.runLater(
                ()->{
                    try {
                        ViewManager.Manager.toPage(path);
                        mainPage.setTop(null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }










}
