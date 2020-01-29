package com.shumyk.sfgpetclinic.service.springdatajpa;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSdJpaServiceTest {

	public static final String SMITH = "Smith";
	public static final Owner RETURN_OWNER = Owner.builder().id(1L).lastName(SMITH).build();

	@InjectMocks private OwnerSdJpaService service;
	@Mock	private OwnerRepository ownerRepository;


	@BeforeEach
	void setUp() {
	}

	@Test
	void findByLastName() {
		when(ownerRepository.findByLastName(any())).thenReturn(RETURN_OWNER);

		final Owner smith = service.findByLastName(SMITH);
		assertNotNull(smith);
		assertEquals(1L, smith.getId());
		assertEquals(SMITH, smith.getLastName());

		verify(ownerRepository).findByLastName(SMITH);
	}

	@Test
	void findAll() {
		final Set<Owner> returnOwnersSet = Sets.newSet(
				Owner.builder().id(1L).build(),
				Owner.builder().id(2L).build()
		);

		when(ownerRepository.findAll()).thenReturn(returnOwnersSet);

		final Set<Owner> resultSet = service.findAll();
		assertNotNull(resultSet);
		assertEquals(2, resultSet.size());
	}

	@Test
	void findById() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(RETURN_OWNER));

		final Owner owner = service.findById(1L);
		assertNotNull(owner);
		assertEquals(1L, owner.getId());
		assertEquals(SMITH, owner.getLastName());
	}

	@Test
	void findByIdNotFound() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

		final Owner owner = service.findById(1L);
		assertNull(owner);
	}

	@Test
	void save() {
		final Owner ownerToSave = Owner.builder().id(1L).build();
		when(ownerRepository.save(ownerToSave)).thenReturn(RETURN_OWNER);

		final Owner resultOwner = service.save(ownerToSave);
		assertNotNull(resultOwner);

		verify(ownerRepository).save(ownerToSave);
	}

	@Test
	void delete() {
		service.delete(RETURN_OWNER);
		verify(ownerRepository).delete(RETURN_OWNER);
	}

	@Test
	void deleteById() {
		service.deleteById(1L);
		verify(ownerRepository).deleteById(1L);
	}
}