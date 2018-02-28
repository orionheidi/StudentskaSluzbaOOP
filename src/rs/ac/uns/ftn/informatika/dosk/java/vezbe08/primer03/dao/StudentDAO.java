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

//CRUD operacije nad studentom
public class StudentDAO {
	
	public static Student getStudentById(Connection conn, int id) {
		Student student = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("SELECT indeks, ime, prezime, grad " +
							"FROM studenti WHERE student_id = " 
							+ id);

			if (rset.next()) {
				String indeks = rset.getString(1);
				String ime = rset.getString(2);
				String prezime = rset.getString(3);
				String grad = rset.getString(4);
				
				student = new Student(id, indeks, ime, prezime, grad);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public static Student getStudentByIndeks(Connection conn, String indeks) {
		Student student = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("SELECT student_id, ime, prezime, grad " +
							"FROM studenti WHERE indeks = '" 
							+ indeks + "'");

			if (rset.next()) {
				int id = rset.getInt(1);
				String ime = rset.getString(2);
				String prezime = rset.getString(3);
				String grad = rset.getString(4);
				
				student = new Student(id, indeks, ime, prezime, grad);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public static List<Student> getAll(Connection conn) {
		List<Student> retVal = new ArrayList<Student>();
		try {
			String query = "SELECT student_id, indeks, ime, prezime, grad FROM studenti ";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query.toString());
			while (rset.next()) {
				int id = rset.getInt(1);
				String indeks = rset.getString(2);
				String ime = rset.getString(3);
				String prezime = rset.getString(4);
				String grad = rset.getString(5);
				
				//preuzimanje predmeta koje student pohadja
				String upitZaPredmete = "select predmet_id from " +
						"pohadja where student_id = " + id;
				Statement stmtZaPredmete = conn.createStatement();
				ResultSet rsetPredmeti = stmtZaPredmete.executeQuery(
						upitZaPredmete);
				List<Predmet> predmetiKojePohadja = new ArrayList<Predmet>();
				while (rsetPredmeti.next()) {
					int idPredmeta = rsetPredmeti.getInt(1);
					Predmet p = PredmetDAO.getPredmetById(conn, idPredmeta);
					predmetiKojePohadja.add(p);
				}
				stmtZaPredmete.close();
				rsetPredmeti.close();
				
				Student student = new Student(id, indeks, ime, prezime, grad);
				student.setPredmeti(predmetiKojePohadja);
				retVal.add(student);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public static boolean add(Connection conn, Student student){
		boolean retVal = false;
		try {
			String update = "INSERT INTO studenti (indeks, ime, " +
					"prezime, grad) values (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setString(1, student.getIndeks());
			pstmt.setString(2, student.getIme());
			pstmt.setString(3, student.getPrezime());
			pstmt.setString(4, student.getGrad());
			if(pstmt.executeUpdate() == 1){
				retVal = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static boolean update(Connection conn, Student student) {
		boolean retVal = false;
		try {
			String update = "UPDATE studenti SET indeks=?, " +
					"ime=?, prezime=?, grad=? WHERE student_id=?";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setString(1, student.getIndeks());
			pstmt.setString(2, student.getIme());
			pstmt.setString(3, student.getPrezime());
			pstmt.setString(4, student.getGrad());
			pstmt.setInt(5, student.getId());
			if(pstmt.executeUpdate() == 1)
				retVal = true;
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static boolean delete(Connection conn, int id) {
		boolean retVal = false;
		try {
			String update = "DELETE FROM studenti WHERE " +
					"student_id = " + id;
			Statement stmt = conn.createStatement();
			if (stmt.executeUpdate(update) == 1)
				retVal = true;
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
