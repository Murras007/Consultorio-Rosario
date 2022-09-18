package util;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInUpBig;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;

public class Alert {

    public static class Toast {
        private static final HBox toast = new HBox();

        public static void showToast (String message, Type type) {
            ViewManager.VIEW_ROOT.getChildren().remove(toast);
            SVGPath icon = new SVGPath();
            switch (type) {
                case WARNING: icon.setContent(IconsPath._24x.ALERT); break;
                case SUCCESS: icon.setContent(IconsPath._24x.CHECK); break;
                case INFO: icon.setContent(IconsPath._24x.LAMP); break;
                case ERROR: icon.setContent(IconsPath._24x.ERROR); break;
            }
            toast.setSpacing(10.0);
            toast.setAlignment(Pos.CENTER_LEFT);
            toast.getStyleClass().add("toast");
            Label label = new Label(message);
            toast.getChildren().clear();
            toast.getChildren().addAll(icon,label);
            FadeInUpBig animate = new FadeInUpBig(toast);
            AnchorPane.setRightAnchor(toast, 10.0);
            AnchorPane.setBottomAnchor(toast, 30.0);
            ViewManager.VIEW_ROOT.getChildren().add(toast);
            toast.setOnMouseClicked((e)-> {
                if (ViewManager.VIEW_ROOT.getChildren().contains(toast))
                    if (ViewManager.VIEW_ROOT.getChildren().contains(toast))
                        ViewManager.VIEW_ROOT.getChildren().remove(toast);
            });
            animate.play();
            new Thread(()-> {
                try { Thread.sleep(8000);} catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
                Platform.runLater(()-> {
                    ViewManager.VIEW_ROOT.getChildren().remove(toast);
                } );
            }).start();
        }
    }

    public static class Message {
        public static Thread thread;

        public static void show(String title, String message, Type type) {
            /* Containers: */
            final VBox boxContent = new VBox();
            final HBox boxFooteer = new HBox();
            final VBox boxBody = new VBox();
            final VBox boxShadow = new VBox();

            /* Labels: */
            final Label titleLabel = new Label(title);
            final Label messageLabel = new Label(message);

            titleLabel.setTextAlignment(TextAlignment.CENTER);
            titleLabel.setWrapText(true);
            titleLabel.getStyleClass().add("title");
            messageLabel.setTextAlignment(TextAlignment.CENTER);
            messageLabel.setWrapText(true);

            /* Actions: */
            final JFXButton buttonClose = new JFXButton();
            final JFXButton buttonAgree = new JFXButton("Ok");
            final JFXButton buttonCancel = new JFXButton("Cancelar");

            /* Icons: */
            final SVGPath closeIcon = new SVGPath();
            final SVGPath displayIcon = new SVGPath();
            closeIcon.setContent(IconsPath._24x.ERROR);

            switch (type) {
                case WARNING:titleLabel.getStyleClass().add("warning"); displayIcon.getStyleClass().add("warning"); displayIcon.setContent(IconsPath._60x.ALERT); break;
                case SUCCESS:titleLabel.getStyleClass().add("sucess");  displayIcon.getStyleClass().add("sucess"); displayIcon.setContent(IconsPath._60x.CHECK); break;
                case INFO:titleLabel.getStyleClass().add("info"); displayIcon.getStyleClass().add("info"); displayIcon.setContent(IconsPath._60x.LAMP); break;
                case ERROR:titleLabel.getStyleClass().add("danger"); displayIcon.getStyleClass().add("danger"); displayIcon.setContent(IconsPath._60x.ERROR); break;
            }

            buttonClose.setGraphic(closeIcon);
            buttonClose.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            buttonAgree.setStyle("-fx-background-color: white; -fx-text-fill: primary;-fx-padding:10px;");
            buttonAgree.setPrefWidth(100.0);
            buttonAgree.setDefaultButton(true);
            buttonCancel.setPrefWidth(100.0);
            buttonCancel.setStyle("-fx-background-color: danger;-fx-padding:10px;");
            boxContent.setAlignment(Pos.CENTER);
            boxShadow.setAlignment(Pos.CENTER);
            buttonAgree.setOnAction(e-> {
                ViewManager.VIEW_ROOT.getChildren().remove(boxShadow);
                if (thread != null) thread.start();
            });


            buttonCancel.setOnAction(event ->
                    ViewManager.VIEW_ROOT.getChildren().remove(boxShadow));
            buttonClose.setOnAction(event ->
                    ViewManager.VIEW_ROOT.getChildren().remove(boxShadow));

            boxBody.getStyleClass().add("messageBox");
            boxBody.setAlignment(Pos.TOP_CENTER);
            boxFooteer.setAlignment(Pos.CENTER);
            boxFooteer.setSpacing(20.0);
            boxFooteer.getChildren().addAll(buttonAgree,buttonCancel);

            boxBody.setSpacing(20.0);
            boxBody.setMinWidth(300.0);
            boxBody.setPrefWidth(300.0);
            boxBody.setMaxWidth(300.0);
            boxBody.getChildren().add(displayIcon);
            boxContent.getChildren().add(titleLabel);
            boxContent.getChildren().add(messageLabel);
            boxBody.getChildren().add(boxContent);
            boxBody.getChildren().add(boxFooteer);

            try {
//                util.ViewManager.VIEW.showPopup(boxBody, Pos.CENTER, false, util.ViewManager.Animation.FADE);

                boxShadow.getChildren().add(boxBody);
                AnchorPane.setRightAnchor(boxShadow, 0.0);
                AnchorPane.setTopAnchor(boxShadow, 0.0);
                AnchorPane.setBottomAnchor(boxShadow, 0.0);
                AnchorPane.setLeftAnchor(boxShadow, 0.0);

                boxShadow.setStyle("-fx-background-color: rgba(0,0,0, 0.6)");

                if (!ViewManager.VIEW_ROOT.getChildren().contains(boxShadow))
                    ViewManager.VIEW_ROOT.getChildren().add(boxShadow);

                FadeIn fadeInUpBig = new FadeIn(boxBody);
                fadeInUpBig.play();
            }catch (Exception e) {
                System.err.println(e.getMessage());
            }

        }
    }

    public enum Type {
        WARNING,
        SUCCESS,
        INFO,
        ERROR
    }

}
