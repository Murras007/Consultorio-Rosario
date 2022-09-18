package api.models.enums;

public enum FormaSaida {
    FIFO("First In First Out"),
    LIFO("Last In First Out"),
    DATA_EXPIRACAO("Data de Expiração"),
    PRECO_ASCENDENTE("Preço Ascendente"),
    PRECO_DESCENDENTE("Preço Descedente");

    private final String nome;
    FormaSaida(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}