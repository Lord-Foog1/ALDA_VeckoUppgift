import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    private static final Random RND = new Random();

    public static void main(String[] args) {
        Set<Point> points = new HashSet<>();
        while (points.size() < 100) {
            points.add(new Point(RND.nextInt(100), RND.nextInt(100)));
        }

        Point[] p = points.toArray(new Point[100]);

        ClosestPointsCalculator c = new ClosestPointsCalculator();

        System.out.println(c.findClosestPairOfPoints(p));
    }
}
