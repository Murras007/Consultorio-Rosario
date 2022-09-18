package views.pages.auth;

import animatefx.animation.Tada;
import api.controller.UserApi;
import api.exception.UserTranslateException;
import api.models.Utilizador;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.Alert;
import util.Routes;
import util.ViewManager;
import views.pages.home.HomeController;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    @FXML
    private JFXTextField txt_user;
    @FXML
    private JFXPasswordField txt_pwd;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    boolean validate() {
        if (txt_user.getText().isEmpty()) {
            Alert.Message.thread = new Thread(()-> Platform.runLater(()-> new Tada(txt_user).play()));
            Alert.Message.show("Campo vazio!", "Por favor, preencha o nome de utilizador!", Alert.Type.WARNING);
            return false;
        }
        if (txt_pwd.getText().isEmpty()) {
            Alert.Message.thread = new Thread(()-> Platform.runLater(()-> new Tada(txt_pwd).play()));
            Alert.Message.show("Campo vazio!", "Por favor, preencha a palavra-passe!", Alert.Type.WARNING);
            return false;
        }
        return true;
    }

    @FXML
    void login(Event e) throws Exception {
        JFXButton btn = (JFXButton) e.getSource();
        btn.setGraphic(new JFXSpinner());
        if ( validate() ) {
           try {
               Utilizador userLogado =  new UserApi.Auth().signin(new Utilizador(txt_user.getText().trim(), txt_pwd.getText()));
               if (userLogado != null) {
                   HomeController.login = userLogado;
                   ViewManager.Manager.toView(Routes.Page.Main.MAIN);
                   return;
               }
           }catch (Exception ex) {
               System.err.println(ex.getMessage());
               Alert.Message.show(
                       UserTranslateException.Auth.translate(ex.getMessage()).title,
                       UserTranslateException.Auth.translate(ex.getMessage()).message,
                       Alert.Type.WARNING);
           }
        }
        btn.setGraphic(null);
    }

    @FXML
    void close (){
        ViewManager.closeApplication();
    }
}
