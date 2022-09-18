package api.models.stock;

import api.models.Utilizador;
import api.models.armazem.Armazem;
import api.models.entrada.Lote;
import api.models.enums.Status;
import api.models.enums.StockMovimento;
import views.pages.home.HomeController;

import java.util.List;

public class Requisicao {

    public Long id;
    public String status;
    public String timestamp;
    public Armazem departamento;
    public Utilizador solicitante = HomeController.login;
    public List<Lote> lotes;
    public String designacao;


}
