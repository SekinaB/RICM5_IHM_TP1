package homefinder;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Map extends JPanel {
	/**
	 * Serial added by default
	 */
	private static final long serialVersionUID = 1L;

	private int height;
	private int width;
	private int nbBedroomsMin;
	private int nbBedroomsMax;
	private int priceMin;
	private int priceMax;

	private ArrayList<Home> homesList = new ArrayList<Home>();

	public Map(int height, int width, int nbHomes, int nbBedroomsMin, int nbBedroomsMax, int priceMin, int priceMax) {
		this.height = height;
		this.width = width;
		this.nbBedroomsMin = nbBedroomsMin;
		this.nbBedroomsMax = nbBedroomsMax;
		this.priceMin = priceMin;
		this.priceMax = priceMax;
		for (int i = 0; i < nbHomes; i++) {
			int nbBedrooms = (int) (Math.random() * (nbBedroomsMax - nbBedroomsMin)) + nbBedroomsMin;
			int price = (int) (Math.random() * (priceMax - priceMin)) + priceMin;
			int lat = (int) (Math.random() * height);
			int lon = (int) (Math.random() * width);
			System.out.println(lat + ", " + lon);
			homesList.add(new Home(lat, lon, nbBedrooms, price));
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(5, 10, this.height, this.width);

		for (Home home : homesList) {
			if (home.getNbBedrooms() <= nbBedroomsMax && home.getNbBedrooms() >= nbBedroomsMin
					&& home.getPrice() >= priceMin && home.getPrice() <= priceMax) {
				g.setColor(Color.yellow);
				g.fillRect(home.getLatitude() * height, home.getLongitude(), 4, 4);
			}
		}
	}
	
	public void setnbBedroomsMin(int val){
		this.nbBedroomsMin = val;
	}
	public void setnbBedroomsMax(int val){
		this.nbBedroomsMax = val;
	}
	public void setpriceMin(int val){
		this.priceMin = val;
	}
	public void setpriceMax(int val){
		this.priceMax = val;
	}

}
