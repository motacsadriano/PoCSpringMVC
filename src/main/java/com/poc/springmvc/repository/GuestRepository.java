package com.poc.springmvc.repository;

import org.springframework.data.repository.CrudRepository;

import com.poc.springmvc.model.Event;
import com.poc.springmvc.model.Guest;

public interface GuestRepository extends CrudRepository<Guest, String> {
	Iterable<Guest> findByEvent(Event event);
	Guest findById(long id);
}
