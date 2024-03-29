package eecs2030.lab4;

import java.util.HashSet;
import java.util.Objects;

/**
 * This class models a course.
 * 
 * @author EECS2030 Summer 2019
 *
 */
public class Course implements Comparable<Course> {

	/**
	 * the code of the course formatted in capital letters and numbers only, e.g.
	 * "EECS2030"
	 */
	private final String code;

	/**
	 * the title of the course, e.g. "Advanced Object Oriented Programming"
	 */
	private final String title;

	/**
	 * the set of prerequisite courses codes to this course
	 */
	private final HashSet<String> prerequisite = new HashSet<String>();

	/**
	 * Initialize the new Course object.
	 * 
	 * @param title the title of the Course to set
	 * @param code  the code of the Course to set. Course code is formatted using
	 *              capital letters and numbers.
	 */
	public Course(String code, String title) {
		this.code = code;
		this.title = title;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the set of prerequisite courses to this course
	 */
	public HashSet<String> getPrerequisite() {
		return prerequisite;
	}

	/**
	 * Add a course to the set of prerequisite courses
	 * 
	 * @param courseCode the code of the prerequisite course
	 */
	public void addPrerequisite(String courseCode) {
		prerequisite.add(courseCode);
	}

	/**
	 * Compare this Course object and other Course object.
	 * 
	 * A Course 'c1' is "smaller" than another 'c2' if c1's code is alphabetically
	 * smaller than c2's.
	 * 
	 * When being sorted in an increasing order (using Arrays.sort), the smaller
	 * Course object appears earlier than the larger one.
	 * 
	 * @param other the other Course to compare this Course to
	 * @return Positive value if this is larger than other, negative if this is
	 *         smaller than other, zero otherwise.
	 */
	@Override
	public int compareTo(Course other) {
		if (this.toString().compareToIgnoreCase(other.toString()) > 0) {
			return 1;
		} else if (this.toString().compareToIgnoreCase(other.toString()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Two Course objects are equal if their {@code code}s are equal.
	 * 
	 * @return Whether this Course object equals 'obj'
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

		Course test = (Course) obj;

		if (!this.code.equals(test.code)) {
			return false;
		}

		return true;
	}

	/**
	 * Returns a hash code based on the Course's {@code code} only
	 */
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public String toString() {
		return String.format("Course [%s - %s]", code, title);
	}

}
