
package eecs2030.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Utility class that deals with all the other classes.
 * 
 * @author EECS2030 Summer 2019
 *
 */
public final class Registrar {

	public static final String COURSES_FILE = "Courses.csv";
	public static final String STUDENTS_FILE = "Students.csv";
	public static final String PATH = System.getProperty("java.class.path");

	/**
	 * Hash table to store the list of students using their {@code id} as the key.
	 */
	public static HashMap<String, Student> students = new HashMap<String, Student>();
	/**
	 * Hash table to store the list of course using their {@code code} as the key.
	 */
	public static HashMap<String, Course> courses = new HashMap<String, Course>();
	/**
	 * The list of currently being courses offered.
	 */
	public static List<CourseOffering> offerings = new ArrayList<CourseOffering>();
	/**
	 * A list to hold the enrollments of students in the offered courses.
	 */
	public static List<StudentEnrollement> enrollments = new ArrayList<StudentEnrollement>();

	/**
	 * Enrolls a {@code student} in the course {@code offering}. This method
	 * verifies that 1) the course is open for enrollment, 2) the student has
	 * completed the prerequisite courses, and 3) the course is not full. If any of
	 * those conditions is not satisfied, the method throws an exception of the
	 * type @{@code Exception} with descriptive message showing the reason.
	 * Otherwise the method adds an object of {@link StudentEnrollement} class to
	 * the {@link enrollments} list. The enrollment object contains references the
	 * student to be enrolled and the course offering. The enrollment date shall be
	 * set to the current date.
	 * 
	 * @param student  the student to be enrolled
	 * @param offering the course offering available
	 * @throws Exception when the student cannot be enrolled
	 */
	public static void enroll(Student student, CourseOffering offering) throws Exception {
		if (offering.getOpenUntil().compareTo(student.getJoinDate()) < 0) {
			throw new Exception("Course is no longer open for enrollment!");
		}
		System.out.println(student.getCompletedCourses());
		System.out.println(offering.getCourse().getPrerequisite());
		if (!student.getCompletedCourses().containsAll(offering.getCourse().getPrerequisite())) {
			throw new Exception("not all prerequisites are fufilled!");
		}
		if (offering.getCapacity() <= getNumEnrolled(offering)) {
			throw new Exception("course is full!");
		}

		LocalDate today = LocalDate.now();

		enrollments.add(new StudentEnrollement(student, offering, today));

	}

	/**
	 * Returns the list of students enrolled in certain course offering.
	 * 
	 * @param offering certain course offering
	 * @return list of students enrolled in the course
	 */
	public static List<Student> getEnrolledStudents(CourseOffering offering) {
		List<Student> students = new ArrayList<Student>();

		for (StudentEnrollement enrolledStudents : enrollments) {
			if (enrolledStudents.getCourseOffering().equals(offering)) {
				students.add(enrolledStudents.getStudent());
			}
		}

		return students;
	}

	/**
	 * Returns the number of students enrolled in certain course offering. This
	 * method counts how many {@link StudentEnrollement} objects having their course
	 * offering equals to the argument.
	 * 
	 * @param offering the course offering to find the number of students enrolled
	 *                 in
	 * @return number of students enrolled in certain course offering.
	 */
	public static int getNumEnrolled(CourseOffering offering) {
		return getEnrolledStudents(offering).size();
	}

