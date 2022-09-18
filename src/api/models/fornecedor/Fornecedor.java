package api.models.fornecedor;

import api.models.Utilizador;
import api.models.enums.Perfil;
import api.models.enums.Status;
import views.pages.home.HomeController;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Fornecedor implements Serializable {
    public int id;
    public Status status;
    public Utilizador utilizador =  HomeController.login;
    public String numero;
    public String nome;
    public String apelido;
    public String email;
    public String nif = "999999999";
    public String telefone;
    public String pais;
    public String cidade;
    public String rua;
    public String provincia;

    @Override
    public String toString() {
        return  nome +" " + apelido;
    }
}
