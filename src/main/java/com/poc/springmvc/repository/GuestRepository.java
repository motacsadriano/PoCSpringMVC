package com.poc.springmvc.repository;

import org.springframework.data.repository.CrudRepository;

import com.poc.springmvc.model.Guest;

public interface GuestRepository extends CrudRepository<Guest, String> {

}
