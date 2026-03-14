import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Stop implements Comparable<Stop> {
    String stopId;
    String name;
    double lat;
    double lon;

    List<Departure> departures = new ArrayList<>();

    double g, h;
    double f;
    Stop parent;
    LocalTime currentTime;
    Departure arrivalDeparture;

    Stop(String stopId, String name, double lat, double lon) {
        this.stopId = stopId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public int compareTo(Stop other) {
        return Double.compare(this.f, other.f);
    }
}
