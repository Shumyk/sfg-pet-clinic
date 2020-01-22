package com.shumyk.sfgpetclinic.repository;

import com.shumyk.sfgpetclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
