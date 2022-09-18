package api.models.stock;

import api.models.Utilizador;
import api.models.entrada.Lote;
import api.models.enums.Direction;
import api.models.enums.Status;
import api.models.enums.StockMovimento;
import util.IconsPath;
import views.pages.home.HomeController;

public class StockProduto {

    public Long id = null;
    public String lote;
    public Lote looter;
    public String produto;
    public Long produto_id;
    public Long armazem_id;
    public String referencia;
    public String armazem;
    public Double quantidade;
    public StockMovimento movimento;
    public Direction sinal;
    public Utilizador feito_por =  HomeController.login;
    public Double stockAnterior;
    public Double stockActual;
}
