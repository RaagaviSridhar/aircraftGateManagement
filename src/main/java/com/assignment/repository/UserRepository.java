package com.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.model.Gates;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<Gates, Long> {

	@Query(value = "SELECT id FROM GATES WHERE GATE_AVAILABILITY = ?1", nativeQuery = true)
	List<Integer> findByGateAvailability(String string);

	@Modifying
	@Query("update gates g set g.GATE_AVAILABILITY = ?1,g.flight_Number =?2,g.destination =?3, g.source =?4 where g.id = ?5")
	int updateFlightDetails(String gateAvailability, String flightNumber, String destination, String source, int id);

	Gates findByFlightNumber(String flightNumber);

	@Modifying
	@Query("update gates g set g.GATE_AVAILABILITY = Y ,g.flight_Number = none ,g.destination = none, g.source =none where g.flight_Number = ?1 and g.id = ?2")
	int updateLeaveRequest(String flightNumber, int id);

	@Query(value = "SELECT flight_Number FROM GATES WHERE destination = ?1", nativeQuery = true)
	List<String> findByDestination(String destination);

	@Query(value = "SELECT id FROM GATES WHERE source = ?1", nativeQuery = true)
	List<Integer> findBySource(String source);

	@Query(value = "SELECT * FROM GATES WHERE GATE_AVAILABILITY <> N ", nativeQuery = true)
	List<Gates> getStatus();

}
