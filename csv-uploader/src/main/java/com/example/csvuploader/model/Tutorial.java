package com.example.csvuploader.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tutorials")
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rowNum")
    private long number;

    @Column(name = "id")
    private String id;

    public Tutorial() {

    }

    public Tutorial(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tutorial [id=" + id + ", number=" + number + "]";
    }

}
