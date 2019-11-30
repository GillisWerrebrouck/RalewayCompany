package com.railway.route_management_service.adapters.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.railway.route_management_service.domain.Connection;
import com.railway.route_management_service.domain.QueryFailedException;
import com.railway.route_management_service.domain.SelfReferentialNodeException;
import com.railway.route_management_service.persistence.ConnectionRepository;
import com.railway.route_management_service.persistence.RouteRepository;
import com.railway.route_management_service.persistence.StationRepository;

@RestController
@RequestMapping("/network/connection")
public class ConnectionRestController extends RouteRestController {
	public ConnectionRestController(StationRepository stationRepository, ConnectionRepository connectionRepository, RouteRepository routeRepository) {
		super(stationRepository, connectionRepository, routeRepository);
	}

	@GetMapping
	public Iterable<Connection> getRailwayNetwork() {
		return this.connectionRepository.findAll();
	}

	@GetMapping("/{id}")
	public Connection getConnectionById(@PathVariable("id") Long id) {
		return this.connectionRepository.findById(id).orElse(null);
	}

	// create a new (direct) connection between two stations
	@PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void connectStations(@RequestBody Connection connection) throws SelfReferentialNodeException, QueryFailedException {
		if (connection.getStationX().getName().compareTo(connection.getStationY().getName()) == 0) {
			String errorMessage = "Self-referential nodes are not allowed";
			throw new SelfReferentialNodeException(errorMessage);
		}
		
		int result = this.connectionRepository.connectStations(connection.getStationX().getName(), connection.getStationY().getName(), connection.getDistance());
		
		if (result == 0) {
			String errorMessage = "Connection between " + connection.getStationX().getName() + " and " + connection.getStationY().getName() + " could not be created";
			throw new QueryFailedException(errorMessage);
		}
	}

	@PutMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void getConnectionById(@RequestBody Connection connection) throws Exception {
		if (connection.getId() == null) {
			String errorMessage = "No connection id specified";
			throw new Exception(errorMessage);
		}
		
		this.connectionRepository.save(connection);
	}
}