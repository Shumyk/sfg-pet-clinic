package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.model.Pet;
import com.shumyk.sfgpetclinic.model.PetType;
import com.shumyk.sfgpetclinic.service.OwnerService;
import com.shumyk.sfgpetclinic.service.PetService;
import com.shumyk.sfgpetclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

	@Mock private PetService petService;
	@Mock private OwnerService ownerService;
	@Mock private PetTypeService petTypeService;
	@InjectMocks private PetController petController;

	private MockMvc mockMvc;
	private Owner owner;
	private Set<PetType> petTypes;

	@BeforeEach void setUp() {
		owner = Owner.builder().id(1L).build();

		petTypes = new HashSet<>();
		petTypes.add(PetType.builder().id(1L).name("Dog").build());
		petTypes.add(PetType.builder().id(2L).name("Cat").build());

		mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
	}

	@Test void initCreationForm() throws Exception {
		doReturn(owner).when(ownerService).findById(anyLong());
		doReturn(petTypes).when(petTypeService).findAll();

		mockMvc.perform(get("/owners/1/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test void processCreationForm() throws Exception {
		doReturn(owner).when(ownerService).findById(anyLong());
		doReturn(petTypes).when(petTypeService).findAll();

		mockMvc.perform(post("/owners/1/pets/new"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));

		verify(petService).save(any());
	}

	@Test void initUpdateForm() throws Exception {
		doReturn(owner).when(ownerService).findById(anyLong());
		doReturn(petTypes).when(petTypeService).findAll();
		doReturn(Pet.builder().id(2L).build()).when(petService).findById(2L);

		mockMvc.perform(get("/owners/1/pets/2/edit"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));

		verify(petService).findById(2L);

	}

	@Test void processUpdateForm() throws Exception {
		doReturn(owner).when(ownerService).findById(anyLong());
		doReturn(petTypes).when(petTypeService).findAll();

		mockMvc.perform(post("/owners/1/pets/2/edit"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));
	}
}
