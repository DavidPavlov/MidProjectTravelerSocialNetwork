package models;

import exceptions.InvalidDataException;

public class Comment {
	private static int id;

	private String text;
	private int numberOfLikes;
	private int idNumber;

	public Comment(String text) throws InvalidDataException {
		this.setText(text);
		this.idNumber = id;
		id++;
	}

	public String getText() {
		return text;
	}

	private void setText(String text) throws InvalidDataException {
		if (text != null && !text.isEmpty()) {
			this.text = text;
		} else {
			throw new InvalidDataException();
		}
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public int getIdNumber() {
		return idNumber;
	}

	public void addLike() {
		this.numberOfLikes++;
	}

}
