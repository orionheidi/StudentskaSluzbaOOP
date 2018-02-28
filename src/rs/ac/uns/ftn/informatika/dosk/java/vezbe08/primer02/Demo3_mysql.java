package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo3_mysql {

	public static void main(String[] args) {
		Connection conn = null;
		try {
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(
		        "jdbc:mysql://localhost/jwtsX", "jwtsX", "jwtsX");
		      
		      // iskljuci automatski commit posle svake SQL naredbe
		      conn.setAutoCommit(false);		      
   
		      // izvrsicemo dve SQL naredbe u jednoj transakciji
		      String sql1 = "UPDATE nastavnici SET ime='Zika' WHERE nastavnik_id=1";
		      String sql2 = "UPDATE predmeti SET naziv='Sociologija' WHERE predmet_id=1";
		      Statement stmt = conn.createStatement();
		      stmt.executeUpdate(sql1);
		      stmt.executeUpdate(sql2);
		      stmt.close();
		      
		      // tek sada se potvrdjuje transakcija; opoziv transakcije: conn.rollback()
		      conn.commit(); 
		    }
		    catch (Exception ex) {
		        try {
		        	if(conn != null)
		        		conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    finally {
					try {
						if(conn != null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		    }
	}
}
