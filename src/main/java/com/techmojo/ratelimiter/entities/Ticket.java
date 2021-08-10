package com.techmojo.ratelimiter.entities;

import java.io.Serializable;

public class Ticket implements Serializable {

    private final long id;
    private final long duration;
    private final String description;

    public Ticket(long id, long duration, String description) {
        this.id = id;
        this.duration = duration;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", duration='" + duration + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
