package models;

import java.util.ArrayList;

public class Destination {
	private String name;
	private String description;
	private ArrayList<Comment> comments;
	private ArrayList<PlaceToSleep> placesToSleep;
	private ArrayList<PlaceToEat> placesToEat;
	private Location location;

	public Destination(String name, String description, Location location) {
		this.name = name;
		this.description = description;
		this.location = location;
		this.comments = new ArrayList<>();
		this.placesToSleep = new ArrayList<>();
		this.placesToEat = new ArrayList<>();
	}

	public void addPlaceToSleep(PlaceToSleep place) {
		this.placesToSleep.add(place);
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	public void addPlaceToEat(PlaceToEat place) {
		this.placesToEat.add(place);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Location getLocation() throws CloneNotSupportedException {
		return (Location) location.clone();
	}
	
	public ArrayList<Comment> getComments() {
		return (ArrayList<Comment>) comments.clone();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
}
