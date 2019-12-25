package com.shumyk.sfgpetclinic.service;

import com.shumyk.sfgpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
