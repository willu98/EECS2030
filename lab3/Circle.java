package eecs2030.lab3;

/**
 * 
 * The {@code Circle} class is immutable class that contains one variable to
 * store the radius of the circle, and methods to get its area and
 * circumference. The class implements {@link Comparable} interface to compare
 * two circles. It also overrides {@code equals}, {@code hashCode}, and
 * {@code toString} from the {@link Object} class.
 * 
 * @author EECS2030 Summer 2019
 */
public class Circle implements Comparable<Circle> {
	/**
	 * Stores the number of objects instantiated from the {@code Circle} class
	 */
	private static int numCircles = 0;

	/**
	 * The radius of the circle
	 */
	private final double radius;

	/**
	 * Initializes the {@link Circle} object using the provided radius argument.
	 * 
	 * <p>
	 * Before assigning radius value to the instance variable {@link radius}, this
	 * constructor verifies that the provided radius is positive otherwise it throws
	 * {@link IllegalArgumentException}.
	 * 
	 * <p>
	 * This constructor also increments the static variable
	 * {@link Circle#numCircles} which tracks number of circle objects created.
	 * 
	 * 
	 * @param radius the radius of this circle
	 * 
	 * @throws IllegalArgumentException if the provided radius is not positive
	 */
	public Circle(double radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException("Radius must be positive");
		} else {
			this.radius = radius;
		}
		this.numCircles++;
	}

	/**
	 * Accessor method that returns the radius of the circle
	 * 
	 * @return the radius of this circle
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * This method returns the area of the circle. The area of a circle is $$PI *
	 * radius^2$$.
	 * 
	 * @return the circumference of the circle
	 */
	public double getArea() {
		return Math.PI * radius * radius;
	}

	/**
	 * This method returns the circumference of the circle. The circumference of a
	 * circle is $$2 * PI * radius$$.
	 * 
	 * @return the circumference of the circle
	 */
	public double getCircumference() {
		return Math.PI * 2 * this.radius;
	}

	/**
	 * This static method returns the number of objects instantiated from the
	 * {@code Circle} class.
	 * 
	 * @return the number of {@code Circle} objects.
	 */
	public static int getNumCircles() {
		return Circle.numCircles;
	}

	/**
	 * Compares area of this circle object and the other circle {@code c}.
	 *
	 * @param c the other circle @return the value {@code -1 } if the area of this
	 *          circle is less than the argument's circle area; {@code +1} if the
	 *          area of this circle is greater than the argument's; and {@code 0} if
	 *          this area of this circle is equal to the argument's within the
	 *          specified tolerance {@link ShapeUtil#TOL}.
	 * 
	 * @throws IllegalArgumentException if the argument is null
	 */
	@Override
	public int compareTo(Circle c) {
		if (c == null) {
			throw new IllegalArgumentException("arguement cicle cannot be null");
		}
		if (this.equals(c)) {
			return 0;
		} else if (this.getArea() > c.getArea()) {
			return 1;
		}
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Circle test = (Circle) obj;

		// if they have a diff radius larger than e^-6
		if (Math.abs(this.radius - test.radius) > ShapeUtil.TOL) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Circle [" + radius + "]";
	}
}
