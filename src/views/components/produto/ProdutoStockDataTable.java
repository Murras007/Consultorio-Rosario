package views.components.produto;

import api.controller.produto.ProdutoControllerApi;
import api.models.stock.StockProduto;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.IconsPath;
import util.Routes;
import util.StringUtil;
import util.ViewManager;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;

import java.util.List;
import java.util.Map;

public class ProdutoStockDataTable extends AbstractDynamicDataTable<StockProduto> {

    public ProdutoStockDataTable(List<StockProduto> data){

        super("STOCK DOS PRODUTOS", data, 5,
                new DynamicColumn("lote","LOTE"),
                new DynamicColumn("produto","ARTIGO"),
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

    protected void history(StockProduto produto) {
            try{
               ProdutoStockHistoricoController.lote = produto.looter;
               ViewManager.Manager.showModal(Routes.Component.Produto.HISTORICO, Pos.CENTER,false);
            }catch(Exception exception){
                exception.printStackTrace();
            }
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
                Label cell = new Label("");
                if (item.get(column.getKey()) != null)
                     cell.setText(String.valueOf(getItem(item.get(column.getKey()).toString())));

                cell.setAlignment(Pos.CENTER);
                row.getChildren().add(cell);
            }

            JFXButton entryButton = new JFXButton();

            entryButton.setGraphic(IconsPath.iconFrom(IconsPath._18x.HISTORY));

            entryButton.getStyleClass().add("data-table-row-btn");
            entryButton.getStyleClass().add("data-table-row-edit-btn");
            HBox actionContainer = new HBox();
            // Action
            actionContainer.setAlignment(Pos.CENTER);
            final StockProduto element = displayedData.get(index++);
            entryButton.setOnAction(e -> history(element));
            actionContainer.getChildren().add(entryButton);
            row.getChildren().add(actionContainer);
            listContent.getChildren().add(row);
        }
        if (listContent.getChildren().isEmpty()){
            notFound();
        }
    }
}
