/**
 * 
 */
package eecs2030.lab2;

import java.util.Arrays;

/**
 * The {@code Polynomial} class represents a polynomial of a single
 * indeterminate with double coefficients. A polynomial is an an expression
 * consisting of variables with non-negative integer exponents and real
 * coefficients. The set of operations supported by the {@code Polynomial} class
 * includes: addition, subtraction, multiplication, function composition,
 * polynomial derivative, and evaluation.
 * 
 * <p>
 * Examples of polynomials with a single indeterminate, x, are:
 * 
 * <pre>
 * p(x) = x^2 - 5.0x + 1.0
 * p(x) = x^6 + 4.1x^4 + 0.5x - 7.2
 * p(x) = 1.0
 * </pre>
 * 
 * @author EECS2030 Summer 2019
 *
 */
public class Polynomial implements Comparable<Polynomial> {

	/**
	 * Array of the polynomial coefficients.
	 * 
	 * <pre>
	 * p(x) = cf[0] * x^0 + ... + cf[n-1] * x^(n-1)
	 * </pre>
	 */
	private double[] cf;

	/**
	 * The degree of the polynomial. That is the largest exponent with non zero
	 * coefficient. If all the coefficients are zero, the degree is zero. To set the
	 * value of {@code degree}, implement and call {@code findDegree()} method every
	 * time the polynomial is created or an operation is performed
	 */
	private int degree;

	/**
	 * Creates a new polynomial {@code a * x^i}
	 * 
	 * @param a the leading coefficient
	 * @param i the exponent
	 * @throws IllegalArgumentException if {@code i} is negative
	 */
	public Polynomial(double a, int i) {
		// throwing exception for negative exponent
		if (i < 0) {
			throw new IllegalArgumentException("The exponent cannot be negative");
		}

		// first
		cf = new double[i + 1];
		for (int j = 0; j < cf.length; j++) {
			if (j < cf.length - 1) {
				cf[j] = 0;
			} else {
				cf[j] = a;
			}
		}
		findDegree();

	}

	/**
	 * A helper function for testing. Do not remove or change.
	 */
	protected int test_getDegree() {
		return degree;
	}

	/**
	 * A helper function for testing. Do not remove or change.
	 */
	protected void test_setDegree(int d) {
		degree = d;
	}

	/**
	 * A helper function for testing. Do not remove or change.
	 */
	protected double[] test_getCf() {
		return cf;
	}

	/**
	 * Creates a new polynomial from array of coefficients. The new polynomial has
	 * the form
	 * 
	 * <pre>
	 * cf[0] * x^0 + ... + cf[n-1] * x^(n-1)
	 * </pre>
	 * 
	 * where {@code n} is the length of the coefficients array.
	 * 
	 * @param cf the coefficients array
	 * @throws IllegalArgumentException if {@code cf} is empty
	 */
	public Polynomial(double[] cf) {
		if (cf.length == 0) {
			throw new IllegalArgumentException();
		}

		this.cf = cf;

		findDegree();

//		degree = cf.length - 1;
	}

	/**
	 * Creates a polynomial with zero degree and one zero coefficient.
	 * 
	 * <pre>
	 * p(x) = 0
	 * </pre>
	 */
	public Polynomial() {
		cf = new double[] { 0 };
		degree = 0;
	}

	/**
	 * Finds the polynomial {@code degree}, which is the largest exponent with non
	 * zero coefficient. If all the coefficients are zero, the degree is zero.
	 */
	private void findDegree() {

		// degree is set to zero
		degree = 0;

		// checking though all of the coefficients from highest degree to lowest
		for (int i = cf.length - 1; i >= 0; i--) {

			// the first coefficient found corresponds to the highest degree
			if (cf[i] != 0) {

				degree = i;

				// stop the loop
				i = -1;
			}
		}

	}

	/**
	 * Returns the degree of the polynomial. A polynomial with zero coefficients has
	 * a zero degree.
	 * 
	 * @return the degree of the polynomial
	 */
	public int getDegree() {
		return this.degree;
	}

	/**
	 * Returns the sum of this polynomial {@code p}} and the other polynomial
	 * {@code q} the argument.
	 *
	 * @param q the other polynomial
	 * @return the polynomial whose value is {@code (p(x) + q(x))}
	 */
	public Polynomial plus(Polynomial q) {

		Polynomial test;

		// if p.degree > q.degree, new polynomial has degree of p
		if (this.degree > q.degree) {

			test = new Polynomial(this.cf);

			for (int i = 0; i <= q.degree; i++) {
				// add the coefficients together
				test.cf[i] = this.cf[i] + q.cf[i];
			}
		}
		// if q.degree > p.degree new polynomial has degree of q
		else {

			test = new Polynomial(q.cf);

			for (int i = 0; i <= this.degree; i++) {
				test.cf[i] = this.cf[i] + q.cf[i];
			}
		}

		return test;
	}

