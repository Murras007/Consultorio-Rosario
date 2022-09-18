package views.components.requisicao;

import api.models.entrada.Lote;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import util.Alert;
import util.StringUtil;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;

import java.util.List;

public class LoteDataTableRequisicao extends AbstractDynamicDataTable<Lote> {

    public LoteDataTableRequisicao(List<Lote> data){

        super("REQUISIÇÕES", data, 5,
                new DynamicColumn("produto","PRODUTO"),
                new DynamicColumn("referencia","REFERÊNCIA"),
                new DynamicColumn("fabricante","FABRICANTE"),
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
                lote.fabricante.contains(search.toLowerCase()) ||
                StringUtil.getNumberFormatted(lote.precoCompra).contains(search.toLowerCase()) ||
                StringUtil.getNumberFormatted(lote.preco).contains(search.toLowerCase()) ||
                StringUtil.getNumberFormatted(lote.margemLucro).contains(search.toLowerCase()) ||
               lote.referencia.contains(search.toLowerCase());
    }

    @Override
    public void editItem(Lote lote) {
        Controller.loteSelected = lote;
        Controller.lotes.remove(lote);
        Controller.loadLote();
        refresh();
    }

    @Override
    public void deleteItem(Lote lote) {
        Alert.Message.thread = new Thread(
                ()-> Platform.runLater(() ->  {
                    Controller.loteSelected = null;
                    Controller.lotes.remove(lote);
                    Controller.loadLote();
                    refresh();
                }));

        Alert.Message.show("Eliminar item","Pretende continuar com a remoção?", Alert.Type.WARNING);

    }
}
