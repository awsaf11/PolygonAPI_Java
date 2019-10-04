import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

/**********************************************************
 * EECS2011: Fundamentals of Data Structures,  Winter 2019
 * Assignment 1: Polygon Hierarchy
 * Section:  M  
 * Student Name: Nafis Ahmed Awsaf  
 * Student eecs account:  awsaf11
 * Student ID number:  215306095
 **********************************************************/

/**
 * PolygonTester should enable a thorough testing of the polygon hierarchy.
 * 
 * It should provide an easy to read input-output recording of the test cases.
 * 
 * The student should also submit these recorded test results in TestIO.txt file
 * as part of Assignment1.
 * 
 * @author Andy Mirzaian
 */
public class PolygonTester {

	public static void main(String[] args) {

		/*
		 * Tester for : size, toString, isSimple, isConvex, area, perimeter
		 */
		ConvexPolygon a = ConvexPolygon.getNewPoly();
		System.out.println(" The size of the Polygon is : " + a.n);
		System.out.println(" Representation of the points :" + a.toString());
		System.out.println(" PolygonType : isSimple ? " + a.isSimple());
		System.out.println(" PolygonType : isConvex ? " + a.isConvex());
		try {
			System.out.println(" Area of the Polygon is : " + a.area());

		} catch (NonSimplePolygonException e) {
			System.out.println(
					" NonSimplePolygonException thrown!\n The Polygon is Non Simple : Cannot calculate area !");
		}
		System.out.println(" Perimeter of the Polygon is : " + a.perimeter());

		/*
		 * Tester for disjointSegments, disjointEdges Tests if two line segments
		 * intersects or not given the 4 points input by the user
		 */
		/* Poly1 */
		Point2D.Double p1 = new Point2D.Double(8.9, 21.8);
		Point2D.Double p2 = new Point2D.Double(29.1, 8.8);
		Point2D.Double p3 = new Point2D.Double(39.2, 20.3);
		Point2D.Double p4 = new Point2D.Double(14, 11);
		Point2D.Double p5 = new Point2D.Double(28, 25);
		boolean lineABtoCD = SimplePolygon.disjointSegments(p1, p2, p3, p4);
		System.out.println(" DisjointSegments ? : " + lineABtoCD);

		System.out.println(" Disjoint Edges ?   : " + SimplePolygon.getNewPoly().disjointEdges(0, 4));

		/*
		 * Tester for getVertex Poly1 Depending on the polygon, these tests will
		 * determine if it will throw an IndexOutOfBoundsException or not.
		 */
		System.out.println(" Get Vertex   : " + SimplePolygon.getNewPoly().getVertex(3));
		System.out.println(" Get Vertex   : " + SimplePolygon.getNewPoly().getVertex(54));

	}
}
