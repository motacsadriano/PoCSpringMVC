package com.poc.springmvc.repository;

import org.springframework.data.repository.CrudRepository;

import com.poc.springmvc.model.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}