	/**
	 * Returns the result of subtracting the specified polynomial {@code q} from
	 * this polynomial {@code p}.
	 *
	 * @param q the other polynomial
	 * @return the polynomial whose value is {@code (p(x) - q(x))}
	 */
	public Polynomial minus(Polynomial q) {
		Polynomial test;

		if (this.degree > q.degree) {

			test = new Polynomial(this.cf);

			for (int i = 0; i <= q.degree; i++) {
				test.cf[i] = this.cf[i] - q.cf[i];
			}
		} else {

			test = new Polynomial(q.cf);

			for (int i = 0; i <= this.degree; i++) {
				test.cf[i] = this.cf[i] - q.cf[i];
			}
		}

		return test;
	}

	/**
	 * Returns the product of this polynomial {@code p} and the specified polynomial
	 * {@code q}.
	 *
	 * @param q the other polynomial
	 * @return the polynomial whose value is {@code (p(x) * q(x))}
	 */
	public Polynomial times(Polynomial q) {
		// the highest number of terms for the new polynomial
		int numTerms = (q.degree) + (this.degree) + 1;
		double[] newCf = new double[numTerms];

		// initializing all coefficients to be zero
		for (int i = 0; i < newCf.length; i++) {
			newCf[i] = 0;
		}

		for (int i = 0; i <= this.degree; i++) {
			for (int j = 0; j <= q.degree; j++) {
				newCf[i + j] += this.cf[i] * q.cf[j];
			}
		}
		return new Polynomial(newCf);
	}

	/**
	 * Returns the composition of this polynomial {@code p} and the specified
	 * polynomial {@code q}.
	 *
	 * @param q the other polynomial
	 * @return the polynomial whose value is {@code (p(q(x)))}
	 */
	public Polynomial compose(Polynomial q) {

		// degree of new polynomial is q.degree * p.degree, num of terms is degree + 1
		double[] test = new double[q.degree * this.degree + 1];

		// initializing all values to zero
		for (int i = 0; i < test.length; i++) {
			test[i] = 0;
		}

		Polynomial finalPoly = new Polynomial(test);
		Polynomial temp1 = null;
		Polynomial temp2 = null;

		// transversing though all the coefficients of p, from cf[cf.length - 1]->cf[0]
		// great -> least so that each coefficient is multiplied by polynomial q, n
		// times, where n is the exponent on x
		// giving, cf[0] + cf[1] * q + cf[2] * q^2 + ... + cf[n] * q^n, simplified w
		// distributive formula on cf1 + q(cf2 + q(cf3 + q...(cfn + q*0))...)
		for (int i = this.degree; i >= 0; i--) {
			// temp1 is the polynomial with one term, cf[i]
			temp1 = new Polynomial(new double[] { this.cf[i] });

			// temp2 is the polynomial, to which we continue to multiply q into
			temp2 = q.times(finalPoly);

			/*
			 * for(int j = 0; j < temp2.cf.length; j++) { System.out.println(temp2.cf[j]); }
			 * System.out.println("yeet98");
			 */

			// each iteration we add another coefficient
			finalPoly = temp1.plus(temp2);

			/*
			 * for(int j = 0; j < finalPoly.cf.length; j++) {
			 * System.out.println(finalPoly.cf[j]); } System.out.println("yeet22");
			 */

		}

		// return cf[0] + cf[1] * q + cf[2] * q^2 + ... + cf[n] * q^n
		return finalPoly;
	}

	/**
	 * Returns the result of deriving this polynomial {@code p}.
	 *
	 * @return the polynomial whose value is {@code p'(x)}
	 */
	public Polynomial derive() {
		Polynomial newPoly;
		double[] newCf = new double[cf.length - 1];

		if (this.degree != 0) {
			for (int i = 0; i < cf.length - 1; i++) {
				newCf[i] = cf[i + 1] * (i + 1);
			}
			newPoly = new Polynomial(newCf);
		} else {
			return this;
		}

		return newPoly;
	}

	/**
	 * Returns the result of evaluating this polynomial {@code p} at the value x.
	 *
	 * @param x the value at which to evaluate the polynomial
	 * @return the value of evaluating {@code (p(x))}
	 */
	public double evaluate(double x) {
		double finalCalc = 0;
		for (int i = 0; i <= degree; i++) {
			finalCalc += cf[i] * Math.pow(x, i);
		}
		return finalCalc;
	}

