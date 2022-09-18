package api.models.enums;

public enum Perfil {
    Administrator("Administrador"),
    Operator("Operador"),
    Visitant("Visitante"),
    Assistent("Assistente"),
    Public("Público"),
    Client("Cliente");
    private String name;

    Perfil(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
