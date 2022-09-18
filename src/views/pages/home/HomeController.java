package views.pages.home;

import api.controller.ArmazemControllerApi;
import api.models.Utilizador;
import api.models.armazem.Armazem;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.Alert;
import util.Routes;
import util.ViewManager;
import views.components.armazem.ArmazemEditorController;
import views.pages.armazem.ArmazemController;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    public static Utilizador login;

    @FXML
    private Label user_label;

    @FXML
    private HBox armazem_list;

    @FXML
    private AnchorPane table_view;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void loadDataTable(){

    }

}
