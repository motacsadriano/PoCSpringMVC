package com.poc.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poc.springmvc.model.Event;
import com.poc.springmvc.repository.EventRepository;

@Controller
public class EventController {

	@Autowired
	private EventRepository eventRepository;
	
	@RequestMapping(value="/saveEvent", method = RequestMethod.GET)
	public String form() {
		return "event/eventForm";
	}
	
	@RequestMapping(value="/saveEvent", method = RequestMethod.POST)
	public String form(Event event) {
		this.eventRepository.save(event);
		
		return "redirect:/saveEvent";
	}
}