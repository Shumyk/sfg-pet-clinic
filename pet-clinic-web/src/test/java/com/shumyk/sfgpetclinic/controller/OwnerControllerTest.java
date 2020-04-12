package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.service.OwnerService;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	@InjectMocks OwnerController ownerController;
	@Mock OwnerService ownerService;

	private MockMvc mockMvc;

	private Set<Owner> ownerSet;

	@BeforeEach
	void setUp() {
		ownerSet = CollectionHelper.asSet(
				Owner.builder().id(1L).build(),
				Owner.builder().id(2L).build()
		);

		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}

	@Test
	void listOwners() throws Exception {
		when(ownerService.findAll()).thenReturn(ownerSet);

		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}

	@Test
	void listOwnersByIndex() throws Exception {
		when(ownerService.findAll()).thenReturn(ownerSet);

		mockMvc.perform(get("/owners/index"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}

	@Test
	void listOwnersByIndexHtml() throws Exception {
		when(ownerService.findAll()).thenReturn(ownerSet);

		mockMvc.perform(get("/owners/index.html"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/index"))
				.andExpect(model().attribute("owners", hasSize(2)));
	}

	@Test
	void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("notImplemented"));

		verifyNoInteractions(ownerService);
	}

	@Test
	void displayOwner() throws Exception {
		when(ownerService.findById(121L)).thenReturn(Owner.builder().id(121L).build());

		mockMvc.perform(get("/owners/121"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownerDetails"))
			.andExpect(model().attribute("owner", hasProperty("id", is(121L))));
	}

}