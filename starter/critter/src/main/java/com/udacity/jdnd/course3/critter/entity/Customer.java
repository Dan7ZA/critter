package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Customer extends User{

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @Column(name = "notes", length = 500)
    private String notes;

    @OneToMany(mappedBy = "owner", targetEntity = Pet.class)
    private List<Pet> pets;

    //constructors

    //getters & setters

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