	/**
	 * Returns a reference to the course with title equals to the argument. This
	 * method searches in the courses stored in the HashMap {@code courses} to find
	 * the course whose title equals to the argument {@code title}. If the course is
	 * not found, {@code null} is returned.
	 * 
	 * @param title the title of the course
	 * @return a reference to the course, or {@code null} if the course is not
	 *         found.
	 */
	public static Course getCourseByTitle(String title) {
		for (String k : courses.keySet()) {

			Course temp = courses.get(k);
			if (title.equals(temp.getTitle())) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * Returns a reference to the Student whose name equals to the argument. This
	 * method searches in the students stored in the HashMap {@code students} to
	 * find the student whose name equals to the argument {@code name}. If the
	 * student is not found, {@code null} is returned.
	 * 
	 * @param name the student name
	 * @return the student whose name is specified in the argument, or {@code null}
	 *         if the student is not found
	 */
	public static Student getStudentByName(String name) {
		for (String k : students.keySet()) {

			Student temp = students.get(k);
			if (name.equals(temp.getName())) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * Add a new student entry to {@code students} HashMap.
	 * 
	 * @param id      id of the new student
	 * @param student object of the new student
	 * @throws Exception if the new {@code id} exists in the {@code students}
	 *                   HashMap
	 */
	public static void addStudent(String id, Student student) throws Exception {
		if (students.get(id) != null) {
			throw new Exception("id already exists");
		}
		students.put(id, student);
	}

	/**
	 * Remove an existing student entry from {@code students} HashMap.
	 * 
	 * @param id the 'id' of the student to be removed
	 * @throws Exception if the {@code id} does not exist in the {@code students}
	 *                   HashMap
	 */
	public static void removeStudent(String id) throws Exception {
		if (students.get(id) == null) {
			throw new Exception("id does not exist");
		}
		students.remove(id);
	}

	/**
	 * Returns the entry in the {@link offerings} list that has the information
	 * provided in the arguments. If the entry is not found, {@code null} is
	 * returned.
	 * 
	 * @param courseCode the code of the course offered
	 * @param section    section of the course
	 * @param term       term
	 * @param year       year
	 * @return Returns the entry in the {@link offerings} list that has the
	 *         information provided in the arguments
	 */
	public static CourseOffering getCourseOffering(String courseCode, char section, char term, int year) {
		for (CourseOffering courses : offerings) {
			if (courses.getSection() == section && courses.getTerm() == term && courses.getYear() == year
					&& courses.getCourse().getCode().equals(courseCode)) {
				return courses;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		// Reading the list of course from the file COURSES_FILE
		readCourses();
		if (courses.isEmpty())
			System.err.println("courses shall not be empty");

		// Reading the list of course from the file STUDENTS_FILE
		readStudents();
		if (students.isEmpty())
			System.err.println("students shall not be empty");
		// add few offerings
		addOfferings();
		if (offerings.isEmpty())
			System.err.println("offerings shall not be empty");

		CourseOffering courseOffer1 = getCourseOffering("EECS2030", 'A', 'W', 2020);
		if (courseOffer1 != null) {
			// enroll the student whose name is "Maximilian" in the first offering
			Student s = getStudentByName("Maximilian");
			if (s != null) {
				try {
					enroll(s, courseOffer1);
					System.out.println(s + " is enrolled in " + courseOffer1);
				} catch (Exception e) {
					System.err.println("Was unable to enroll " + s + " in " + courseOffer1 + "\n" + e.getMessage());
				}
			} else {
				System.err.println("Student Maximilian is not found");
			}

			s = getStudentByName("Alphonso");
			if (s != null) {
				try {
					enroll(s, courseOffer1);
					System.out.println(s + " is enrolled in " + courseOffer1);
				} catch (Exception e) {
					System.err.println("Was unable to enroll " + s + " in " + courseOffer1 + "\n" + e.getMessage());
				}
			} else {
				System.err.println("Student Maximilian is not found");
			}

		} else {
			System.err.println("Course EECS2030 is not offered in W2020");
		}
	}

	/**
	 * Populate the course offerings list with few entries.
	 */
	public static void addOfferings() {
		offerings.add(new CourseOffering(courses.get("EECS1021"), 20, 'F', 'A', 2020, LocalDate.of(2020, 9, 30)));
		offerings.add(new CourseOffering(getCourseByTitle("Object Oriented Programming"), 20, 'F', 'B', 2020,
				LocalDate.of(2020, 9, 30)));
		offerings.add(new CourseOffering(courses.get("EECS1022"), 30, 'F', 'A', 2020, LocalDate.of(2020, 9, 30)));
		offerings.add(new CourseOffering(courses.get("EECS2030"), 15, 'W', 'A', 2020, LocalDate.of(2020, 9, 30)));
	}

	/**
	 * Read a list of courses from the file {@link COURSES_FILE} and populate
	 * {@code course} HashMap.
	 */
	public static void readCourses() {
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new FileInputStream(PATH + "/eecs2030/lab4/" + COURSES_FILE));
		} catch (FileNotFoundException e) {
			System.out.println("File Courses.csv was not found");
			System.out.println("or could not be opened.");
			System.out.println(e.toString());
			System.exit(0);
		}
		String line;
		while (inputStream.hasNext()) {
			line = inputStream.nextLine();
			String[] info = line.split(",");
			Course c = new Course(info[0].trim(), info[1].trim());
			if (info.length > 2) {
				String[] preList = info[2].split(";");
				for (String p : preList)
					c.addPrerequisite(p.trim());
			}
			courses.put(c.getCode(), c);
		}
		inputStream.close();
	}

	/**
	 * Read a list of students from the file {@link STUDENTS_FILE} and populate
	 * {@code students} HashMap.
	 */
	public static void readStudents() {
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new FileInputStream(PATH + "/eecs2030/lab4/" + STUDENTS_FILE));
		} catch (FileNotFoundException e) {
			System.out.println("File " + STUDENTS_FILE + " was not found");
			System.out.println("or could not be opened.");
			System.out.println(e.toString());
			System.exit(0);
		}
		String line;
		while (inputStream.hasNext()) {
			line = inputStream.nextLine();
			String[] s = line.split(",");
			String id = s[0].trim();
			String name = s[1].trim();
			int y = Integer.parseInt(s[2].trim());
			int m = Integer.parseInt(s[3].trim());
			int d = Integer.parseInt(s[4].trim());
			String email = s[5].trim();
			LocalDate joinDate = LocalDate.of(y, m, d);
			Student stu = new Student(id, name, joinDate, email);
			if (s.length > 6) {
				String[] cc = s[6].split(";");
				for (String w : cc) {
					stu.completeCourse(w.trim());
				}
			}
			students.put(stu.getId(), stu);
		}
		inputStream.close();
	}
}
