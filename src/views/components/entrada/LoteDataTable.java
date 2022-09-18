package views.components.entrada;

import api.models.entrada.Lote;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import util.Alert;
import util.StringUtil;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;

import java.util.List;

public class LoteDataTable extends AbstractDynamicDataTable<Lote> {

    public LoteDataTable(List<Lote> data){

        super("LOTES DE ENTRADA", data, 5,
                new DynamicColumn("armazem","ARMAZÉM"),
                new DynamicColumn("produto","PRODUTO"),
                new DynamicColumn("numeroLote","LOTE"),
                new DynamicColumn("referencia","REFERÊNCIA"),
                new DynamicColumn("fabricante","FABRICANTE"),
                new DynamicColumn("dataValidade","DATA DE VALIDADE"),
                new DynamicColumn("quantidade","QUANTIDADE COMPRADO")
        );
    }


    @Override
    protected void sortByColumn(JFXButton columnBtn) {

    }

    @Override
    protected boolean filterCondition(Lote lote, String search) {
        return String.valueOf(lote.id).contains(search) ||
                lote.armazem.designacao.toLowerCase().contains(search.toLowerCase()) ||
                lote.armazem.descricao.toLowerCase().contains(search.toLowerCase()) ||
                lote.produto.designacao.toLowerCase().contains(search.toLowerCase()) ||
                lote.dataValidade.toLowerCase().contains(search.toLowerCase()) ||
                StringUtil.getNumberFormatted(lote.quantidade).toLowerCase().contains(search.toLowerCase()) ||
                lote.fabricante.toLowerCase().contains(search.toLowerCase()) ||
                lote.numeroLote.toLowerCase().contains(search.toLowerCase()) ||
               lote.referencia.contains(search.toLowerCase());
    }

    @Override
    public void editItem(Lote lote) {
        EntradaController.loteSelected = lote;
        EntradaController.lotes.remove(lote);
        EntradaController.loadLote();
        refresh();
    }

    @Override
    public void deleteItem(Lote lote) {
        Alert.Message.thread = new Thread(
                ()-> Platform.runLater(() ->  {
                    EntradaController.loteSelected = null;
                    EntradaController.lotes.remove(lote);
                    EntradaController.loadLote();
                    refresh();
                }));

        Alert.Message.show("Eliminar item","Pretende continuar com a remoção?", Alert.Type.WARNING);

    }
}
