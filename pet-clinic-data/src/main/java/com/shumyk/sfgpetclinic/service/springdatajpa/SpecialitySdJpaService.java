package com.shumyk.sfgpetclinic.service.springdatajpa;

import com.shumyk.sfgpetclinic.model.Speciality;
import com.shumyk.sfgpetclinic.repository.SpecialtyRepository;
import com.shumyk.sfgpetclinic.service.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialitySdJpaService implements SpecialtyService {

	private final SpecialtyRepository specialtyRepository;

	public SpecialitySdJpaService(SpecialtyRepository specialtyRepository) {
		this.specialtyRepository = specialtyRepository;
	}

	@Override
	public Set<Speciality> findAll() {
		final Set<Speciality> specialities = new HashSet<>();
		specialtyRepository.findAll().forEach(specialities::add);
		return specialities;
	}

	@Override
	public Speciality findById(Long aLong) {
		return specialtyRepository.findById(aLong).orElse(null);
	}

	@Override
	public Speciality save(Speciality object) {
		return specialtyRepository.save(object);
	}

	@Override
	public void delete(Speciality object) {
		specialtyRepository.delete(object);
	}

	@Override
	public void deleteById(Long aLong) {
		specialtyRepository.deleteById(aLong);
	}
}
