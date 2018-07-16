import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
  private final List<LineSegment> lineSegments;

  /**
   * finds all line segments containing 4 or more points
   *
   * @param points
   */
  public FastCollinearPoints(Point[] points) {
    // exception1: if the array == null
    if (points == null) throw new IllegalArgumentException();
    // exception2: if the array has null elements
    // exception3: if the array exists repeated points
    // O(nlgn)
    Arrays.sort(points);
    // O(n)
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) throw new IllegalArgumentException();
      if (i > 0 && points[i].compareTo(points[i - 1]) == 0) throw new
              IllegalArgumentException();
    }
    lineSegments = new LinkedList<>();
    if (points.length < 4) return;

    Point[] clone = new Point[points.length - 1];
    // O(n * (n + nlgn + n))
    Point vertex; //当前参考点
    for (int index = 0; index < points.length; index++) {
      vertex = points[index]; //参考点初始化
      for (int j = 0; j < clone.length; j++) {
        if (j < index) clone[j] = points[j];
        if (j >= index) clone[j] = points[j + 1];
      }
      // 按照和参考点的slope排序
      Arrays.sort(clone, vertex.slopeOrder());
      int length;
      for (int j = 0; j < clone.length - 2; j += length) {
        double slope1 = vertex.slopeTo(clone[j]);
        int tail = j + 1;
        while (tail < clone.length && slope1 == vertex.slopeTo(clone[tail])) {
          tail++;
        }
        length = tail - j;
        if (length >= 3 && vertex.compareTo(clone[j]) == -1) {
          LineSegment lineSegment = new LineSegment(vertex, clone[tail - 1]);
          lineSegments.add(lineSegment);
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

  /**
   * the line segments
   *
   * @return
   */
  public LineSegment[] segments() {
    LineSegment[] res = new LineSegment[numberOfSegments()];
    int i = 0;
    for (LineSegment lineSegment : lineSegments) {
      res[i] = lineSegment;
      i++;
    }
    return res;
  }
}