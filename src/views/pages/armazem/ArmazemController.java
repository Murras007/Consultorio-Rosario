package views.pages.armazem;

import api.controller.ArmazemControllerApi;
import api.models.armazem.Armazem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import util.Routes;
import util.ViewManager;
import views.components.armazem.ArmazemDataTable;
import views.components.armazem.ArmazemEditorController;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArmazemController implements Initializable {

    @FXML
    private AnchorPane dataTableContainer;
    public static Runnable refresh;
    final ArmazemControllerApi api = new ArmazemControllerApi();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    void initComponents(){
        try
        {
            new Thread(ArmazemController.refresh = () -> Platform.runLater(this::loadData)).start();
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    void loadData() {
        try {
            List<Armazem> storageEntities = api.findAll();
            BorderPane dataTable = new ArmazemDataTable(storageEntities).getTable();

            AnchorPane.setRightAnchor(dataTable, 0.0);
            AnchorPane.setLeftAnchor(dataTable, 0.0);
            AnchorPane.setTopAnchor(dataTable, 0.0);
            AnchorPane.setBottomAnchor(dataTable, 0.0);

            dataTableContainer.getChildren().add(dataTable);

        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    void adicionar() throws Exception {
        ArmazemEditorController.armazem = null;
        ViewManager.Manager.showModal(
                Routes.Component.Armazem.EDITOR,
                Pos.CENTER,
                true
        );
    }

}
