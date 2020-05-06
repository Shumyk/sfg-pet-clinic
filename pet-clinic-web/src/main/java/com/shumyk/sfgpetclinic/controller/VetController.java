package com.shumyk.sfgpetclinic.controller;

import com.shumyk.sfgpetclinic.model.Vet;
import com.shumyk.sfgpetclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@RequestMapping("/vets")
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listVets(final Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vet/index";
    }

    @GetMapping("/api")
    public @ResponseBody Set<Vet> vetsJson() {
        return vetService.findAll();
    }
}
