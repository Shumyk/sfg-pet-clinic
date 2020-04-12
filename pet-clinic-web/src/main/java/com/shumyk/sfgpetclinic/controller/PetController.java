package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.model.Pet;
import com.shumyk.sfgpetclinic.model.PetType;
import com.shumyk.sfgpetclinic.service.OwnerService;
import com.shumyk.sfgpetclinic.service.PetService;
import com.shumyk.sfgpetclinic.service.PetTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static java.util.Objects.nonNull;

@Controller
@AllArgsConstructor
@RequestMapping("/owners/{ownerId}")
public class PetController {

	public static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private final PetService petService;
	private final PetTypeService petTypeService;
	private final OwnerService ownerService;

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return petTypeService.findAll();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable final Long ownerId) {
		return ownerService.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(final WebDataBinder dataBinder) {
		 dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/pets/new")
	public String initCreationForm(final Owner owner, final Model model) {
		final Pet pet = new Pet();
		owner.getPets().add(pet);
		model.addAttribute("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/new")
	public String processCreationForm(final Owner owner, @Valid final Pet pet,
									  final BindingResult bindingResult,
									  final ModelMap modelMap) {
		if (StringUtils.hasLength(pet.getName()) && pet.isNew() && nonNull(owner.getPet(pet.getName(), true))) {
			bindingResult.rejectValue("name", "duplicate", "alreadyExists");
		}
		owner.getPets().add(pet);
		if (bindingResult.hasErrors()) {
			modelMap.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(@PathVariable final Long petId, final Model model) {
		model.addAttribute("pet", petService.findById(petId));
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid final Pet pet, final BindingResult bindingResult,
									final Owner owner, final Model model) {
		if (bindingResult.hasErrors()) {
			pet.setOwner(owner);
			model.addAttribute("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		} else {
			owner.getPets().add(pet);
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
}
