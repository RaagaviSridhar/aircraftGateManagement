package com.assignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "gates")
public class Gates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "gate_Availability", nullable = false)
	private String gateAvailability;

	@Transient
	@NotEmpty(message = "Please provide Number of Gates")
	private int noOfGates;

	@Column(name = "flight_Number")
	@NotEmpty(message = "Please provide Flight Number")
	private String flightNumber;

	@Column(name = "source")
	@NotEmpty(message = "Please provide your source airport or atleast specify NONE")
	private String source;

	@Column(name = "destination")
	@NotEmpty(message = "Please provide your destination airport or atleast specify NONE")
	private String destination;

	public int getNoOfGates() {
		return noOfGates;
	}

	public void setNoOfGates(int noOfGates) {
		this.noOfGates = noOfGates;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGateAvailability() {
		return gateAvailability;
	}

	public void setGateAvailability(String gateAvailability) {
		this.gateAvailability = gateAvailability;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}