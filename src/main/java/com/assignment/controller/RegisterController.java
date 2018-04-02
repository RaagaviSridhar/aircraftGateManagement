package com.assignment.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.model.Gates;
import com.assignment.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

@Controller
public class RegisterController {

	private UserService userService;

	@Autowired
	public RegisterController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/gatesInput", method = RequestMethod.GET)
	public ModelAndView showGatesInputPage(ModelAndView modelAndView, Gates user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("register");
		return modelAndView;
	}

	/*
	 * Process input data - No of gates input is received and equivalent no of entries are made in Database
	 */

	@RequestMapping(value = "/gatesInput", method = RequestMethod.POST)
	public ModelAndView processRegistrationForm(ModelAndView modelAndView, @ModelAttribute Gates gate, BindingResult bindingResult, HttpServletRequest request) {
		int noOfGates = gate.getNoOfGates();

		for (int i = 0; i < noOfGates; i++) {
			gate.setGateAvailability("Y");
			userService.saveGates(gate);
		}
		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/*
	 * Process Update data - input is received and gate is updated with the aircraft details
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView confirmRegistration(ModelAndView modelAndView, @ModelAttribute Gates gate) {

		List<Integer> gatesList = userService.findByGateAvailability();

		if (gatesList != null) {
			// Gate Available
			// To find the nearest gate the list is sorted
			Collections.sort(gatesList);
			gate.setGateAvailability("N");
			gate.setId(gatesList.get(0));
			userService.updateFlightDetails(gate);
			modelAndView.addObject("successMessage", "Gate Allocated successfully");
		} else {
			// Gate Not Available
			modelAndView.addObject("errorMessage", "No Gates available");
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/*
	 * Process Leave Aircraft - Respective gate is emptied
	 */

	@RequestMapping(value = "/leaveAircraft", method = RequestMethod.POST)
	public ModelAndView leaveAircraft(ModelAndView modelAndView, @ModelAttribute Gates gate) {

		if (userService.findByFlightNumber(gate.getFlightNumber()) != null) {
			// Aircraft Available
			// Update the leave request
			userService.updateLeaveRequest(gate);
			modelAndView.addObject("successMessage", "Aircraft Left the gate");
		} else {
			// Aircraft Not Available
			modelAndView.addObject("errorMessage", "No such Aircraft");
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/*
	 * Process gate present - gate is retrieved for where the aircraft is present
	 */
	@RequestMapping(value = "/gatePresent", method = RequestMethod.POST)
	public ModelAndView gatePresent(ModelAndView modelAndView, @ModelAttribute Gates gate) {

		Gates gateNew = userService.findByFlightNumber(gate.getFlightNumber());
		if (userService.findByFlightNumber(gate.getFlightNumber()) != null) {
			// Aircraft available in the gate
			modelAndView.addObject("successMessage", "Aircraft is in the gate" + gateNew.getId());
		} else {
			// Aircraft Not Available in any gate
			modelAndView.addObject("errorMessage", "No such Aircraft");
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/*
	 * Process flying to destination - No of Aircrafts flying to the corresponding destination
	 */
	@RequestMapping(value = "/flyingTo", method = RequestMethod.POST)
	public ModelAndView flyingTo(ModelAndView modelAndView, @ModelAttribute Gates gate) {

		List<String> flightsDestination = userService.findByDestination(gate.getDestination());
		if (flightsDestination != null) {
			// Aircraft available in the gate
			modelAndView.addObject("successMessage", "Aircrafts flying to the destination are" + flightsDestination);
		} else {
			// Aircraft Not Available in any gate
			modelAndView.addObject("errorMessage", "No such Destination");
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/*
	 * Process source from - No of gates with the aircraft flying from the source
	 */
	@RequestMapping(value = "/sourceFrom", method = RequestMethod.POST)
	public ModelAndView sourceFrom(ModelAndView modelAndView, @ModelAttribute Gates gate) {

		List<Integer> gatesSource = userService.findBySource(gate.getSource());
		if (gatesSource != null) {
			// Aircraft available in the gate
			modelAndView.addObject("successMessage", "Aircrafts flying from the source are" + gatesSource);
		} else {
			// Aircraft Not Available in any gate
			modelAndView.addObject("errorMessage", "No such source");
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	/*
	 * Process Status of the Gate Management - INformation of the gates with aircraft is displayed
	 */
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public ModelAndView status(ModelAndView modelAndView, @ModelAttribute Gates gate) {

		List<Gates> gatesSource = userService.getStatus();
		if (gatesSource != null) {
			modelAndView.addObject("successMessage", "Status:" + gatesSource);
		} else {
			modelAndView.addObject("errorMessage", "No Gates has Aircrafts");
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

}