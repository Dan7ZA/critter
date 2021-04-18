package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

 /*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized // should use @Nationalized instead of @Type=nstring
    private String name;

    //getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/
}
