package com.poc.springmvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poc.springmvc.model.Event;
import com.poc.springmvc.model.Guest;
import com.poc.springmvc.repository.EventRepository;
import com.poc.springmvc.repository.GuestRepository;

@Controller
public class EventController {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private GuestRepository guestRepository;
	
	@RequestMapping(value="/saveEvent", method = RequestMethod.GET)
	public String form() {
		return "event/eventForm";
	}
	
	@RequestMapping(value="/saveEvent", method = RequestMethod.POST)
	public String form(@Valid Event event, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("message","Please, validate the form");
			return "redirect:/saveEvent";
		}
		
		this.eventRepository.save(event);
		
		attributes.addFlashAttribute("message","Event Added with success!");
		return "redirect:/events";
		
	}
	
	@RequestMapping("/events")
	public ModelAndView listEvents() {
		ModelAndView modelAndView = new ModelAndView("event/index.html");
		Iterable<Event> events = this.eventRepository.findAll();
		
		modelAndView.addObject("events", events);
		return modelAndView;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ModelAndView eventDetails(@PathVariable("id") long id) {
		Event event = this.eventRepository.findById(id);
		
		ModelAndView modelAndView = new ModelAndView("event/eventDetail");
		modelAndView.addObject("event", event);
		
		Iterable<Guest> guests = this.guestRepository.findByEvent(event);
		modelAndView.addObject("guests", guests);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/removeEvent", method = RequestMethod.GET)
	public String removeEvent(long id, RedirectAttributes attributes) {
		Event event = this.eventRepository.findById(id);
		Iterable<Guest> iterable = this.guestRepository.findByEvent(event);
		
		if(iterable.spliterator().getExactSizeIfKnown() > 0) {
			attributes.addFlashAttribute("message","This Event has guests. Please, remove the guests first");
			return "redirect:/events";
		}
		
		this.eventRepository.delete(this.eventRepository.findById(id));
		
		return "redirect:/events";
	}
	
	@RequestMapping(value="/removeGuest", method = RequestMethod.GET)
	public String removeGuest(long id) {
		Guest guest = this.guestRepository.findById(id) ;
		
		this.guestRepository.delete(guest);
		
		//Event event = this.eventRepository.findById(eventId);
		return "redirect:/"+guest.getEvent().getId();
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public String saveGuestToEvent(@PathVariable("id") long id, @Valid Guest guest, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("message","Please, validate the form");
			return "redirect:/{id}";
		}
		
		Event event = this.eventRepository.findById(id);
		guest.setEvent(event);
		
		this.guestRepository.save(guest);
		
		attributes.addFlashAttribute("message","Guest Added with success!");
		return "redirect:/{id}";
	}
	
	
	
	
	
}