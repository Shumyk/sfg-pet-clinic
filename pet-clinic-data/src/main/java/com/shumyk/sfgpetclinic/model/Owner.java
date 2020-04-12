package com.shumyk.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (nonNull(pets)) {
            this.pets = pets;
        }
    }

    @Column(name = "address") private String address;
    @Column(name = "city") private String city;
    @Column(name = "telephone") private String telephone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    public Pet getPet(final String name) {
        return getPet(name, false);
    }

    public Pet getPet(final String name, final boolean ignoreNew) {
        for (final Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                if (pet.getName().equalsIgnoreCase(name)) {
                    return pet;
                }
            }
        }
        return null;
    }
}
