import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

public class SLLite {
    Map<String, Stop> stops = new HashMap<>();

    public void loadData() throws IOException {
        BufferedReader stopReader = new BufferedReader(new FileReader("./Files/sl_stops.txt"));
        stopReader.readLine();

        String line;
        while((line = stopReader.readLine()) != null) {
            String[] p = line.split(",");
            stops.put(p[0], new Stop(p[0], p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3])));
        }
        stopReader.close();

        BufferedReader timeReader = new BufferedReader(new FileReader("./Files/sl_stop_times.txt"));
        timeReader.readLine();

        String lastTrip = "";
        Stop lastStop = null;
        LocalTime lastDeparture = null;

        while((line = timeReader.readLine()) != null) {
            String[] s = line.split(",");
            String tripId = s[0];
            LocalTime arrival = parseTime(s[1]);
            LocalTime departure = parseTime(s[2]);
            Stop current = stops.get(s[3]);

            if(tripId.equals(lastTrip) && lastStop != null) {
                lastStop.departures.add(new Departure(tripId, arrival, lastDeparture, current));
            }

            lastTrip = tripId;
            lastStop = current;
            lastDeparture = departure;
        }
        timeReader.close();
    }

    private LocalTime parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hour = Integer.parseInt(parts[0]) % 24;
        return LocalTime.of(hour, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    private long toMinutes(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    public void findPath(String start, String end, LocalTime startTime) {
        Stop startNode = stops.get(start);
        Stop endNode = stops.get(end);

        if(startNode == null || endNode == null) {
            System.out.println("Start eller slut finns inte.");
            return;
        }

        if(startNode == endNode) {
            System.out.println("Start och slut är samma plats, vi är redan där.");
            return;
        }

        PriorityQueue<Stop> openList = new PriorityQueue<>();
        ArrayList<Stop> closedList = new ArrayList<>();

        openList.add(startNode);

        startNode.g = 0;
        startNode.h = heuristic(startNode, endNode);
        startNode.f = startNode.g + startNode.h;
        startNode.parent = null;
        startNode.currentTime = startTime;

        while(!openList.isEmpty()) {
            Stop current = openList.poll();

            if(current == endNode) {
                printPath(current);
                return;
            }

            closedList.add(current);

            for(Departure dep : current.departures) {
                if(closedList.contains(dep.destination)) {
                    continue;
                }

                double depMin = toMinutes(dep.departureTime);
                double arrMin = toMinutes(dep.arrivalTime);
                double currentMin = toMinutes(current.currentTime);

                if(depMin < currentMin) {
                    depMin += 1440;
                }
                if(arrMin < depMin) {
                    arrMin += 1440;
                }

                double tentativeG = current.g + (arrMin - currentMin);

                if(!openList.contains(dep.destination)) {
                    openList.add(dep.destination);
                } else if(tentativeG >= dep.destination.g) {
                    continue;
                }

                dep.destination.arrivalDeparture = dep;
                dep.destination.currentTime = dep.arrivalTime;
                dep.destination.parent = current;
                dep.destination.g = tentativeG;
                dep.destination.h = heuristic(dep.destination, endNode);
                dep.destination.f = dep.destination.g + dep.destination.h;
            }
        }
        System.out.println("No path exists.");
    }

    private double heuristic(Stop start, Stop end) {
        return Math.sqrt(Math.pow((start.lat - end.lat), 2) + Math.pow((start.lon - end.lon), 2));
    }

    private void printPath(Stop goal) {
        if(goal == null) {
            System.out.println("Ingen väg hittades");
            return;
        }

        List<Stop> path = new ArrayList<>();
        Stop current = goal;

        while(current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);

        System.out.println("Resplan hittad:");
        System.out.println("-----------------------------------------");

        for(int i = 0; i < path.size(); i++) {
            Stop step = path.get(i);

            if(i == 0) {
                System.out.println("Starta vid: " + step.name + " kl. " + step.currentTime);
            } else {
                Departure dep = step.arrivalDeparture;
                System.out.println("Kliv på trip " + dep.tripId + " vid " + path.get(i-1).name + " kl. " + dep.departureTime);

                System.out.println("Ankom till: " + step.name + " kl. " + dep.arrivalTime);
            }
            System.out.println("--------------------------------------------");
        }
        System.out.println("Total restid: " + goal.g + " minuter.");
    }
}
