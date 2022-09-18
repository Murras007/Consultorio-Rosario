package views.components.dynamic.datatable;

public class DynamicColumn {
    private int index;
    private String key;
    private String name;
    private boolean editable = false;
    public DynamicColumn(String key, String name) {
        this.key = key;
        this.name = name;
    }
    public DynamicColumn(String key, String name, boolean editable) {
        this.key = key;
        this.name = name;
        this.editable = editable;
    }
    public DynamicColumn(String key) {
        this.key = key;
        this.name = key.toUpperCase();
    }

    public DynamicColumn(int index, String key, String name) {
        this.index = index;
        this.key = key;
        this.name = name;
    }

    public DynamicColumn(int index, String key, String name, boolean editable) {
        this.index = index;
        this.key = key;
        this.name = name;
        this.editable = editable;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
