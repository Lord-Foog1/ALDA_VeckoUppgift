package alda.graph.proj;

import java.util.*;

public class Node {
    String id;
    String name;
    double latitude;
    double longitude;

    List<Departure> departures = new ArrayList<>();

    Node(String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
