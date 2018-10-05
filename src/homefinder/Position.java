package homefinder;

public class Position {
	private int latitude;
	private int longitude;
	
	public Position(int lat, int lon){
		latitude = lat;
		longitude = lon;
	}
	
	public int getLatitude(){
		return latitude;
	}
	public int getLongitude(){
		return longitude;
	}
	
	public void setLatitude(int lat){
		latitude = lat;
	}
	public void setLongitude(int lon){
		longitude = lon;
	}
}
