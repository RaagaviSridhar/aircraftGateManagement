package com.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.model.Gates;
import com.assignment.repository.UserRepository;

@Service("userService")
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void saveGates(Gates gate) {
		userRepository.save(gate);
	}

	public List<Integer> findByGateAvailability() {
		return userRepository.findByGateAvailability("Y");

	}

	public int updateFlightDetails(Gates gate) {
		return userRepository.updateFlightDetails(gate.getGateAvailability(), gate.getFlightNumber(), gate.getDestination(), gate.getSource(), gate.getId());

	}

	public Gates findByFlightNumber(String flightNumber) {
		return userRepository.findByFlightNumber(flightNumber);

	}

	public int updateLeaveRequest(Gates gate) {
		return userRepository.updateLeaveRequest(gate.getFlightNumber(), gate.getId());

	}

	public List<String> findByDestination(String destination) {
		return userRepository.findByDestination(destination);

	}

	public List<Integer> findBySource(String source) {
		return userRepository.findBySource(source);

	}

	public List<Gates> getStatus() {
		return userRepository.getStatus();

	}

}