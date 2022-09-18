package api.models.produto;

import api.models.Utilizador;
import api.models.enums.Status;
import views.pages.home.HomeController;

public class Categoria{
    public int id;
    public Status status;
    public Utilizador feito_por =  HomeController.login;
    public String designacao;
    public String descricao;

    @Override
    public String toString() {
        return designacao;
    }
}