	/**
	 * Returns a hash code for this polynomial. The hash code value is equal to the
	 * degree of the polynomial multiplied with 31 plus the hashcode of the
	 * coefficients array.
	 * 
	 * @return 31 * degree + Arrays.hashCode(cf)
	 */
	@Override
	public int hashCode() {
		return degree * 31 + Arrays.hashCode(cf);
	}

	/**
	 * Compares this polynomial to the other object {@code obj}. The result is true
	 * if and only if the argument is a polynomial object having the same degree and
	 * coefficients as this polynomial.
	 * 
	 * @param obj an object to compare
	 * @return {@code true} if this polynomial equals to the specified object; and
	 *         {@code false} otherwise
	 */
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

		Polynomial test = (Polynomial) obj;

		// if they have a diff amount of coefficients return false
		if (test.cf.length != this.cf.length) {
			return false;
		}
		// if they do have same amount, check for degree and compare coefficients
		else {
			// comparing coefficients
			for (int i = 0; i < this.cf.length; i++) {
				if (this.cf[i] != test.cf[i]) {
					return false;
				}
			}
			// checking degree
			if (test.degree == this.degree) {
				return true;
			}
		}

		return true;
	}

	/**
	 * Compares this polynomial {@code p} and the other polynomial {@code q} by
	 * their degree. If the degrees are equal, the polynomial coefficients are
	 * compared, and so on.
	 *
	 * @param q the other polynomial
	 * @return the value {@code -1 } if this polynomial is less than the argument
	 *         polynomial; {@code +1} if this polynomial is greater than the
	 *         argument polynomial; and {@code 0} if this polynomial is equal to the
	 *         argument polynomial.
	 */
	@Override
	public int compareTo(Polynomial q) {
		if (this.degree > q.degree) {
			return 1;
		} else if (this.degree < q.degree) {
			return -1;
		}

		for (int i = q.cf.length - 1; i >= 0; i--) {
			if (this.cf[i] > q.cf[i]) {
				return 1;
			} else if (this.cf[i] < q.cf[i]) {
				return -1;
			}
		}
		return 0;
	}

	/**
	 * Return a string representation of this polynomial in the standard form. The
	 * standard form of a polynomial is to put the terms with the highest degree
	 * first. The following are examples of the returned string for different
	 * polynomials.
	 * 
	 * <pre>
	new Polynomial();                              //0.0
	new Polynomial(1,0)                            //1.0
	new Polynomial(1,1);                           //1.0x
	new Polynomial(new double[] {1,1});            //1.0x + 1.0
	new Polynomial(new double[] {0,0,2});          //2.0x^2
	new Polynomial(new double[] {0,1,2});          //2.0x^2 + 1.0x
	new Polynomial(new double[] {1,0,2});          //2.0x^2 + 1.0
	new Polynomial(new double[] {0.0001213,1,2});  //2.0x^2 + 1.0x + 1.213E-4
	 * </pre>
	 * 
	 * @return a string representation of this polynomial
	 */
	@Override
	public String toString() {
		String test = "";
		String s = "";

		boolean allZero = true;

		// the degree is 0 then
		if (degree == 0) {
			return "" + cf[0];
		}

		// if the degree of the polynomial is one and there is no constant
		if (degree == 1 && cf[0] == 0) {
			return cf[1] + "x";
		}

		// checking if there is only one term in the polynomial
		for (int i = 1; i <= degree; i++) {

			// checking all the cf before the first to see if they are zero
			for (int j = 0; j < degree; j++) {

				if (cf[j] != 0) {
					allZero = false;
				}
			}

			// return 1st term only, if there is only one term
			if (degree == i && cf[i - 1] == 0 && allZero == true) {
				return cf[i] + "x^" + i;
			}
		}

		// for loop going through each coefficient of the polynomial
		for (int i = degree; i >= 0; i--) {

			// if on the term cf * x
			if (i == 1) {
				// if there is no const at the end, polynomial ends with x
				if (cf[0] == 0) {
					s = "x";
				}
				// if there is a const, add a space after x to accommodate
				else if (cf[0] != 0) {
					s = "x ";
				}
			}
			// if on the first term, then
			else if (i == cf.length - 1) {
				s = "x^" + i + " ";
			}
			// continue if on last term
			else if (i == 0) {
				s = "";
			} else {
				s = " + " + "x^" + i;
			}

			if (cf[i] != 0) {
				if (i == cf.length - 1) {
					test += "" + cf[i] + s;
				} else if (cf[i] < 0 && i != cf.length - 1) {
					test += "- " + (-1 * cf[i]) + s;
				} else if (cf[i] > 0 && i != cf.length - 1) {
					test += "+ " + cf[i] + s;
				}
			}
		}
		return test;
	}

}
