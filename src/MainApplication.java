import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import util.Routes;
import util.ViewManager;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager.initView(primaryStage);
        ViewManager.Manager.showModal(Routes.Page.AUTH.LOGIN, Pos.CENTER_LEFT, false);
    }
}
