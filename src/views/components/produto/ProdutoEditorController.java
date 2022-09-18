package views.components.produto;

import api.controller.produto.ProdutoControllerApi;
import api.models.enums.FormaSaida;
import api.models.enums.UnidadeMedida;
import api.models.produto.Categoria;
import api.models.produto.Produto;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import util.Routes;
import util.ViewManager;
import views.pages.armazem.ArmazemController;
import views.pages.produto.ProdutoController;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProdutoEditorController implements Initializable {

    public static Produto produto;
    @FXML
    private JFXTextField txt_designacao;
    @FXML
    private JFXTextArea txt_descricao;
    @FXML
    private HBox imageContainer;
    @FXML
    private JFXComboBox<UnidadeMedida> cmb_unidade;
    @FXML
    private JFXComboBox<FormaSaida> cmb_saida;
    @FXML
    private JFXComboBox<Categoria> cmb_categoria;

    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    void init() {

        cmb_saida.getItems().addAll(FormaSaida.values());
        cmb_unidade.getItems().addAll(UnidadeMedida.values());

        carregarCategorias();
        if (produto == null)
            produto = new Produto();
        else{
            txt_designacao.setText(produto.designacao);
            txt_descricao.setText(produto.descricao);
            if (produto.formaSaida != null)
                cmb_saida.setValue(produto.formaSaida);

            if (produto.unidade != null)
                cmb_unidade.setValue(produto.unidade);

            if (produto.categoria != null)
                selectCateoria(produto.categoria);
        }
    }

    void selectCateoria(Categoria categoria) {
        for(Categoria item: cmb_categoria.getItems()) {
            if (categoria.id == item.id){
                cmb_categoria.setValue(item);
                break;
            }
        }
    }

    void carregarCategorias() {
        try {
            ProdutoControllerApi.CategoriaControllerApi categoriaControllerApi = new ProdutoControllerApi.CategoriaControllerApi();
            cmb_categoria.getItems().addAll(categoriaControllerApi.findAll());
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    @FXML
    void save() throws Exception{
        fill();
        ProdutoControllerApi ProdutoControllerApi = new ProdutoControllerApi();
        ProdutoControllerApi.save(produto, file);
        close();
    }
    @FXML
    void saveAndEntry() throws Exception{
        save();
        ViewManager.Manager.showModal(Routes.Component.Entrada.EDITOR, Pos.CENTER,true );
    }


    void fill() {
        produto.designacao = txt_designacao.getText();
        produto.descricao = txt_descricao.getText();
        produto.unidade = cmb_unidade.getValue();
        produto.formaSaida = cmb_saida.getValue();
        produto.categoria = cmb_categoria.getValue();
    }

    @FXML
    void close() {
        new Thread(ProdutoController.refresh).start();
        ViewManager.closeModal();
    }

    void prevImage() throws Exception {
        if (file != null) {
            imageContainer.getChildren().clear();
            ImageView view = new ImageView(new Image(new FileInputStream(file)));
            view.setFitHeight(100);
            view.setFitWidth(100);
            view.setPreserveRatio(true);
            imageContainer.getChildren().add(view);
        }
    }

    @FXML
    void onDragOver(DragEvent event){
        if (event.getDragboard().hasFiles())
            event.acceptTransferModes(TransferMode.COPY);
        event.consume();
    }


    @FXML
    void onDragDropped(DragEvent event) throws Exception{
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            file = db.getFiles().get(0);
            prevImage();
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

}
