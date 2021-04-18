package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = Pet.class, cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Pet> pets;

    @Nationalized // should use @Nationalized instead of @Type=nstring
    private String name;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(name = "notes", length = 500)
    private String notes;

    public void addPet(Pet pet) {
        if(pets == null){
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }

    //constructors

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
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}
