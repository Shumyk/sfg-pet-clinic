package com.shumyk.sfgpetclinic.service.map;

import com.shumyk.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

	private OwnerMapService ownerMapService;

	private final static Long OWNER_ID = 50L;
	private final static String LAST_NAME = "Husak";

	@BeforeEach
	void setUp() {
		ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
		ownerMapService.save(Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build());
	}

	@Test
	void findAll() {
		final Set<Owner> ownerSet = ownerMapService.findAll();

		assertEquals(1, ownerSet.size());
	}

	@Test
	void findById() {
		final Owner owner = ownerMapService.findById(OWNER_ID);

		assertEquals(OWNER_ID, owner.getId());
	}

	@Test
	void deleteById() {
		assertEquals(1, ownerMapService.findAll().size());
		ownerMapService.deleteById(OWNER_ID);
		assertEquals(0, ownerMapService.findAll().size());
	}

	@Test
	void delete() {
		assertEquals(1, ownerMapService.findAll().size());
		ownerMapService.delete(ownerMapService.findById(OWNER_ID));
		assertEquals(0, ownerMapService.findAll().size());
	}

	@Test
	void save() {
		final long id = 2L;
		final Owner owner = Owner.builder().id(id).build();
		final Owner savedOwner = ownerMapService.save(owner);

		assertEquals(id, savedOwner.getId());
	}

	@Test
	void saveNoId() {
		final Owner savedOwner = ownerMapService.save(Owner.builder().build());
		assertNotNull(savedOwner);
		assertNotNull(savedOwner.getId());
	}

	@Test
	void findByLastName() {
		final Owner owner = ownerMapService.findByLastName(LAST_NAME);
		assertNotNull(owner);
		assertEquals(OWNER_ID, owner.getId());
	}

	@Test
	void findByLastNameNotFound() {
		final Owner notFound = ownerMapService.findByLastName("WRONG LAST NAME");
		assertNull(notFound);
	}
}