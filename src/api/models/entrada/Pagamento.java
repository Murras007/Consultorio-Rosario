package api.models.entrada;

import api.models.Utilizador;
import api.models.enums.FormaPagamento;
import api.models.fornecedor.Fornecedor;

import java.util.List;

public class Pagamento {
    public Long id;
    public String status;
    public String timestamp;
    public FormaPagamento formaPagamento;
    public String local;
    public String referencia;
    public String moeda = "Kwanza";
    public String moedaSimbolo = "AOA";
    public Double valor;
    public Double cambio = 1.0;
}