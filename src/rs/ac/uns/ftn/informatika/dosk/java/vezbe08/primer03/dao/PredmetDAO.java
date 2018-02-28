package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model.Predmet;

//CRUD operacije nad predmetom
public class PredmetDAO {
	
	public static Predmet getPredmetById(Connection conn, int id) {
		Predmet predmet = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("SELECT naziv FROM predmeti WHERE predmet_id = " + id);

			if (rset.next()) {
				String naziv = rset.getString(1);				
				predmet = new Predmet(id, naziv);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return predmet;
	}

	public static Predmet getPredmetByNaziv(Connection conn, String naziv) {
		Predmet predmet = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("SELECT predmet_id FROM predmeti WHERE naziv = '" 
							+ naziv + "'");

			if (rset.next()) {
				int id = rset.getInt(1);				
				predmet = new Predmet(id, naziv);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return predmet;
	}
	
	public static List<Predmet> getAll(Connection conn) {
		List<Predmet> retVal = new ArrayList<Predmet>();
		try {
			String query = "SELECT predmet_id, naziv FROM predmeti ";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query.toString());
			while (rset.next()) {
				int id = rset.getInt(1);
				String naziv = rset.getString(2);
				
				Predmet predmet = new Predmet(id, naziv);
				retVal.add(predmet);
			}
			rset.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	public static boolean add(Connection conn, Predmet predmet){
		boolean retVal = false;
		try {
			String update = "INSERT INTO predmeti (naziv) values (?)";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setString(1, predmet.getNaziv());
			if(pstmt.executeUpdate() == 1){
				retVal = true;
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static boolean update(Connection conn, Predmet predmet) {
		boolean retVal = false;
		try {
			String update = "UPDATE predmeti SET naziv=? WHERE predmet_id=?";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setString(1, predmet.getNaziv());
			pstmt.setInt(2, predmet.getId());
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
			String update = "DELETE FROM predmeti WHERE predmet_id = " + id;
			Statement stmt = conn.createStatement();
			if(stmt.executeUpdate(update) == 1)
				retVal = true;
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
