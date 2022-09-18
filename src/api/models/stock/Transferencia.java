package api.models.stock;

import api.models.Utilizador;
import api.models.armazem.Armazem;
import api.models.enums.Status;
import views.pages.home.HomeController;

import java.util.List;

public class Transferencia {

    public Long id = null;
    public Status status;
    public Utilizador feito_por = HomeController.login;
    public Armazem armazemOrigem;
    public Armazem armazemDestino;
    public List<Stock> stockMovimentos;
}
