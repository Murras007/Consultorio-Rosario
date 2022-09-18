package views.components.requisicao;

import api.controller.RequisicaoControllerApi;
import api.controller.produto.ProdutoControllerApi;
import api.models.entrada.Lote;
import api.models.armazem.Armazem;
import api.models.produto.Produto;
import api.models.stock.Requisicao;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import util.*;
import views.pages.home.HomeController;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    private JFXTextField txt_qtd;
    @FXML
    private JFXComboBox<Produto> cmb_produto;

    @FXML
    private JFXComboBox<Lote> cmb_referencia;

    @FXML
    private AnchorPane itensContainer;


    public static List<Lote> lotes;

    private static Runnable runner;


    public static Lote loteSelected;
    public static Armazem armazem;
    public static Produto produto;
    List<Lote> data;
    List<Requisicao> dataRequisicao;
    private Requisicao requisicao;
    LoteDataTableRequisicao loteDataTableRequisicao;
    LoteDataTableRequisicoes dataTableRequisicoes;
    @FXML
    private AnchorPane datatablecontainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initComponents();
    }

    void initComponents(){
        ComponentUtil.onlyNumericTextField(txt_qtd);
        loteDataTableRequisicao = new LoteDataTableRequisicao(data = new ArrayList<>());
        dataTableRequisicoes = new LoteDataTableRequisicoes(dataRequisicao = new ArrayList<>());

        BorderPane table = loteDataTableRequisicao.getTable();
        BorderPane tableRequisicoes = dataTableRequisicoes.getTable();

        AnchorPane.setRightAnchor(table, 0.0);
        AnchorPane.setTopAnchor(table, 0.0);
        AnchorPane.setLeftAnchor(table, 0.0);
        AnchorPane.setBottomAnchor(table, 0.0);

        AnchorPane.setRightAnchor(tableRequisicoes, 0.0);
        AnchorPane.setTopAnchor(tableRequisicoes, 0.0);
        AnchorPane.setLeftAnchor(tableRequisicoes, 0.0);
        AnchorPane.setBottomAnchor(tableRequisicoes, 0.0);

        itensContainer.getChildren().add(table);
        datatablecontainer.getChildren().add(tableRequisicoes);
        ProdutoControllerApi produtoControllerApi = new ProdutoControllerApi();
        try {

            cmb_produto.getItems().addAll(produtoControllerApi.findAll());
            cmb_produto.setOnAction(e -> {
                if (cmb_produto.getValue() != null){
                    cmb_referencia.getItems().clear();
                    try {
                        Long id = cmb_produto.getValue().id;
                        cmb_referencia.getItems().addAll(produtoControllerApi.findAllLote(id));

                    }catch(Exception exc){
                        exc.printStackTrace();
                    }

                }
            });
            loadData();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadLote(){
        new Thread(()-> Platform.runLater(runner)).start();
    }

    private void loadData() {

        try {
            RequisicaoControllerApi requisicoesApi = new RequisicaoControllerApi();
            dataRequisicao.clear();
            dataRequisicao.addAll(requisicoesApi.findAll());
            dataTableRequisicoes.refresh();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    @FXML
    void add(ActionEvent event) throws Exception {
        if (cmb_produto.getValue() != null && !txt_qtd.getText().isEmpty()){
            Lote lote = cmb_referencia.getValue();
            if (lote == null){
                lote = new Lote();
            }
            lote.id = null;
            lote.produto = cmb_produto.getValue();
            lote.quantidade =  StringUtil.getValueFromString(txt_qtd.getText());
            if (cmb_referencia.getValue() != null){
                lote.referencia = String.valueOf(cmb_referencia.getValue());
            }
            data.add(lote);
            loteDataTableRequisicao.refresh();
        }
    }

    @FXML
    void salvar() throws Exception{

        if (!data.isEmpty()) {
            requisicao = new Requisicao();
            requisicao.departamento = armazem;
            requisicao.lotes = data;
            requisicao.solicitante = HomeController.login;
            requisicao.designacao = "Requisição de "+ requisicao.solicitante.username + ", dia "+LocalDateTime.now();
            RequisicaoControllerApi api = new RequisicaoControllerApi();
            Requisicao req = api.save(requisicao);
            loadData();

            api.findReport(req.id);
        }

    }
}
