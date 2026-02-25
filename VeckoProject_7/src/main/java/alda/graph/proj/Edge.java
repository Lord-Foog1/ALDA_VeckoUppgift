package alda.graph.proj;

public class Edge {
    private String name;
    private double departureTime;
    private double travelTime;
    private double arrivalTime;

    Edge(String name, double departureTime, double travelTime, double arrivalTime) {
        this.name = name;
        this.departureTime = departureTime;
        this.travelTime = travelTime;
        this.arrivalTime = arrivalTime;
    }

    public String getName() {
        return name;
    }

    public double getDepartureTime() {
        return departureTime;
    }

    public double getTravelTime() {
        return travelTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }
}
