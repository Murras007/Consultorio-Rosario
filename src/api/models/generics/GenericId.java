package api.models.generics;

import api.models.enums.Status;

import java.time.LocalDateTime;

public abstract class GenericId {


    private Long id = null;
    // default = activo
    private Status status = Status.ACTIVE;
    private LocalDateTime data = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}