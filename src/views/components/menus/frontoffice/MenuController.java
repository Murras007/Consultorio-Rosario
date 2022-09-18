package views.components.menus.frontoffice;

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
    void toDepartamento() throws Exception {
        ViewManager.Manager.toPage(Routes.Component.Entrada.EDITOR);
    }
    @FXML
    void toRequisicao() throws Exception {
        ViewManager.Manager.toPage(Routes.Component.Requisicao.EDITOR);
    }
}
