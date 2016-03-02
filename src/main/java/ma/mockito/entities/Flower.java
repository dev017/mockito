package ma.mockito.entities;

import java.util.Date;

public class Flower {

	// ATTRIBUTES
	private String color;

	private String name;

	private static final Date SYS_DATE = new Date();

	// CONSTRUCTOR
	public Flower() {
	}

	public Flower(String color, String name) {
		super();
		this.color = color;
		this.name = name;
	}

	// GETTERS AND SETTERS
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Nombre des fleurs arrosées en se basant sur une date. Si la date renseignée est avant la date Système ça retourne 500 si non ça retourne 100.
	 * 
	 * @param date
	 * @return Nombre des fleurs arrosées
	 */
	public int getNumberOfFlowerWatered(Date date) {
		if (SYS_DATE.before(date))
			return 500;
		else
			return 100;
	}

	/**
	 * Méthode qui retourne une fleure sans aucun traitement
	 * 
	 * @param name
	 * @param color
	 * @return une fleure
	 */
	public Flower getFlowerByNameAndColor(String name, String color) {
		return new Flower("Rouge,Blanche", "Amaryllis");
	}

	/**
	 * ToString
	 */
	@Override
	public String toString() {
		return "[" + this.name + "," + this.color + "]";
	}

	/**
	 * Equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Flower))
			return false;

		Flower flower = (Flower) obj;

		if (flower.getColor().equals(this.color) && flower.getName().equals(this.name))
			return true;
		else
			return false;
	}
}
