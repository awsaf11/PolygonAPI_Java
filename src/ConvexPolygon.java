import java.awt.geom.Point2D;
import java.util.Scanner;

/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019

 * Assignment 1: Polygon Hierarchy
 * Section:  M  
 * Student Name: Nafis Ahmed Awsaf
 * Student eecs account:  awsaf11
 * Student ID number:    215306095
 **********************************************************/

/**
 * The class ConvexPolygon extends SimplePolygon.
 * 
 * TODO:_______ Add more Javadoc comments here. ______ ???
 * 
 * @author Andy Mirzaian
 */
public class ConvexPolygon extends SimplePolygon {

	public ConvexPolygon(int size) {
		super(size);
	}

	public ConvexPolygon() {
		super();
	}

	/*
	 * Helper method for isConvex, finding out the signs for each case, sign +ve
	 * (orientation : CCW ),sign -ve (orientation : CW),sign 0 (orientation :
	 * collinear)
	 *
	 */

	private int deltasign(double delta) {
		if (delta > 0) {
			return 1;
		} else if (delta < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	public boolean isConvex() {
		/* Checking for the precondition of isConvex */

		if (!isSimple()) {
			return false;
		}
		/*
		 * G
		 */
		int initialdelta = deltasign(delta(this.getVertex(0), this.getVertex(1), this.getVertex(2)));
		for (int i = 1; i < n; i++) {
			/*
			 * Getting the orientation of the next angle,
			 */
			int deltaRes = deltasign(
					delta(this.getVertex(i), this.getVertex((i + 1) % n), this.getVertex((i + 2) % n)));

			/*
			 * comparing the orientation with the initial orientation and if they are not
			 * the same or not collinear then this is not convex and return false.
			 */
			if (deltaRes != 0 && deltaRes != initialdelta) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Creating a getNewPolyMethod for this class as the previous method cannot be
	 * called here
	 */
	public static ConvexPolygon getNewPoly() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Creating a Polygon");
		System.out.println("Enter no of vertices");
		int size = scanner.nextInt();
		ConvexPolygon p = new ConvexPolygon(size);

		for (int i = 0; i < size; i++) {
			double x = scanner.nextDouble();
			double y = scanner.nextDouble();
			p.vertices[i].x = x;
			p.vertices[i].y = y;
		}

		return p;
	}
}