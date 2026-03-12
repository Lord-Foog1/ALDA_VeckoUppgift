// Oliver Nordander olno1943

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestPointsCalculator {

	private static Comparator<Point> compareX = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			return Integer.compare(p1.x(), p2.x());
		}
	};

	private static Comparator<Point> compareY = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			return Integer.compare(p1.y(), p2.y());
		}
	};

	public static Point[] findClosestPairOfPoints(Point[] points) {
		if(points == null || points.length < 2) {
			return null;
		}
		if(points.length == 2) {
			return points;
		}

		Arrays.sort(points, compareX);

		int mid = points.length/2;
		int midX = points[mid].x();

		Point[] pLeft = Arrays.copyOfRange(points, 0, mid);
		Point[] pRight = Arrays.copyOfRange(points, mid, points.length);

		Point[] dl = findClosestPairOfPoints(pLeft);
		Point[] dr = findClosestPairOfPoints(pRight);

		Point[] bestSoFar = null;
		if(dl == null) {
			bestSoFar = dr;
		} else if(dr == null) {
			bestSoFar = dl;
		} else {
			double distL = dl[0].distanceTo(dl[1]);
			double distR = dr[0].distanceTo(dr[1]);
			bestSoFar = (distL < distR) ? dl : dr;
		}

		if(bestSoFar == null) {
			return null;
		}

		double d = bestSoFar[0].distanceTo(bestSoFar[1]);

		List<Point> strip = new ArrayList<>();
		for(Point p : points) {
			if(Math.abs(p.x() - midX) < d) {
				strip.add(p);
			}
		}

		Point[] stripPoints = stripClosest(strip.toArray(new Point[0]), d);

		if(stripPoints != null && stripPoints[0] != null) {
			if(stripPoints[0].distanceTo(stripPoints[1]) < d) {
				return stripPoints;
			}
		}
		return bestSoFar;
	}

	static private Point[] stripClosest(Point[] points, double d) {
		double minDist = d;
		Point[] result = new Point[2];

		Arrays.sort(points, compareY);

		for(int i = 0; i < points.length; i++) {
			for(int j = i + 1; j < points.length && (points[i].y() - points[j].y()) < minDist; j++) {
				if(points[i].distanceTo(points[j]) < minDist) {
					minDist = points[i].distanceTo(points[j]);
					result[0] = points[i];
					result[1] = points[j];
				}
			}
		}
		return result;
	}
}
