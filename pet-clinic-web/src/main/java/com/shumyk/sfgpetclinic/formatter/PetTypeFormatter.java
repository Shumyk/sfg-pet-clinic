package com.shumyk.sfgpetclinic.formatter;

import com.shumyk.sfgpetclinic.model.PetType;
import com.shumyk.sfgpetclinic.service.PetTypeService;
import lombok.AllArgsConstructor;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
@AllArgsConstructor
public class PetTypeFormatter implements Formatter<PetType> {

	private final PetTypeService petTypeService;

	@Override
	public String print(PetType object, Locale locale) {
		return object.getName();
	}

	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		final Collection<PetType> petTypes = petTypeService.findAll();
		for (final PetType petType : petTypes) {
			if (petType.getName().equals(text)) {
				return petType;
			}
		}
		throw new ParseException("Pet Type not found: " + text, 0);
	}
}
