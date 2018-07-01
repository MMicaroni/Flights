package it.polito.tdp.flight.model;

public class Route {
	
	private int routeId;
	private Airline airline;
	private Border sourceAirport;
	private Border destinationAirport;
	private String codeshare;
	private int stops;
	private String equipment;

	public Route(int routeId, Airline airline, Border sourceAirport, Border destinationAirport,
		 String codeshare, int stops, String equipment) {
		this.routeId = routeId;
		this.airline = airline;
		this.sourceAirport = sourceAirport;
		this.destinationAirport = destinationAirport;
		this.codeshare = codeshare;
		this.stops = stops;
		this.equipment = equipment;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Border getSourceAirport() {
		return sourceAirport;
	}

	public void setSourceAirport(Border sourceAirport) {
		this.sourceAirport = sourceAirport;
	}

	public Border getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(Border destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getCodeshare() {
		return codeshare;
	}

	public void setCodeshare(String codeshare) {
		this.codeshare = codeshare;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + routeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (routeId != other.routeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Route [airline=");
		builder.append(airline);
		builder.append(", sourceAirport=");
		builder.append(sourceAirport);
		builder.append(", destinationAirport=");
		builder.append(destinationAirport);
		builder.append("]");
		return builder.toString();
	}
}
