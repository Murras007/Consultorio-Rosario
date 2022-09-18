package views.components.produto;

import api.controller.ArmazemControllerApi;
import api.controller.StockControllerApi;
import api.controller.produto.ProdutoControllerApi;
import api.models.armazem.Armazem;
import api.models.enums.FormaSaida;
import api.models.enums.UnidadeMedida;
import api.models.produto.Categoria;
import api.models.produto.Produto;
import api.models.stock.StockProduto;
import api.models.stock.Transferencia;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Routes;
import util.ViewManager;
import views.pages.home.HomeController;
import views.pages.produto.ProdutoController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProdutoTransferenciaController implements Initializable {

    @FXML
    private JFXComboBox<Armazem> cmb_armazem_origem;

    @FXML
    private VBox list_produtos_origem;

    @FXML
    private VBox list_origem_destino;

    @FXML
    private JFXComboBox<Armazem> cmb_armazem_destino;

    @FXML
    private JFXTextField txt_pesquisa;

    @FXML
    private JFXComboBox<Produto> cmb_produto;

    List<StockProduto>  stockProdutos;

    final StockControllerApi stockApi = new StockControllerApi();
    Transferencia transferencia = new Transferencia();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    void init() {
        try {

            ArmazemControllerApi armazemControllerApi = new ArmazemControllerApi();
            ProdutoControllerApi produtoControllerApi = new ProdutoControllerApi();
            cmb_produto.getItems().addAll(produtoControllerApi.findAll());
            List<Armazem> armazens = armazemControllerApi.findAll();
            cmb_armazem_destino.getItems().addAll(armazens);
            cmb_armazem_origem.getItems().addAll(armazens);
            transferencia.feito_por = HomeController.login;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void loadListProduto(){


        try {
            stockProdutos = stockApi.findAllStokProduto();
            list_produtos_origem.getChildren().clear();
             for(StockProduto p : stockProdutos){
                 HBox produtItem = FXMLLoader.load(getClass().getResource(Routes.Component.Produto.CARD_TRANSFERENCIA));

                 list_produtos_origem.getChildren().add(produtItem);
             }

        }catch(Exception exception){
            exception.printStackTrace();
        }
    }



    @FXML
    void save() throws Exception{

        close();
    }



    @FXML
    void close() {

        ViewManager.closeModal();
    }

}
