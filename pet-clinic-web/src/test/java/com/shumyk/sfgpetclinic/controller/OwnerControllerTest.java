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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
	void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"))
				.andExpect(model().attributeExists("owner"));

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

	@Test void processFindFormReturnMany() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(asList(Owner.builder().id(32L).build(), Owner.builder().id(85L).build()));

		mockMvc.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownersList"))
			.andExpect(model().attribute("selections", hasSize(2)));
	}

	@Test void processFindFormReturnOne() throws Exception {
		doReturn(singletonList(Owner.builder().id(32L).build())).when(ownerService).findAllByLastNameLike(anyString());

		mockMvc.perform(get("/owners"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/32"));
	}

	@Test void initCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"))
			.andExpect(model().attributeExists("owner"));
		verifyNoInteractions(ownerService);
	}

	@Test void processCreationForm() throws Exception {
		doReturn(Owner.builder().id(1L).build()).when(ownerService).save(any());

		mockMvc.perform(post("/owners/new"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"))
			.andExpect(model().attributeExists("owner"));

		verify(ownerService).save(any());
	}

	@Test void initUpdateOwnerForm() throws Exception {
		doReturn(Owner.builder().id(1L).build()).when(ownerService).findById(1L);

		mockMvc.perform(get("/owners/1/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"))
			.andExpect(model().attributeExists("owner"));

		verify(ownerService).findById(1L);
	}

	@Test void processUpdateOwnerForm() throws Exception {
		doReturn(Owner.builder().id(1L).build()).when(ownerService).save(any());

		mockMvc.perform(post("/owners/1/edit"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"))
			.andExpect(model().attributeExists("owner"));

		verify(ownerService).save(any());
	}

}