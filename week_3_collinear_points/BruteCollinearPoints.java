import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

  private final List<LineSegment> lineSegments;

  /**
   * finds all line segments containing 4 points
   *
   * @param points
   */
  public BruteCollinearPoints(Point[] points) {
    // exception1: if the array == null
    if (points == null) throw new java.lang.IllegalArgumentException();
    // exception2: if the array has null elements
    // exception3: if the array exists repeated points
    Arrays.sort(points);
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) throw new java.lang.IllegalArgumentException();
      if (i > 0 && points[i].compareTo(points[i - 1]) == 0) throw new java.lang
              .IllegalArgumentException();
    }
    int size = points.length;
    lineSegments = new LinkedList<>();
    // n choose 4
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        double slope12 = points[i].slopeTo(points[j]);
        for (int h = j + 1; h < size; h++) {
          double slope13 = points[i].slopeTo(points[h]);
          if (slope12 != slope13) continue;
          for (int z = h + 1; z < size; z++) {
            double slope14 = points[i].slopeTo(points[z]);
            if (slope12 == slope14) {
              LineSegment segment = new LineSegment(points[i], points[z]);
              lineSegments.add(segment);
            }
          }
        }
      }
    }
  }

  /**
   * the number of line segments
   *
   * @return
   */
  public int numberOfSegments() {
    return lineSegments.size();
  }

  public LineSegment[] segments() {
    int segNum = lineSegments.size();
    LineSegment[] res = new LineSegment[segNum];
    for (int i = 0; i < segNum; i++) {
      res[i] = lineSegments.get(i);
    }
    return res;
  }
}
