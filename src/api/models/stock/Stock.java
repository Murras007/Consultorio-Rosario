package api.models.stock;

import api.models.Utilizador;
import api.models.entrada.Lote;
import api.models.enums.Status;
import api.models.enums.StockMovimento;
import views.pages.home.HomeController;

public class Stock {

    public Long id = null;
    public Status status;
    public Lote lote;
    public Double quantidade;
    public StockMovimento movimento;
    public String descricao;
    public Utilizador feito_por = HomeController.login;
    public Double stockAnterior;
    public Double stockActual;
}
