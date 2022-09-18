package api.models.entrada;

import api.models.Utilizador;
import api.models.enums.Status;
import views.pages.home.HomeController;

public class FormaEntrada {

    public Long id;
    public Status status;
    public String timestamp;
    public Utilizador feito_por = HomeController.login;
    public String designacao;
    public String descricao;


    public FormaEntrada() {
    }

    public FormaEntrada(Long id, Status status, String timestamp, Utilizador feito_por, String designacao, String descricao) {
        this.id = id;
        this.status = status;
        this.timestamp = timestamp;
        this.feito_por = feito_por;
        this.designacao = designacao;
        this.descricao = descricao;
    }

    public FormaEntrada(String designacao, String descricao) {
        this.designacao = designacao;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return designacao;
    }
}
