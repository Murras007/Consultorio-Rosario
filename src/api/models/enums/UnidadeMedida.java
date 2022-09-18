package api.models.enums;

public enum UnidadeMedida {
    UNI("Uni."),
    G("G"),
    MG("Mg"),
    KG("Kg"),
    ML("ml"),
    L("l"),
    KL("Kl"),
    CM3("cm3"),
    CM("cm"),
    KM_H("Km/h"),
    M_S("m/s"),
    C("c"),
    Cal("Cal"),
    S("s"),
    M("m"),
    H("h"),
    Temp("Temp"),
    T("T"),
    VOLT("V"),
    AMPERE("A"),
    GHZ("Ghz"),
    DECIBEZ("Dec.");

    private String nome;
    private UnidadeMedida(String nome){
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}