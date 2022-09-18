package api.models.armazem;

import api.models.Utilizador;
import api.models.enums.Status;
import views.pages.home.HomeController;

public class Armazem {

    public Long id;
    public Status status;
    public String timestamp;
    public Utilizador feito_por = HomeController.login;
    public String designacao;
    public String descricao;


    public Armazem() {
    }

    public Armazem(Long id, Status status, String timestamp, Utilizador feito_por, String designacao, String descricao) {
        this.id = id;
        this.status = status;
        this.timestamp = timestamp;
        this.feito_por = feito_por;
        this.designacao = designacao;
        this.descricao = descricao;
    }

    public Armazem(String designacao, String descricao) {
        this.designacao = designacao;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return designacao;
    }
}
