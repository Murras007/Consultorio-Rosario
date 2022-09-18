package api.models.enums;

public enum Direction {
    UP("▲"),
    DOWN("▼"),
    STABLE("▬");
    final String dir;
    Direction (String dir){
        this.dir = dir;
    }

    @Override
    public String toString() {
        return dir;
    }
}
