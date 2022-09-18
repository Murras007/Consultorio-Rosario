package views.components.armazem;

import api.controller.ArmazemControllerApi;
import api.models.armazem.Armazem;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.geometry.Pos;
import util.Alert;
import util.Routes;
import util.ViewManager;
import views.pages.armazem.ArmazemController;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;

import java.util.List;

public class ArmazemDataTable extends AbstractDynamicDataTable<Armazem> {

    public ArmazemDataTable(List<Armazem> data){

        super("ARMAZÉM", data, 5,
                new DynamicColumn("id","ID"),
                new DynamicColumn("designacao","DESIGNAÇÃO"),
                new DynamicColumn("descricao","DESCRIÇÃO"));
    }


    @Override
    protected void sortByColumn(JFXButton columnBtn) {

    }

    @Override
    protected boolean filterCondition(Armazem armazem, String search) {
        return String.valueOf(armazem.id).contains(search) ||
                armazem.designacao.toLowerCase().contains(search.toLowerCase()) ||
                armazem.descricao.toLowerCase().contains(search.toLowerCase());
    }

    @Override
    public void editItem(Armazem armazem) {
        try {
            ArmazemEditorController.armazem = armazem;
            ViewManager.Manager.showModal(Routes.Component.Armazem.EDITOR, Pos.CENTER,true );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteItem(Armazem armazem) {
        Alert.Message.thread = new Thread(
                ()-> Platform.runLater(() -> {
                    try {
                        ArmazemControllerApi api = new ArmazemControllerApi();
                        api.delete(armazem);
                        new Thread(ArmazemController.refresh).start();
                    }catch (Exception ex) {
                        Alert.Toast.showToast("Impossível deletar este armazém", Alert.Type.WARNING);
                    }
                } ));
        Alert.Message.show("Eliminar armazém","Está prestes a eliminar o armazém "+armazem.designacao, Alert.Type.WARNING);
    }
}
