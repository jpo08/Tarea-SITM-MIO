public class Stop {
    public int stopId;
    public String shortName;
    public double lat;
    public double lon;

    public Stop(int stopId, String shortName, double lat, double lon) {
        this.stopId = stopId;
        this.shortName = shortName;
        this.lat = lat;
        this.lon = lon;
    }
}