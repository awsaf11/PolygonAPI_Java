
/**********************************************************
 * Assignment 1: Polygon Hierarchy

 * Section:  M  
 * Student Name:  Nafis Ahmed Awsaf
 * Student eecs account:  awsaf11
 * Student ID number:  215306095
 **********************************************************/

import java.awt.geom.Point2D;
import java.util.Scanner;

/**
 * The class SimplePolygon implements the Polygon interface.
 * 
 * It is intended to be further extended by ConvexPolygon.
 * 
 * @author Andy Mirzaian
 */
public class SimplePolygon implements Polygon {

	/********* protected fields ************************/

	protected int n; // number of vertices of the polygon
	protected Point2D.Double[] vertices; // vertices[0..n-1] around the polygon boundary

	/********* protected constructors ******************/

	/**
	 * constructor used in the static factory method getNewPoly()
	 * 
	 * @param size number of edges (also vertices) of the polygon
	 */
	protected SimplePolygon(int size) {
		n = size;
		vertices = new Point2D.Double[size]; // initializing the array
		for (int i = 0; i < size; i++) {
			vertices[i] = new Point2D.Double();
		}
	}

	/** default no-parameter constructor */
	protected SimplePolygon() {
		// TODO: place your code here
		n = 0;
		vertices = new Point2D.Double[0];
	}

	/********* public getters & toString ***************/

	/**
	 * static factory method constructs and returns an unverified simple-polygon,
	 * initialised according to user provided input data. Runs in O(n) time.
	 * 
	 * @return an unverified simple-polygon instance
	 */
	public static SimplePolygon getNewPoly() {
		// TODO: replace this line with your code

		Scanner scanner = new Scanner(System.in);
		System.out.println("Creating a Polygon");
		System.out.println("Enter no of vertices");
		int size = scanner.nextInt();
		SimplePolygon p = new SimplePolygon(size);

		for (int i = 0; i < size; i++) {
			double x = scanner.nextDouble();
			double y = scanner.nextDouble();
			p.vertices[i].x = x;
			p.vertices[i].y = y;
		}

		return p;
	}

	/**
	 * 
	 * @return n, the number of edges (equivalently, vertices) of the polygon.
	 */
	public int getSize() {
		return this.vertices.length;
	}

