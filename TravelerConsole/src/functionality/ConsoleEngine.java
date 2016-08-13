package functionality;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.InvalidCoordinatesException;
import exceptions.InvalidDataException;
import models.Destination;
import models.Location;
import models.PlaceToEat;
import models.PlaceToSleep;
import models.User;

public class ConsoleEngine {

	private HashMap<String, User> userDataBase; // users stored by email
	private HashMap<String, Destination> destinations;
	private ArrayList<PlaceToEat> resturants;
	private ArrayList<PlaceToSleep> hotels;
	private User currentUser;

	public ConsoleEngine() {
		this.userDataBase = new HashMap<>();
		this.destinations = new HashMap<>();
		this.resturants = new ArrayList<>();
		this.hotels = new ArrayList<>();
	}

	public void run() {
		IO.printLine("For list of commands type help");
		while (true) {
			try {
				commandParse();
			} catch (InvalidDataException e) {
				IO.printLine(e.getMessage());
			} catch (ArrayIndexOutOfBoundsException e) {
				// if the parameters are invalid
				IO.printLine("Invalid parameters for the specified command !");
			}
			IO.printLine("Enter a command!");
		}
	}

	private void commandParse() throws InvalidDataException {
		String line = IO.readLine();
		if (line == null) {
			return;
		}

		String[] commandParameters = line.split(" +");
		String output = execudeCommand(commandParameters);
		if (output != null) {
			IO.printLine(output);
		}
	}

	private String execudeCommand(String[] commandParameters) throws InvalidDataException {
		String command = commandParameters[0];
		switch (command) {
		case "help":
			return executeHelpCommand();
		case "RegisterUser":
			return executeRegisterUserCommand(commandParameters);
		case "Login":
			return executeLoginCommand(commandParameters);
		case "Logout":
			return executeLogoutCommand();
		case "AddDestination":
			return executeAddDestinationCommand(commandParameters);
		default:
			return "Invalid Command";
		}

	}

	private String executeHelpCommand() {
		StringBuilder sb = new StringBuilder();
		sb.append("Possible commands:\n");
		sb.append("----------------------\n");
		sb.append("RegisterUser firstName lastName password email description  //registers a new user\n");
		sb.append("Login email password // login with a specified user\n");
		sb.append("Logout // logs out the current user\n");
		sb.append("AddDestination name description lattitude longitude "
				+ "//adds a destination if it doesn't exist in the database or add's it to the current user's visited destinations\n");
		sb.append("AddHotel name destinationName lattitude longitude //adds a place to sleep for a destination/n");
		sb.append("AddResturant name destinationName lattitude longitude //adds a place to eat for a destination");

		return sb.toString();
	}

	private String executeRegisterUserCommand(String[] commandParameters) throws InvalidDataException {
		String firstName = commandParameters[1];
		String lastName = commandParameters[2];
		String password = commandParameters[3];
		String email = commandParameters[4];
		StringBuilder description = new StringBuilder();
		for (int i = 5; i < commandParameters.length; i++) {
			description.append(commandParameters[i]);
			description.append(" ");
		}
		if (userDataBase.containsKey(email)) {
			IO.printLine("User with this email already exist");
			return null;
		}
		User user = UserFactory.createUser(firstName, lastName, password, email, description.toString());
		this.userDataBase.put(email, user);
		return "User Registered Succesfully";
	}

	private String executeLoginCommand(String[] commandParameters) {
		if (this.currentUser != null) {
			return "You have been already logged in the session.";
		}
		String email = commandParameters[1];
		String password = commandParameters[2];

		if (!this.userDataBase.containsKey(email)) {
			return "Incorrect email!";
		}

		User userInDB = userDataBase.get(email);
		char[] pass = password.toCharArray();
		if (pass.length != userInDB.getPassword().length) {
			return "Incorrect Password";
		}

		for (int i = 0; i < pass.length; i++) {
			if (pass[i] != userInDB.getPassword()[i]) {
				return "Incorrect Password";
			}
		}
		this.currentUser = userInDB;
		return "Succesfull Login";
	}

	private String executeLogoutCommand() {
		if (this.currentUser == null) {
			return "There is no currently logged in user";

		}
		currentUser = null;
		return "Logout succesfull";
	}

	private String executeAddDestinationCommand(String[] commandParameters)
			throws NumberFormatException, InvalidCoordinatesException {
		if (this.currentUser == null) {
			return "You have to login to add a destination";
		}
		String name = commandParameters[1];
		if (this.destinations.containsKey(name)) {
			return "This Destination already exists";
		}
		String longtitude = commandParameters[commandParameters.length - 1];
		String lattitude = commandParameters[commandParameters.length - 2];
		StringBuilder description = new StringBuilder();
		for (int i = 3; i < commandParameters.length - 2; i++) {
			description.append(commandParameters[i]);
			description.append(" ");
		}
		Destination destination = DestinationFactory.createDestination(name, description.toString(),
				new Location(Double.parseDouble(lattitude), Double.parseDouble(longtitude)));
		this.destinations.put(name, destination);
		return "Destination added succesfully";
	}
}
