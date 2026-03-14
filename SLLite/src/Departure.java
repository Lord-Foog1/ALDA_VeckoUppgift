import java.time.LocalTime;

public class Departure {
    String tripId;
    LocalTime arrivalTime;
    LocalTime departureTime;
    Stop destination;

    Departure(String tripId, LocalTime arrivalTime, LocalTime departureTime, Stop destination) {
        this.tripId = tripId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.destination = destination;
    }
}
