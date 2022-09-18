package api.models;

import api.models.enums.Status;

import java.time.LocalDateTime;

public class AbstractModel extends AbstractId{

    private String designation;
    private String description;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
