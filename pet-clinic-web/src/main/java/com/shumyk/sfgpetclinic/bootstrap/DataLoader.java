package com.shumyk.sfgpetclinic.bootstrap;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.model.Vet;
import com.shumyk.sfgpetclinic.service.OwnerService;
import com.shumyk.sfgpetclinic.service.VetService;
import com.shumyk.sfgpetclinic.service.map.OwnerMapService;
import com.shumyk.sfgpetclinic.service.map.VetMapService;
import org.springframework.boot.CommandLineRunner;

public class DataLoader  implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerMapService();
        this.vetService = new VetMapService();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");

        ownerService.save(owner2);

        System.out.println("Loaded Owners...");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
