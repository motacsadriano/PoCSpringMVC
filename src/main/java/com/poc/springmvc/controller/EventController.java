package com.poc.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		
		//return "redirect:/saveEvent";
		return "redirect:/events";
		
	}
	
	@RequestMapping("/events")
	public ModelAndView listEvents() {
		ModelAndView modelAndView = new ModelAndView("event/index.html");
		Iterable<Event> events = this.eventRepository.findAll();
		
		modelAndView.addObject("events", events);
		return modelAndView;
	}
	
	@RequestMapping("/{id}")
	public ModelAndView eventDetails(@PathVariable("id") long id) {
		Event event = this.eventRepository.findById(id);
		
		ModelAndView modelAndView = new ModelAndView("event/eventDetail");
		modelAndView.addObject("event", event);
		
		return modelAndView;
	}
}