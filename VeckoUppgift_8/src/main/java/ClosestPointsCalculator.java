public class ClosestPointsCalculator {
	
	public static Point[] findClosestPairOfPoints(Point[] points) {
		double minDist = Double.POSITIVE_INFINITY;
		Point[] result = new Point[2];

		for(int i = 0; i < points.length; i++) {
			for(int j = i + 1; j < points.length; j++) {
				if(points[i] == points[j]) {
					break;
				}
				double currDist = points[i].distanceTo(points[j]);
				if(currDist < minDist) {
					minDist = currDist;
					result[0] = points[i];
					result[1] = points[j];
				}
			}
		}
		return result;
	}

}
