package views.components.produto;

import api.controller.produto.ProdutoControllerApi;
import api.models.enums.Direction;
import api.models.produto.Produto;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.Alert;
import util.IconsPath;
import util.Routes;
import util.ViewManager;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;
import views.components.entrada.EntradaController;
import views.pages.produto.ProdutoController;

import java.util.List;
import java.util.Map;

public class ProdutoDataTable extends AbstractDynamicDataTable<Produto> {

    public ProdutoDataTable(List<Produto> data){

        super("PRODUTO", data, 5,
                new DynamicColumn("id","ID"),
                new DynamicColumn("designacao","DESIGNAÇÃO"),
                new DynamicColumn("descricao","DESCRIÇÃO"),
                new DynamicColumn("categoria","CATEGORIA"),
                new DynamicColumn("unidade","UNIDADE"),
                new DynamicColumn("formaSaida","FORMA DE SAÍDA")
        );
    }


    @Override
    protected void sortByColumn(JFXButton columnBtn) {

    }

    @Override
    protected boolean filterCondition(Produto produto, String search) {
        return String.valueOf(produto.id).contains(search) ||
                String.valueOf(produto.designacao).toLowerCase().contains(search.toLowerCase()) ||
                String.valueOf(produto.feitoPor).toLowerCase().contains(search.toLowerCase()) ||
                String.valueOf(produto.categoria).toLowerCase().contains(search.toLowerCase()) ||
                String.valueOf(produto.unidade).toLowerCase().contains(search.toLowerCase()) ||
                String.valueOf(produto.formaSaida).toLowerCase().contains(search.toLowerCase()) ||
                String.valueOf(produto.descricao).toLowerCase().contains(search.toLowerCase());
    }

    @Override
    public void editItem(Produto produto) {
        try {
            ProdutoEditorController.produto = produto;
            ViewManager.Manager.showModal(Routes.Component.Produto.EDITOR, Pos.CENTER,true );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void entryItem(Produto produto) {
        try {
            EntradaController.produto = produto;
            ViewManager.Manager.showModal(Routes.Component.Entrada.EDITOR, Pos.CENTER,true );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteItem(Produto produto) {
        Alert.Message.thread = new Thread(
                ()-> Platform.runLater(() -> {
                    try {
                        ProdutoControllerApi api = new ProdutoControllerApi();
                        api.delete(produto);
                        new Thread(ProdutoController.refresh).start();
                    }catch (Exception ex) {
                        Alert.Toast.showToast("Impossível deletar este produto", Alert.Type.WARNING);
                    }
                } ));
        Alert.Message.show("Eliminar produto","Está prestes a eliminar o produto "+produto.designacao, Alert.Type.WARNING);
    }

//    SUBSCREVER O SIMPLE DATA


    @Override
    public void simpleTable() {
        listContent.getChildren().clear();
        int index = 0;
        for(Map<String, Object> item : columnData){
            final HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.getStyleClass().add("data-table-row");

            for(DynamicColumn column: columns){

                final String val = String.valueOf(getItem(item.get(column.getKey())));
                Label cell = new Label(val);
                if(val.equals(Direction.UP.toString()))
                    cell.setStyle("-fx-text-fill:#0e44ff");
                else if (val.equals(Direction.DOWN.toString()))
                    cell.setStyle("-fx-text-fill:danger");


                cell.setAlignment(Pos.CENTER);
                row.getChildren().add(cell);
            }

            final HBox rowAction = new HBox();
            rowAction.setAlignment(Pos.CENTER);

            JFXButton editButton = new JFXButton();
            JFXButton deleteButton = new JFXButton();
            JFXButton entryButton = new JFXButton();

            editButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.EDIT));
            deleteButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.DELETE));
            entryButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.ENTER));

            editButton.getStyleClass().add("data-table-row-btn");
            editButton.getStyleClass().add("data-table-row-edit-btn");

            entryButton.getStyleClass().add("data-table-row-btn");
            entryButton.getStyleClass().add("data-table-row-edit-btn");

            deleteButton.getStyleClass().add("data-table-row-btn");
            deleteButton.getStyleClass().add("data-table-row-delete-btn");

            // Action
            final Produto element = displayedData.get(index++);
            editButton.setOnAction(e -> editItem(element));
            deleteButton.setOnAction(e -> deleteItem(element));
            entryButton.setOnAction(e -> entryItem(element));
            rowAction.getChildren().addAll(entryButton,editButton, deleteButton);
            row.getChildren().add(rowAction);
            listContent.getChildren().add(row);
        }
        if (listContent.getChildren().isEmpty()){
            notFound();
        }
    }
}
