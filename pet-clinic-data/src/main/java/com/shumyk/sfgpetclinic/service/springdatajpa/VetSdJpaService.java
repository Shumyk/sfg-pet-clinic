package com.shumyk.sfgpetclinic.service.springdatajpa;

import com.shumyk.sfgpetclinic.model.Vet;
import com.shumyk.sfgpetclinic.repository.VetRepository;
import com.shumyk.sfgpetclinic.service.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSdJpaService implements VetService {

	private final VetRepository vetRepository;

	public VetSdJpaService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Override
	public Set<Vet> findAll() {
		final Set<Vet> vets = new HashSet<>();
		vetRepository.findAll().forEach(vets::add);
		return vets;
	}

	@Override
	public Vet findById(Long aLong) {
		return vetRepository.findById(aLong).orElse(null);
	}

	@Override
	public Vet save(Vet object) {
		return vetRepository.save(object);
	}

	@Override
	public void delete(Vet object) {
		vetRepository.delete(object);
	}

	@Override
	public void deleteById(Long aLong) {
		vetRepository.deleteById(aLong);
	}
}
