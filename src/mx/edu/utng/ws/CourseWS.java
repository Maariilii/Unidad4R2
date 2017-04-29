package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	

public CourseWS() {
	conectar();
}
private void conectar(){
	try {
		Class.forName("org.postgresql.Driver");
		connection=DriverManager.getConnection("jdbc:postgresql://localhost:5432/wstest","postgres","arevalo");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}catch (SQLException e) {
	e.printStackTrace();
	}
}
public int addCourse(Course course){
	int result=0;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("INSERT INTO course (clave,title,credits,department,enrollments,instructors) "
					+ " VALUES (?,?,?,?,?,?);");
			ps.setInt(1, course.getClave());
			ps.setString(2, course.getTitle());
			ps.setString(3, course.getCredits());
			ps.setString(4, course.getDepartment());
			ps.setString(5, course.getEnrollments());
			ps.setString(6, course.getInstructors());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
//para hacer un update
public int updateCourse(Course course){
	int result=0;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("UPDATE course SET clave= ?,title= ?,credits= ?,department= ?,enrollments= ?,instructors= ? WHERE id= ?;");
				
			ps.setInt(1, course.getClave());
			ps.setString(2, course.getTitle());
			ps.setString(3, course.getCredits());
			ps.setString(4, course.getDepartment());
			ps.setString(5, course.getEnrollments());
			ps.setString(6, course.getInstructors());
			ps.setInt(7, course.getId());
			result=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
//para borrar
public int removeCourse(int id){
	int result=0;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("DELETE FROM course WHERE id= ?;");
			ps.setInt(1, id);
			result=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return result;
}
//lista de course
public Course[] getCourse(){
	Course[] result = null;
	List <Course> courses=new ArrayList<Course>();
	if(connection!=null){
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery("SELECT * FROM course;");
			while(resultSet.next()){
				Course course=new Course(
						resultSet.getInt("id"),
						resultSet.getInt("clave"),
						resultSet.getString("title"), 
						resultSet.getString("credits"), 
						resultSet.getString("department"),
						resultSet.getString("enrollments"),
						resultSet.getString("instructors"));
				courses.add(course);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	result = courses.toArray(new Course[courses.size()]);
	return result;
}
//
public Course getCoursebyId(int id){
	Course course=null;
	if(connection!=null){
		try {
			ps=connection.prepareStatement("SELECT * FROM course WHERE id = ?;");
			ps.setInt(1, id);
			resultSet=ps.executeQuery();
			while(resultSet.next()){
				course=new Course(
						resultSet.getInt("id"),
						resultSet.getInt("clave"),
						resultSet.getString("title"), 
						resultSet.getString("credits"), 
						resultSet.getString("department"), 
						resultSet.getString("enrollments"), 
						resultSet.getString("instructors"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return course;
}

}
