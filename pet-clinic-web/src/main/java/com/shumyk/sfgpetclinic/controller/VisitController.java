package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Pet;
import com.shumyk.sfgpetclinic.model.Visit;
import com.shumyk.sfgpetclinic.service.PetService;
import com.shumyk.sfgpetclinic.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class VisitController {
	private final VisitService visitService;
	private PetService petService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Pet object always has an id (Even though id is not part of the form fields)
	 * @param petId
	 * @return Pet
	 */
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable("petId") final Long petId, Map<String, Object> model) {
		Pet pet = petService.findById(petId);
		model.put("pet", pet);
		Visit visit = new Visit();
		visit.setPet(pet);
		pet.getVisits().add(visit);
		return visit;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping("/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") Long petId, Map<String, Object> model) {
		return "pets/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}
		else {
			visitService.save(visit);
			return "redirect:/owners/{ownerId}";
		}
	}
}
