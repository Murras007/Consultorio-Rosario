package api.models.entrada;

import api.models.Utilizador;
import api.models.fornecedor.Fornecedor;
import views.pages.home.HomeController;

import java.util.List;

public class Entrada {
    public Long id;
    public String status;
    public String timestamp;
    public String dataFatura;
    public FormaEntrada formaEntrada;
    public Utilizador feitoPor = HomeController.login;
    public Fornecedor fornecedor;
    public String numeroFatura;
    public List<Lote> lotes;
    public List<Pagamento> pagamentos;
    public Double total;
    public Double desconto;
}