package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Owner;
import com.shumyk.sfgpetclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.isNull;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    public static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(final WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @GetMapping("/find")
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
        final List<Owner> ownerResults = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
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
    public ModelAndView showOwner(@PathVariable final Long ownerId) {
        final ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(this.ownerService.findById(ownerId));
        return modelAndView;
    }

    @GetMapping("/new")
    public String initCreationForm(final Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid final Owner owner, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            final Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable final Long ownerId, final Model model) {
        model.addAttribute(ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid final Owner owner,
                                         final BindingResult bindingResult,
                                         @PathVariable final Long ownerId) {
        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        owner.setId(ownerId);
        final Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }
}
