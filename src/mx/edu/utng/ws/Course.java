package mx.edu.utng.ws;

public class Course {
	private int id;
	private int clave;
	private String title;
	private String credits;
	private String department;
	private String enrollments ;
	private String instructors;
	
	
	public Course(int id, int clave, String title, String credits, String department, String enrollments,
			String instructors) {
		super();
		this.id = id;
		this.clave= clave;
		this.title = title;
		this.credits = credits;
		this.department = department;
		this.enrollments = enrollments;
		this.instructors = instructors;
	}
	
	public Course(){
		this(0,0,"","","","","");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(String enrollments) {
		this.enrollments = enrollments;
	}

	public String getInstructors() {
		return instructors;
	}

	public void setInstructors(String instructors) {
		this.instructors = instructors;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", clave=" + clave + ", title=" + title + ", credits=" + credits + ", department="
				+ department + ", enrollments=" + enrollments + ", instructors=" + instructors + "]";
	}

	
	
}
