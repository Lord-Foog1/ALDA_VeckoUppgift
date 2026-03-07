package alda.graph.proj;

import java.time.LocalTime;

public class Departure {
    Node destination;
    LocalTime departureTime;
    LocalTime arrivalTime;
    String tripId;

    Departure(Node destination, LocalTime departure, LocalTime arrival, String id) {
        this.destination = destination;
        this.departureTime = departure;
        this.arrivalTime = arrival;
        this.tripId = id;
    }
}
