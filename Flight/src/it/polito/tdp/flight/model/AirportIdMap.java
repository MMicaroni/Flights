package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {
	
	private Map<Integer, Border> map;
	
	public AirportIdMap() {
		map = new HashMap<>();
	}
	
	public Border get(int airportId) {
		return map.get(airportId);
	}
	
	public Border get(Border airport) {
		Border old = map.get(airport.getAirportId());
		if (old == null) {
			map.put(airport.getAirportId(), airport);
			return airport;
		}
		return old;
	}
	
	public void put(Border airport, int airportId) {
		map.put(airportId, airport);
	}
}
