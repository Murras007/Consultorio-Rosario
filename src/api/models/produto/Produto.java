package api.models.produto;

import api.models.Utilizador;
import api.models.enums.FormaSaida;
import api.models.enums.UnidadeMedida;
import views.pages.home.HomeController;

public class Produto {
    public Long id;
    public String status;
    public String timestamp;
    public Utilizador feitoPor = HomeController.login;
    public String designacao;
    public String descricao;
    public String logo;
    public Categoria categoria;
    public UnidadeMedida unidade;
    public FormaSaida formaSaida;
    public String nomeAlternativo;

    @Override
    public String toString() {
        return designacao;
    }
}