package views.pages.produto;

import api.controller.StockControllerApi;
import api.controller.produto.ProdutoControllerApi;
import api.models.produto.Produto;
import api.models.stock.StockProduto;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import util.Routes;
import util.ViewManager;
import views.components.produto.ProdutoDataTable;
import views.components.produto.ProdutoEditorController;
import views.components.produto.ProdutoStockDataTable;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProdutoController implements Initializable {

    @FXML
    private AnchorPane dataTableContainer;
    @FXML
    private AnchorPane stockdataTableContainer;
    @FXML
    private AnchorPane stockTransferenciasPanel;


    public static Runnable refresh;
    final ProdutoControllerApi api = new ProdutoControllerApi();
    final StockControllerApi stockApi = new StockControllerApi();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComponents();
    }

    void initComponents(){
        try
        {

            initTransferencias();
            new Thread(ProdutoController.refresh = () -> Platform.runLater(this::loadData)).start();
            new Thread(ProdutoController.refresh = () -> Platform.runLater(this::loadStockData)).start();
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }


    void initTransferencias(){
        new Thread(()->
                Platform.runLater(()->{
                    try{
                        AnchorPane transferenciaPanel = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Routes.Component.Produto.TRANSFERENCIA)));
                        AnchorPane.setRightAnchor(transferenciaPanel, 0.0);
                        AnchorPane.setTopAnchor(transferenciaPanel, 0.0);
                        AnchorPane.setLeftAnchor(transferenciaPanel, 0.0);
                        AnchorPane.setBottomAnchor(transferenciaPanel, 0.0);
                        stockTransferenciasPanel.getChildren().clear();
                        stockTransferenciasPanel.getChildren().add(transferenciaPanel);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                })
        ).start();
    }

    void loadStockData() {
        try {
            List<StockProduto> storageEntities = stockApi.findAllStokProduto();
            BorderPane dataTable = new ProdutoStockDataTable(storageEntities).getTable();

            AnchorPane.setRightAnchor(dataTable, 0.0);
            AnchorPane.setLeftAnchor(dataTable, 0.0);
            AnchorPane.setTopAnchor(dataTable, 0.0);
            AnchorPane.setBottomAnchor(dataTable, 0.0);

            stockdataTableContainer.getChildren().add(dataTable);

        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    void loadData() {
        try {
            List<Produto> storageEntities = api.findAll();
            BorderPane dataTable = new ProdutoDataTable(storageEntities).getTable();

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
        ProdutoEditorController.produto = null;
        ViewManager.Manager.showModal(
                Routes.Component.Produto.EDITOR,
                Pos.CENTER,
                true
        );
    }

    @FXML
    void transferencia() throws Exception {
        ViewManager.Manager.showModal(
                Routes.Component.Produto.TRANSFERENCIA,
                Pos.CENTER,
                true
        );
    }

}
