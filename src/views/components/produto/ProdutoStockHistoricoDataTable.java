package views.components.produto;

import api.controller.produto.ProdutoControllerApi;
import api.models.Utilizador;
import api.models.enums.Direction;
import api.models.enums.StockMovimento;
import api.models.stock.StockProduto;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import util.IconsPath;
import util.Routes;
import util.StringUtil;
import util.ViewManager;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;
import views.pages.home.HomeController;

import java.util.List;
import java.util.Map;

public class ProdutoStockHistoricoDataTable extends AbstractDynamicDataTable<StockProduto> {

    public ProdutoStockHistoricoDataTable(List<StockProduto> data){

        super("STOCK DOS PRODUTOS", data, 5,
                new DynamicColumn("lote","LOTE"),
                new DynamicColumn("referencia","REFERÊNCIA"),
                new DynamicColumn("armazem","ARMAZÉM"),
                new DynamicColumn("feito_por","UTILIZADOR"),
                new DynamicColumn("stockAnterior","QTD. ANTERIOR"),
                new DynamicColumn("quantidade","QTD. MOVIMENTO"),
                new DynamicColumn("stockActual","QTD. POSTERIOR"),
                new DynamicColumn("movimento","TIPO MOVIMENTO"),
                new DynamicColumn("sinal","MOVIMENTO")
        );
    }


    @Override
    protected void sortByColumn(JFXButton columnBtn) {

    }

    @Override
    protected boolean filterCondition(StockProduto produto, String search) {
        return
                produto.produto.toLowerCase().contains(search.toLowerCase())||
                produto.referencia.toLowerCase().contains(search.toLowerCase())||
                String.valueOf(produto.id).toLowerCase().contains(search.toLowerCase())||
                        StringUtil.getNumberFormatted(produto.quantidade).toLowerCase().contains(search.toLowerCase())||
                        StringUtil.getNumberFormatted(produto.stockActual).toLowerCase().contains(search.toLowerCase())||
                        StringUtil.getNumberFormatted(produto.stockAnterior).toLowerCase().contains(search.toLowerCase())
                ;
    }

    @Override
    public void editItem(StockProduto stockProduto) {

    }

    @Override
    public void deleteItem(StockProduto stockProduto) {

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


            // Action

            listContent.getChildren().add(row);
        }
        if (listContent.getChildren().isEmpty()){
            notFound();
        }
    }

    @Override
    protected void configColumns(){
        final HBox row = new HBox();
        row.getStyleClass().add("data-table-column");

        for(DynamicColumn column: columns) {
            JFXButton columnBtn = new JFXButton(column.getName().toUpperCase());
            columnBtn.setTooltip(new Tooltip(column.getName()));
            columnBtn.setOnAction( e -> sortByColumn((JFXButton)e.getSource()));
            row.getChildren().add(columnBtn);
        }
        borderContent.setTop(row);
        simpleTable();
    }
}
