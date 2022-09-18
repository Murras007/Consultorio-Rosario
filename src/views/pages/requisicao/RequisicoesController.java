package views.pages.requisicao;

import api.controller.RequisicaoControllerApi;
import api.models.stock.Requisicao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import views.components.requisicao.AdmDataTableRequisicoes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RequisicoesController implements Initializable {

    AdmDataTableRequisicoes table;
    List<Requisicao> data;
    private BorderPane border;
    RequisicaoControllerApi req = new RequisicaoControllerApi();
    @FXML
    private AnchorPane container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table = new AdmDataTableRequisicoes(data = new ArrayList<>());
        border = table.getTable();
        initTable();
    }

    void initTable(){
        container.getChildren().add(border);
        AnchorPane.setRightAnchor(border, 0.0);
        AnchorPane.setTopAnchor(border, 0.0);
        AnchorPane.setBottomAnchor(border, 0.0);
        AnchorPane.setLeftAnchor(border, 0.0);
        loadData();
    }

    void loadData(){
        try {
            data.clear();
            data.addAll(req.findAll());
            table.refresh();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
