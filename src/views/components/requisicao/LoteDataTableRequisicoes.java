package views.components.requisicao;

import api.models.entrada.Lote;
import api.models.stock.Requisicao;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import util.Alert;
import util.StringUtil;
import views.components.dynamic.datatable.AbstractDynamicDataTable;
import views.components.dynamic.datatable.DynamicColumn;

import java.util.List;

public class LoteDataTableRequisicoes extends AbstractDynamicDataTable<Requisicao> {

    public LoteDataTableRequisicoes(List<Requisicao> data){

        super("REQUISIÇÕES", data, 5,
                new DynamicColumn("id","ID"),
                new DynamicColumn("departamento","DEPARTAMENTO"),
                new DynamicColumn("solicitante","SOLICITANTE"),
                new DynamicColumn("status","ESTADO")
        );
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
