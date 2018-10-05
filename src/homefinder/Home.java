package homefinder;

public class Home {
	
	private Position pos;
	private int nbBedrooms;
	private int price;
	
	public Home(int lat, int lon, int nbBedrooms, int price) {
		pos = new Position(lat, lon);
		this.nbBedrooms = nbBedrooms;
		this.price = price;
	}
	
	public int getLatitude(){
		return pos.getLatitude();
	}
	public int getLongitude(){
		return pos.getLongitude();
	}
	public int getNbBedrooms(){
		return nbBedrooms;
	}
	public int getPrice(){
		return price;
	}
}
