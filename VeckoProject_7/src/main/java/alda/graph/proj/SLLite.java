package alda.graph.proj;

import java.time.LocalTime;
import java.util.*;
import java.io.*;

public class SLLite {
    //Key: stop_id, Value: Stop-objekt (Nodes)
    Map<String, Node> stops = new HashMap<>();

    /** Skapar det objekt som finns i de filerna vi har blivit tilldelade
     *
     * @throws IOException
     */
    public void loadData() throws IOException {
        BufferedReader brStops = new BufferedReader(new FileReader("./Files/sl_stops.txt"));
        brStops.readLine();

        String line;
        while((line = brStops.readLine()) != null) {
            String[] p = line.split(",");
            stops.put(p[0], new Node(p[0], p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3])));
        }
        brStops.close();

        BufferedReader brTimes = new BufferedReader(new FileReader("./Files/sl_stop_times.txt"));
        brTimes.readLine();

        String lastTripId = "";
        Node lastStop = null;
        LocalTime lastDeparture = null;

        while((line = brTimes.readLine()) != null) {
            String[] p = line.split(",");
            String tripId = p[0];
            LocalTime arrTime = parseTime(p[1]);
            LocalTime depTime = parseTime(p[2]);
            Node currentStop = stops.get(p[3]);

            if(tripId.equals(lastTripId) && lastStop != null) {
                lastStop.departures.add(new Departure(currentStop, lastDeparture, arrTime, tripId));
            }

            lastTripId = tripId;
            lastStop = currentStop;
            lastDeparture = depTime;
        }
        brTimes.close();
    }

    /** Tar strängen av tid och omvandlar den till en LocalTime version.
     *
     * @param timeStr strängen av tid
     * @return LocalTime version av tid delen av strängen
     */
    private LocalTime parseTime(String timeStr) {
        String[] parts = timeStr.split(":");
        int hour = Integer.parseInt(parts[0]) % 24;
        return LocalTime.of(hour, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    private long toMinutes(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    public void findPath(String startId, String endId, LocalTime startTime) {
        Node startNode = stops.get(startId);
        Node endNode = stops.get(endId);

        if(startNode == null || endNode == null) {
            System.out.println("Start eller slut nod saknas.");
            return;
        }

        PriorityQueue<SearchNode> openList = new PriorityQueue<>();
        Map<Node, Double> bestCost = new HashMap<>();

        double startMin = toMinutes(startTime);

        openList.add(new SearchNode(startNode, startTime, 0, getHeuristic(startNode, endNode), null, null));

        while(!openList.isEmpty()) {
            SearchNode current = openList.poll();

            if(current.node.equals(endNode)) {
                printPath(current);
                return;
            }

            if(bestCost.containsKey(current.node) && bestCost.get(current.node) <= current.costFromStart) {
                continue;
            }
            bestCost.put(current.node, current.costFromStart);

            for(Departure dep : current.node.departures) {
                double depMin = toMinutes(dep.departureTime);
                double arrMin = toMinutes(dep.arrivalTime);
                double currentMin = toMinutes(current.currentTime);

                if(depMin < currentMin) {
                    depMin += 1440;
                }
                if(arrMin < depMin) {
                    arrMin += 1440;
                }

                double travelDuration = arrMin - currentMin;
                double newG = current.costFromStart + travelDuration;

                double h = getHeuristic(dep.destination, endNode);
                double f = newG + h;

                LocalTime nextTime = dep.arrivalTime;

                openList.add(new SearchNode(dep.destination, nextTime, newG, f, current, dep));
            }
        }
        System.out.println("Ingen väg Hittades");
    }

    private double getHeuristic(Node current, Node goal) {
        double latDiff = current.latitude - goal.latitude;
        double lonDiff = current.longitude - goal.longitude;
        double distance = Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111;
        return distance;
    }

    private void printPath(SearchNode target) {
        if(target == null) {
            System.out.println("Ingen väg hittades");
            return;
        }

        List<SearchNode> path = new ArrayList<>();
        SearchNode current = target;

        while(current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);

        System.out.println("Resplan hittad:");
        System.out.println("-----------------------------------------");

        for(int i = 0; i < path.size(); i++) {
            SearchNode step = path.get(i);

            if(i == 0) {
                System.out.println("Starta vid: " + step.node.name + " kl. " + step.currentTime);
            } else {
                Departure dep = step.arrivalDeparture;
                System.out.println("Kliv på trip " + dep.tripId + " vid " + path.get(i-1).node.name + " kl. " + dep.departureTime);

                System.out.println("Ankom till: " + step.node.name + " kl. " + dep.arrivalTime);
            }
            System.out.println("--------------------------------------------");
        }
        System.out.println("Total restid: " + target.costFromStart + " minuter.");
    }
}
