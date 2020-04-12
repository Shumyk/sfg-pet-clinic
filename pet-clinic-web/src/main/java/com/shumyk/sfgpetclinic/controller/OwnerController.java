package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.Objects.isNull;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @RequestMapping("/find")
    public String findOwners(final Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(final Owner owner, final BindingResult bindingResult, final Model model) {
        // allow parameterless GET request for /owners to return all records
        if (isNull(owner.getLastName())) {
            owner.setLastName("");
        }

        // find owners by last name
        final List<Owner> ownerResults = ownerService.findAllByLastNameLike(owner.getLastName());
        if (ownerResults.isEmpty()) {
            // no owners found
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (1 == ownerResults.size()) {
            // only one owner found
            return "redirect:/owners/" + ownerResults.iterator().next().getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", ownerResults);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") final Long ownerId) {
        final ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(this.ownerService.findById(ownerId));
        return modelAndView;
    }
}
