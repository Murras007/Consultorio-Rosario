package views.components.entrada;

import api.controller.ArmazemControllerApi;
import api.controller.EntradaControllerApi;
import api.controller.FornecedorControllerApi;
import api.controller.produto.ProdutoControllerApi;
import api.models.entrada.Entrada;
import api.models.entrada.FormaEntrada;
import api.models.entrada.Lote;
import api.models.armazem.Armazem;
import api.models.entrada.Pagamento;
import api.models.enums.FormaPagamento;
import api.models.fornecedor.Fornecedor;
import api.models.produto.Produto;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.*;
import views.pages.home.HomeController;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EntradaController implements Initializable {

    @FXML
    private JFXComboBox<Produto> cmb_produto;

    @FXML
    private JFXTextField txt_fabricante;
    @FXML
    private JFXTextField txt_num_lote;

    @FXML
    private JFXDatePicker data_validade;

    @FXML
    private JFXTextField txt_custo_compra;

    @FXML
    private JFXTextField txt_qtd;

    @FXML
    private JFXSlider sld_margem;

    @FXML
    private Tab itens_tab_title;

    @FXML
    private Tab tab_finalizar;

    @FXML
    private JFXTextField txt_preco_venda;

    @FXML
    private JFXTextField txt_referencia;

    @FXML
    private JFXCheckBox check_referencia_auto;

    @FXML
    private AnchorPane itens_container;

    @FXML
    private JFXComboBox<FormaPagamento> cmb_forma_pagamento;

    @FXML
    private JFXTextField txt_referencia_pagamento;

    @FXML
    private JFXTextField txt_valor_pagamento;

    @FXML
    private VBox vb_pagamento;

    @FXML
    private JFXComboBox<Fornecedor> cmb_fornecedor;

    @FXML
    private JFXTabPane tab_pane_entrada;

    @FXML
    private JFXComboBox<Armazem> cmb_armazem;

    @FXML
    private JFXComboBox<FormaEntrada> cmb_forma_entrada;

    @FXML
    private JFXButton btn_finalizar;

    @FXML
    private JFXTextField txt_n_fatura;

    @FXML
    private JFXDatePicker data_entrada;

    @FXML
    private Label lbl_subtotal;

    @FXML
    private Label lbl_total;

    @FXML
    private Label lbl_pago;
    public static List<Lote> lotes;
    private LoteDataTable loteDataTable;
    private static Runnable runner;


    public static Lote loteSelected;
    public static Armazem armazem;
    public static Produto produto;

    private Entrada entrada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initModel();
        initComponents();
        runner = this::fillEntryLote;
    }

    void initModel() {
        entrada = new Entrada();
        lotes = new ArrayList<>();
        entrada.lotes = lotes;
        entrada.pagamentos = new ArrayList<>();
        itens_container.getChildren().add((loteDataTable = new LoteDataTable(lotes)).getTable());
    }

    void selectProduto(Produto pro) {
        for(Produto p: cmb_produto.getItems())
            if (pro.id.equals(p.id))
            {
                cmb_produto.setDisable(true);
                cmb_produto.setValue(p);
                return;
            }


    }

    void selectArmazem(Armazem arm) {
        for(Armazem a: cmb_armazem.getItems())
            if (a.id.equals(arm.id))
            {
                cmb_armazem.setDisable(true);
                cmb_armazem.setValue(a);
                return;
            }


    }

    void initComponents(){
        ComponentUtil.onlyNumericTextField(txt_qtd);
        ComponentUtil.onlyNumericTextField(txt_custo_compra);
        ComponentUtil.onlyNumericTextField(txt_preco_venda);
        ComponentUtil.onlyNumericTextField(txt_valor_pagamento);
        btn_finalizar.setDisable(true);
        tab_finalizar.setDisable(true);
        vb_pagamento.getChildren().clear();
        ArmazemControllerApi armazemControllerApi = new ArmazemControllerApi();
        ProdutoControllerApi produtoControllerApi = new ProdutoControllerApi();
        FornecedorControllerApi fornecedorControllerApi = new FornecedorControllerApi();
        EntradaControllerApi.FormaEntradaControllerApi formaEntradaApi = new EntradaControllerApi.FormaEntradaControllerApi();
        try {
            cmb_armazem.getItems().addAll(armazemControllerApi.findAll());
            cmb_produto.getItems().addAll(produtoControllerApi.findAll());
            cmb_fornecedor.getItems().addAll(fornecedorControllerApi.findAll());
            cmb_forma_pagamento.getItems().addAll(FormaPagamento.values());
            cmb_forma_entrada.getItems().addAll(formaEntradaApi.findAll());

            if (produto != null)
                selectProduto(produto);
            if (armazem != null)
                selectArmazem(armazem);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void clear() {
        txt_referencia.clear();
        check_referencia_auto.setSelected(true);
        txt_fabricante.clear();
        data_validade.setValue(null);
        txt_qtd.clear();
        txt_custo_compra.clear();
        txt_preco_venda.clear();
        sld_margem.setValue(50.0);
    }

    @FXML
    void addItem(){
        final Armazem armazem = cmb_armazem.getValue();
        final Produto produto = cmb_produto.getValue();
        if (armazem != null && produto != null && !txt_num_lote.getText().trim().isEmpty()) {

            final String refer =txt_referencia.getText();
            final String fabricante = txt_fabricante.getText();
            final String numLote =  txt_num_lote.getText();

            final LocalDate dataValidade = data_validade.getValue();
            final Double qtd = StringUtil.getValueFromString(txt_qtd.getText());
            final Double custo = StringUtil.getValueFromString(txt_custo_compra.getText());
            final Double precoVenda = StringUtil.getValueFromString(txt_preco_venda.getText());

            Lote lote = new Lote();
            lote.numeroLote = numLote;
            lote.armazem = armazem;
            lote.produto = produto;
            lote.precoCompra = custo;
            lote.preco = precoVenda;
            lote.quantidade = qtd;
            lote.referencia = refer;


//            lote.margemLucro = ((lote.preco - lote.precoCompra) / lote.precoCompra) * 100;
            lote.margemLucro = 0.0;
            lote.totalCusto = lote.precoCompra * lote.quantidade;
            lote.totalLucro = (lote.preco - lote.precoCompra) * lote.quantidade;
            lote.fabricante = fabricante;
            if (dataValidade != null)
                lote.dataValidade = LocalDateTime.of(dataValidade, LocalTime.now()).format(DateTimeFormatter.ISO_DATE_TIME);
            addElement(lote);
        }else
        Alert.Toast.showToast("Verifique se os campos estão devidamente preenchidos", Alert.Type.INFO);
    }

    @FXML
    void calc() {
        double custo = StringUtil.getValueFromString(txt_custo_compra.getText().trim());
        double percMargem = sld_margem.getValue();
        double val = custo + (custo * (percMargem / 100.0));
        txt_preco_venda.setText(StringUtil.getNumberFormatted(val));
    }

    void total(){
        double subtotal = 0.0;
        double total = 0.0;
        double pago = 0.0;
        for(Lote lote: entrada.lotes){
            subtotal += lote.quantidade;
            total += lote.totalCusto;
        }

        for(Pagamento pagamento: entrada.pagamentos){
            pago += pagamento.valor;
        }
        lbl_subtotal.setText(StringUtil.getNumberFormatted(subtotal));
        lbl_total.setText(StringUtil.getNumberFormatted(total) + "Kz");
        lbl_pago.setText(StringUtil.getNumberFormatted(pago) + "Kz");
    }

    void addElement(Lote lote){
        for(Lote l: lotes)
        {
            if (l.produto.id.equals(lote.produto.id) && l.armazem.id.equals(lote.armazem.id) && l.referencia.equals(lote.referencia)|| l.numeroLote.equals(lote.numeroLote)) {
               l.armazem = lote.armazem;
               l.produto = lote.produto;
               l.precoCompra = lote.precoCompra;
               l.preco = lote.preco;

               l.quantidade += lote.quantidade;
//               l.margemLucro = (l.preco - l.precoCompra) / l.precoCompra;
               l.margemLucro = 0.0;
               l.totalCusto = l.precoCompra * l.quantidade;
               l.totalLucro = (l.preco - l.precoCompra) * l.quantidade;
               l.referencia = lote.referencia;
               l.fabricante = lote.fabricante;
               l.dataValidade = lote.dataValidade;
               loteDataTable.refresh();
                return;
            }
        };
        lotes.add(lote);
        itens_tab_title.setText("ITENS ADICIONADOS("+lotes.size()+")");
        loteDataTable.refresh();
        btn_finalizar.setDisable(false);
        tab_finalizar.setDisable(false);
        total();
        clear();
    }
    @FXML
    void generateLoteNumber(){
        String numLote = check_referencia_auto.isSelected()?
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyHHmmss")):"";
        txt_num_lote.setText(numLote);
    }

    void fillEntryLote() {
        if (loteSelected != null){
            txt_referencia.setText(loteSelected.referencia);
            txt_fabricante.setText(loteSelected.fabricante);
            data_validade.setValue(LocalDate.parse(loteSelected.dataValidade, DateTimeFormatter.ISO_DATE_TIME));
            txt_qtd.setText(StringUtil.getNumberFormatted(loteSelected.quantidade));
            txt_custo_compra.setText(StringUtil.getNumberFormatted(loteSelected.precoCompra));
            txt_preco_venda.setText(StringUtil.getNumberFormatted(loteSelected.preco));
            cmb_armazem.setValue(loteSelected.armazem);
            cmb_produto.setValue(loteSelected.produto);
        }

        btn_finalizar.setDisable(lotes.isEmpty());
        tab_finalizar.setDisable(lotes.isEmpty());
        itens_tab_title.setText("ITENS ADICIONADOS("+lotes.size()+")");
        tab_pane_entrada.getSelectionModel().selectFirst();
    }

    public static void loadLote(){
        new Thread(()-> Platform.runLater(runner)).start();
    }

    @FXML
    void close() {
        produto = null;
        armazem = null;
        loteSelected = null;

        ViewManager.closeModal();

    }

    @FXML
    void finalTab(){
        tab_pane_entrada.getSelectionModel().selectLast();
    }

    @FXML
    void addPagamento(){
        if (!txt_valor_pagamento.getText().isEmpty() && !cmb_forma_pagamento.getSelectionModel().isEmpty()){
            Pagamento pagamento = new Pagamento();
            pagamento.formaPagamento = cmb_forma_pagamento.getValue();
            pagamento.valor = StringUtil.getValueFromString(txt_valor_pagamento.getText());
            pagamento.referencia = pagamento.local = txt_referencia_pagamento.getText();
            entrada.pagamentos.add(pagamento);

            cmb_forma_pagamento.getSelectionModel().selectFirst();
            txt_referencia_pagamento.clear();
            txt_valor_pagamento.clear();
            renderPagamentos();
            total();
        }
        else
            Alert.Toast.showToast("Deve inserir o valor do pagamento e a forma de como pagou", Alert.Type.WARNING);
    }

    void renderPagamentos(){
        vb_pagamento.getChildren().clear();
        for(final Pagamento p: entrada.pagamentos){
            try {
                HBox pBox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Routes.Component.Entrada.PAGAMENTO_ITEM)));
                VBox payInfo = (VBox) pBox.getChildren().get(0);
                VBox payAction = (VBox) pBox.getChildren().get(1);

                Label formaPagamento = (Label)  payInfo.getChildren().get(0);
                Label referenciaPagamento = (Label)  payInfo.getChildren().get(1);
                Label valorPagamento = (Label)  payInfo.getChildren().get(2);

                JFXButton btnEdit = (JFXButton) payAction.getChildren().get(0);
                JFXButton btnRem = (JFXButton) payAction.getChildren().get(1);

                formaPagamento.setText(p.formaPagamento.toString());
                referenciaPagamento.setText(p.referencia);
                valorPagamento.setText(StringUtil.getNumberFormatted(p.valor).concat(p.moedaSimbolo));


                btnEdit.setOnAction(e -> {
                    cmb_forma_pagamento.setValue(p.formaPagamento);
                    txt_referencia_pagamento.setText(p.referencia);
                    txt_valor_pagamento.setText(StringUtil.getNumberFormatted(p.valor));
                    entrada.pagamentos.remove(p);
                    renderPagamentos();
                });

                btnRem.setOnAction(e -> {
                    entrada.pagamentos.remove(p);
                    renderPagamentos();
                });

                vb_pagamento.getChildren().add(pBox);
            }catch (Exception ignored) {}
        }
        total();
    }

    @FXML
    void save(ActionEvent event) throws Exception {
        LocalDate dataEntrada = data_entrada.getValue();
        if (cmb_fornecedor.getValue() != null && !txt_n_fatura.getText().isEmpty()) {
            entrada.fornecedor = cmb_fornecedor.getValue();
            entrada.feitoPor = HomeController.login;
            entrada.formaEntrada = cmb_forma_entrada.getValue();
            entrada.numeroFatura = txt_n_fatura.getText().toUpperCase().trim();
            entrada.dataFatura = (dataEntrada == null? LocalDate.now().toString():dataEntrada.toString()) + "T00:00:00";
            EntradaControllerApi api = new EntradaControllerApi();
            Entrada newEntrada = api.save(entrada);
            api.findReport(newEntrada.id);
            close();
        }
    }
}
