package functionality;

import java.util.concurrent.ConcurrentHashMap;

import dbModels.DestinationDAO;
import exceptions.InvalidCoordinatesException;
import models.Destination;
import models.Location;
import models.User;

public class DestinationsManager {

	private static DestinationsManager instance; // Singleton
	private ConcurrentHashMap<String, Destination> allDestinations;

	private DestinationsManager() {
		allDestinations=new ConcurrentHashMap<>();
		for (Destination d : DestinationDAO.getInstance().getAllDestinations()) { //adds all destinations form DB to collection
			allDestinations.put(d.getName(), d);
		}
	}

	public static synchronized DestinationsManager getInstance() {
		if (instance == null) {
			instance=new DestinationsManager();
		}
		return instance;
	}

	public boolean validateDestination(String name) { // validation of input
		if (!allDestinations.containsKey(name)) { // no such destination
			return false;
		}
		return true;
	}

	public boolean addDestination(User u, String name, String description, double latitude, double longitude, String picture) throws InvalidCoordinatesException, CloneNotSupportedException {
		if (UsersManager.getInstance().validateUser(u.getEmail(), u.getPassword())) { // if the user exists in the collection
			Destination destination=new Destination(name, description, new Location(latitude, longitude), picture);
			allDestinations.put(name, destination); // adds the new destination to the collection
			DestinationDAO.getInstance().saveDestinationToDB(u, destination); // saves destination to DB
			return true;
		}
		return false; // no such user
	}

	public static boolean addComment(User user, String comment) {
		return true;
		// TODO
	}

	public static boolean addHotel(User user, String name, double longitude, double lattitude, String contact) {
		return true;
		// TODO
	}

	public static boolean addResturant(User user, String name, double longtitude, double lattitude, String workingHours) {
		return true;
		// TODO
	}

	public boolean updateDestinationInfo(String name, String description, double longitude, double lattitude, String picture) throws InvalidCoordinatesException {
		if (!allDestinations.containsKey(name)) { // no such destination
			return false;
		}
		Destination destination = allDestinations.get(name); // takes the destination and updates its fields
		destination.setDescription(description);
		destination.setLocation(new Location(lattitude, longitude));
		DestinationDAO.getInstance().updateDestinationInDB(name, description, longitude, lattitude, picture); // updates the DB user:
		return true;
	}

}
