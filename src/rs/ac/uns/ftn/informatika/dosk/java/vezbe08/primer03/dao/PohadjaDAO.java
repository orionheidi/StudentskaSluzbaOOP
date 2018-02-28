package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model.Predmet;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model.Student;

//CRUD operacije nad veznom tabelom pohadja
public class PohadjaDAO {

	public static List<Predmet> getPredmetiByStudentId(Connection conn, int id) {
		List<Predmet> retVal = new ArrayList<Predmet>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("SELECT predmet_id FROM pohadja " +
							"WHERE student_id = " + id);

			while (rset.next()) {
				int predmetId = rset.getInt(1);
				retVal.add(PredmetDAO.getPredmetById(conn, predmetId));
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static List<Student> getStudentiByPredmetId(Connection conn, int id) {
		List<Student> retVal = new ArrayList<Student>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("SELECT student_id FROM pohadja WHERE " +
							"predmet_id = " + id);

			while (rset.next()) {
				int studentId = rset.getInt(1);
				retVal.add(StudentDAO.getStudentById(conn, studentId));
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static boolean add(Connection conn, int studentId, int predmetId){
		boolean retVal = false;
		try {
			String update = "INSERT INTO pohadja " +
					"(student_id, predmet_id) values (?,?)";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setInt(1, studentId);
			pstmt.setInt(2, predmetId);
			if(pstmt.executeUpdate() == 1)
				retVal = true;
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	
	public static boolean delete(Connection conn, int studentId, int predmetId) {
		boolean retVal = false;
		try {
			String update = "DELETE FROM pohadja WHERE student_id = " + 
					studentId + " AND predmet_id = " + predmetId;
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(update) == 1)
				retVal = true;
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static boolean deletePohadjanjaStudenta(Connection conn, int studentId) {
		boolean retVal = false;
		try {
			String update = "DELETE FROM pohadja WHERE student_id = " + studentId;
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(update) != 0)
				retVal = true;
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static boolean deletePohadjanjaPredmeta(Connection conn, int predmetId) {
		boolean retVal = false;
		try {
			String update = "DELETE FROM pohadja WHERE predmet_id = " + predmetId;
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(update) != 0)
				retVal = true;
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	//update svih pohadjanja jednog studenta
	public static boolean update(Connection conn, Student student){
		boolean retVal = false;
		try {
			//obrisemo prethodna pohadjaja
			retVal = deletePohadjanjaStudenta(conn, student.getId());
			//ako je brisanje uspelo idemo na dodavanje
			if(retVal){
				for (Predmet predmet : student.getPredmeti()) {
					retVal = add(conn, student.getId(), predmet.getId());
					if(retVal == false)
						throw new Exception("Dodavanje nije valjalo");
				}
			} else {
				throw new Exception("Brisanje nije valjalo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	//update svih pohadjanja jednog predmeta
	public static boolean update(Connection conn, Predmet predmet){
		boolean retVal = false;
		try {
			//obrisemo prethodna pohadjaja
			retVal = deletePohadjanjaPredmeta(conn, predmet.getId());
			//ako je brisanje uspelo idemo na dodavanje
			if(retVal){
				for (Student student : predmet.getStudenti()) {
					retVal = add(conn, student.getId(), predmet.getId());
					if(retVal == false)
						throw new Exception("Dodavanje nije valjalo");
				}
			} else {
				throw new Exception("Brisanje nije valjalo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
