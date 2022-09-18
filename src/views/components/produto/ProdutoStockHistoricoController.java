package views.components.produto;

import api.controller.StockControllerApi;
import api.controller.produto.ProdutoControllerApi;
import api.models.entrada.Lote;
import api.models.enums.FormaSaida;
import api.models.enums.UnidadeMedida;
import api.models.produto.Categoria;
import api.models.produto.Produto;
import api.models.stock.StockProduto;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import util.Routes;
import util.ViewManager;
import views.pages.produto.ProdutoController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProdutoStockHistoricoController implements Initializable {

    public static Lote lote;
    @FXML
    private Label produto_label;

    @FXML
    private AnchorPane datatablecontainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    void init() {
        produto_label.setText(lote.produto.designacao.toUpperCase());

        try{

            List<StockProduto> list = new StockControllerApi().findHistoricoProdutoByLote(lote);
            BorderPane dataTable = new ProdutoStockHistoricoDataTable(list).getTable();

            AnchorPane.setRightAnchor(dataTable, 0.0);
            AnchorPane.setTopAnchor(dataTable, 0.0);
            AnchorPane.setLeftAnchor(dataTable, 0.0);
            AnchorPane.setBottomAnchor(dataTable, 0.0);

            datatablecontainer.getChildren().add(dataTable);


        }catch(Exception exception){
            exception.printStackTrace();
        }


    }



    @FXML
    void close() {

        ViewManager.closeModal();
    }

}
