package views.components.menus.backoffice;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.Routes;
import util.ViewManager;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void toArmazem() throws Exception {
        ViewManager.Manager.toPage(Routes.Component.Armazem.MAIN);
    }

    @FXML
    void toProduto() throws Exception {
        ViewManager.Manager.toPage(Routes.Page.Produto.MAIN);
    }

    @FXML
    void toRequisicoes() throws Exception {
        ViewManager.Manager.toPage(Routes.Page.Requisicao.MAIN);
    }
}
