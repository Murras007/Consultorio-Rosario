package api.models.entrada;

import api.models.Utilizador;
import api.models.armazem.Armazem;
import api.models.produto.Produto;
import views.pages.home.HomeController;

public class Lote {
    public Long id;
    public String status;
    public String timestamp;
    public Utilizador feitoPor = HomeController.login;
    public Produto produto;
    public String fabricante;
    public String numeroLote;
    public String referencia;
    public Armazem armazem;
    public Double precoCompra;
    public Double preco;
    public String dataValidade;
    public Double quantidade;

    public Double margemLucro;
    public Double totalLucro;
    public Double totalCusto;


    @Override
    public String toString() {
        return numeroLote;
    }
}