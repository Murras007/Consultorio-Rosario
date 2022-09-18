package views.components.requisicao;

import api.models.enums.Direction;
import api.models.stock.Requisicao;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import util.Alert;
import util.IconsPath;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;

import java.util.List;
import java.util.Map;

public class AdmDataTableRequisicoes extends AbstractDynamicDataTable<Requisicao> {

    public AdmDataTableRequisicoes(List<Requisicao> data){
        super("REQUISIÇÕES", data, 5,
                new DynamicColumn("id","ID"),
                new DynamicColumn("departamento","DEPARTAMENTO"),
                new DynamicColumn("solicitante","SOLICITANTE"),
                new DynamicColumn("status","ESTADO")
        );
    }



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

                cell.setTooltip(new Tooltip(val));
                cell.setAlignment(Pos.CENTER);
                row.getChildren().add(cell);
            }

            final HBox rowAction = new HBox();
            rowAction.setAlignment(Pos.CENTER);


            JFXButton editButton = new JFXButton();
            JFXButton deleteButton = new JFXButton();

            editButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.APROVE));
            deleteButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.CLOSE));

            editButton.getStyleClass().add("data-table-row-btn");
            editButton.getStyleClass().add("data-table-row-edit-btn");

            deleteButton.getStyleClass().add("data-table-row-btn");
            deleteButton.getStyleClass().add("data-table-row-delete-btn");


            // Action
            final Requisicao element = displayedData.get(index++);
            editButton.setOnAction(e -> editItem(element));
            deleteButton.setOnAction(e -> deleteItem(element));

            rowAction.getChildren().addAll(editButton, deleteButton);
            row.getChildren().add(rowAction);
            listContent.getChildren().add(row);
        }
        if (listContent.getChildren().isEmpty()){
            notFound();
        }
    }
    @Override
    protected void sortByColumn(JFXButton columnBtn) {

    }

    @Override
    protected boolean filterCondition(Requisicao lote, String search) {
        return true;
    }

    @Override
    public void editItem(Requisicao lote) {
        refresh();
    }

    @Override
    public void deleteItem(Requisicao lote) {

        Alert.Message.show("Eliminar item","Pretende continuar com a remoção?", Alert.Type.WARNING);

    }
}