	/**
	 * 
	 * @param i index of the vertex.
	 * @return the i-th vertex of the polygon.
	 * @throws IndexOutOfBoundsException if {@code i < 0 || i >= n }.
	 */
	public Point2D.Double getVertex(int i) throws IndexOutOfBoundsException {
		try {
			return vertices[i];
		} catch (Exception e) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * @return a String representation of the polygon in O(n) time.
	 */
	@Override
	public String toString() {
		String points = "";
		for (int i = 0; i < vertices.length; i++) {
			points += "[" + "(" + vertices[i].x + ", " + vertices[i].y + ")" + "] ";
		}
		return points;
	}

	/************** utilities *********************/

	/**
	 * 
	 * @param a
	 * @param b
	 * @param c three input points.
	 * @return twice the signed area of the oriented triangle (a,b,c). Runs in O(1)
	 *         time.
	 */
	public static double delta(Point2D.Double a, Point2D.Double b, Point2D.Double c) {

		return (a.x * (b.y - c.y)) - (a.y * (b.x - c.x)) + ((b.x * c.y) - (b.y * c.x));

	}

	/**
	 * @param a
	 * @param b end points of line-segment (a,b).
	 * @param c
	 * @param d end points of line-segment (c,d).
	 * @return true if closed line-segments (a,b) and (c,d) are disjoint. Runs in
	 *         O(1) time.
	 */
	public static boolean disjointSegments(Point2D.Double a, Point2D.Double b, Point2D.Double c, Point2D.Double d) {
		double i, j, k, l;
		i = (delta(a, b, c));
		j = (delta(a, b, d));
		k = (delta(c, d, a));
		l = (delta(c, d, b));

		if ((((k <= 0) == (l <= 0)) || ((i <= 0) == (j <= 0)))) {
			return true; /*
							 * if the delta of cda and cdb have the same orientation (CCW or collinear) , OR
							 * the delta of abc and abd have the same orientation, then the lines are
							 * disjoint and if all the points are collinear (we do not care about the
							 * overlapping point case as per discussed in class
							 */
		} else if (((k > 0) == (l > 0)) || ((i > 0) == (j > 0))) {
			return true; /* the case where the orientation is CW (clockwise) */
		} else {
			return false; /*
							 * if neither of the cases, then the lines are not disjoint (e.g intersecting)
							 */
		}
	}

	/**
	 * @param i
	 * @param j indices of two edges of the polygon.
	 * @return true if the i-th and j-th edges of the polygon are disjoint. Runs in
	 *         O(1) time.
	 * @throws IndexOutOfBoundsException if i or j are outside the index range
	 *                                   [0..n-1].
	 */
	public boolean disjointEdges(int i, int j) throws IndexOutOfBoundsException {
		try {
			return disjointSegments(this.vertices[i], this.vertices[(i + 1) % this.n], this.vertices[j],
					/* using mod to avoid indexOutOfBoundsException and consider all lines */
					this.vertices[(j + 1) % this.n]);
		} catch (Exception e) {
			throw new IndexOutOfBoundsException();

		}
	}

	/**
	 * This method verifies whether the claimed "simple-polygon" is indeed simple.
	 * 
	 * @return true if the polygon is simple. Runs in O(n^2) time.
	 */
	public boolean isSimple() {

		for (int i = 0; i < n; i++) {
			for (int j = i + 2; j < n - 2; j++) {
				if (disjointEdges(i, j)) {
					continue; // if disjointEdge(i,j) returns true
				} else {
					return false;
				}
			}
		}
		return true;
	}

	/************ perimeter & area ***************/

	/**
	 * 
	 * @return the sum of the edge lengths of the polygon. Runs in O(n) time.
	 */
	public double perimeter() {
		double circumference = 0;
		for (int i = 0; i < n; i++) {
			double dst = 0;
			if (i == n - 1) {
				/*
				 * to ensure index out of bound exception is not thrown at the last iteration
				 * and consider the line vertex at index n-1 and index 0
				 */
				/*
				 * break at the end to ensure that the if branch is the last iteration of the
				 * loop
				 */
				dst = Point2D.distance(vertices[i].x, vertices[i].y, vertices[0].x, vertices[0].y);

				circumference = circumference + dst;
				break;

			}
			dst = Point2D.distance(vertices[i].x, vertices[i].y, vertices[i + 1].x, vertices[i + 1].y);
			circumference = circumference + dst;
		}

		return circumference;
	}

	/**
	 * 
	 * @return area of the polygon interior. Runs in O(n) time not counting the
	 *         simplicity test.
	 * @throws NonSimplePolygonException if the polygon is non-simple.
	 */
	public double area() throws NonSimplePolygonException {
		try {
			/*
			 * Checking the precondition for area
			 */
			if (isSimple()) {
				double sum = 0;
				Point2D.Double a = new Point2D.Double(1, 1); /* Fixed point */
				Point2D.Double b = new Point2D.Double(0, 0); /* Point b and c to be updated at every iteration */
				Point2D.Double c = new Point2D.Double(0, 0);
				if (isSimple()) {
					for (int i = 0; i < n; i++) {
						if (i == n - 1) { /*
											 * to ensure index out of bound exception is not thrown at the last
											 * iteration and consider the line vertex at index n-1 and index 0
											 */

							b.setLocation(vertices[i].x, vertices[i].y);
							c.setLocation(vertices[0].x, vertices[0].y);
							sum = sum + delta(a, b, c);
							break;
						}

						/*
						 * Incrementing the points b and c with the fixed point a and calculating the
						 * area triangle by triangle and accumulating the sum
						 */
						b.setLocation(vertices[i].x, vertices[i].y);
						c.setLocation(vertices[i + 1].x, vertices[i + 1].y);
						sum = sum + delta(a, b, c);
					}
				}
				/*
				 * */
				return 0.5 * Math.abs(sum);
			} else {
				throw new NonSimplePolygonException();
			}
		} catch (NonSimplePolygonException e) {
			throw new NonSimplePolygonException();
		}

	}

}
