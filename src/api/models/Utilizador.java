package api.models;

import api.models.enums.Perfil;
import api.models.enums.Status;

import java.io.Serializable;

public class Utilizador implements Serializable {
    public int id;
    public Status status;
    public String username;
    public String nome;
    public String password;
    public Perfil perfil;

    public Utilizador(Status status, String username, String password) {
        this.status = status;
        this.username = username;
        this.password = password;
    }

    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }
    @Override
    public String toString() {
        return nome;
    }
}
