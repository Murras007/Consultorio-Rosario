package views.components.armazem;

import api.controller.ArmazemControllerApi;
import api.models.armazem.Armazem;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.ViewManager;
import views.pages.armazem.ArmazemController;

import java.net.URL;
import java.util.ResourceBundle;

public class ArmazemEditorController implements Initializable {
    public static Armazem armazem;

    @FXML
    private JFXTextField txt_designacao;
    @FXML
    private JFXTextArea txt_descricao;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    void init() {
        if (armazem == null)
            armazem = new Armazem();
        else{
            txt_designacao.setText(armazem.designacao);
            txt_descricao.setText(armazem.descricao);
        }
    }

    @FXML
    void save() throws Exception{
        fill();
        ArmazemControllerApi armazemControllerApi = new ArmazemControllerApi();
        if(armazem.id == null) {
            armazemControllerApi.save(armazem);
        }else {
            armazemControllerApi.update(armazem);
        }
        close();
    }

    void fill(){
        armazem.designacao = txt_designacao.getText();
        armazem.descricao = txt_descricao.getText();
    }

    @FXML
    void close() {
        new Thread(ArmazemController.refresh).start();
        ViewManager.closeModal();
    }

}
