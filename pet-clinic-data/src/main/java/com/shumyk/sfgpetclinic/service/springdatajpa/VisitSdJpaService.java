package com.shumyk.sfgpetclinic.service.springdatajpa;

import com.shumyk.sfgpetclinic.model.Visit;
import com.shumyk.sfgpetclinic.repository.VisitRepository;
import com.shumyk.sfgpetclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitSdJpaService implements VisitService {

	private final VisitRepository visitRepository;

	public VisitSdJpaService(VisitRepository visitRepository) {
		this.visitRepository = visitRepository;
	}

	@Override
	public Set<Visit> findAll() {
		final Set<Visit> visits = new HashSet<>();
		visitRepository.findAll().forEach(visits::add);
		return visits;
	}

	@Override
	public Visit findById(Long aLong) {
		return visitRepository.findById(aLong).orElse(null);
	}

	@Override
	public Visit save(Visit object) {
		return visitRepository.save(object);
	}

	@Override
	public void delete(Visit object) {
		visitRepository.delete(object);
	}

	@Override
	public void deleteById(Long aLong) {
		visitRepository.deleteById(aLong);
	}
}
