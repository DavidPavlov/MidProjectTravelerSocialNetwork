package models;

import exceptions.InvalidCoordinatesException;

public class Location {
	private double latitude;
	private double longitude;

	public Location(double latitude, double longitude) throws InvalidCoordinatesException {
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public double getLatitude() {
		return latitude;
	}

	private void setLatitude(double latitude) throws InvalidCoordinatesException {
		if (latitude < 0 || latitude > 90) {
			throw new InvalidCoordinatesException();
		}
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	private void setLongitude(double longitude) throws InvalidCoordinatesException {
		if (longitude < 0 || longitude > 180) {
			throw new InvalidCoordinatesException();
		}
		this.longitude = longitude;
	}

}
