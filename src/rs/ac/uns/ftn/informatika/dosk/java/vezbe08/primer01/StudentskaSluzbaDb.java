package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class StudentskaSluzbaDb {

	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		try {
			// ucitavanje MySQL drajvera
			Class.forName("com.mysql.jdbc.Driver");		
			// Konekcija na bazu
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/studentskasluzba", "root", "rootroot");
			
			//prikaz menija
			String odluka = "";
			while (!odluka.equals("x")) {
				ispisiMenu();
				System.out.print("opcija:");
				odluka = scanner.nextLine();
				switch (odluka) {				
					case "1":
						//prikaziSveStudenteSaPredmetimaBezJoin(conn);
						prikaziSveStudenteSaPredmetimaPutemJoin(conn);
						break;
					case "2":
						unosStudenta(conn);
						break;
					case "x":
						System.out.println("Izlaz");
						break;
					default:
						System.out.println("Nepostojeca komanda");
						break;
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ispis teksta osnovnih opcija
	private static void ispisiMenu() {
		System.out.println("Studentska Sluzba - Meni:");
		System.out.println("\t1 - Spisak studenata");
		System.out.println("\t2 - Unos studenta");
		System.out.println("\tx - IZLAZ IZ PROGRAMA");
	}
	
	private static void prikaziSveStudenteSaPredmetimaBezJoin(Connection conn) {
		List<Student> studenti = new ArrayList<Student>(); //lista studenata u memoriji
		try {
			String query = "SELECT student_id, indeks, ime, " +
					"prezime, grad FROM studenti ";
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query); //preuzimamo studente iz baze
			while (rset.next()) { 
				int id = rset.getInt(1);
				String indeks = rset.getString(2);
				String ime = rset.getString(3);
				String prezime = rset.getString(4);
				String grad = rset.getString(5);
				
				/* PREUZIMANJE PREDMETA KOJE STUDENT POHADJA - 
				 * 	1. NACIN - BEZ JOIN
				 */
				String upitZaPredmete = "select predmet_id from " +
						"pohadja where student_id = " + id;
				Statement stmtZaPredmete = conn.createStatement();
				ResultSet rsetPredmeti = stmtZaPredmete.executeQuery(
						upitZaPredmete);
				List<Predmet> predmetiKojePohadja = new ArrayList<Predmet>();
				while (rsetPredmeti.next()) {
					int idPredmeta = rsetPredmeti.getInt(1);					
					Predmet p = pronadjiPredmetPoId(idPredmeta, conn);
					predmetiKojePohadja.add(p);
				}
				stmtZaPredmete.close();
				rsetPredmeti.close();
				
				//kreiramo objekat u memoriji na osnovu preuzetog sloga iz baze
				Student student = new Student(id, indeks, ime, prezime, grad);
				student.setPredmeti(predmetiKojePohadja);
				studenti.add(student); //dodamo objekat Student u listu svih studenata
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// sada imamo napunjenu listu studenata u memoriji 
		// Prolazimo kroz listu i ispisujemo podatke o svakom studentu
		for (Student s: studenti) {
			System.out.println(s);
		}
	}
	
	private static Predmet pronadjiPredmetPoId(int id, Connection conn) {
		Predmet predmet = null;
		try {
			String query = "SELECT naziv FROM predmeti where " +
					"predmet_id = " + id;
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query.toString()); //preuzimamo studente iz baze
			if (rset.next()) { 
				String naziv = rset.getString(1);
				predmet = new Predmet(id, naziv);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return predmet;
	}
	
	private static void prikaziSveStudenteSaPredmetimaPutemJoin(Connection conn) {
		List<Student> studenti = new ArrayList<Student>(); //lista studenata u memoriji
		try {
			String query = "SELECT s.student_id, s.indeks, s.ime, s.prezime, s.grad, " +
					"pr.predmet_id, pr.naziv FROM studenti s left join pohadja poh " +
					"on s.student_id = poh.student_id " +
					"left join predmeti pr on pr.predmet_id = poh.predmet_id";
			Statement stmt = conn.createStatement();
			//preuzimamo studente iz baze zajedno sa predmetima koje pohadja
			ResultSet rset = stmt.executeQuery(query); 
			while (rset.next()) { 
				int id = rset.getInt(1);
				String indeks = rset.getString(2);
				String ime = rset.getString(3);
				String prezime = rset.getString(4);
				String grad = rset.getString(5);				
				Student noviStudent = new Student(id, indeks, ime, prezime, grad);
				/* join ce vratiti onoliko redova koliko ima predmeta 
				 * mozda smo ovog studenta vec dodali u listu
				 * dodamo ga u listu ako nismo vec dodali. Ako jesmo, samo preuzmemo
				 * referencu na ranije dodati objekat
				 * */
				Student student = dodajIliPreuzmiStudenta(studenti, noviStudent);				
				
				int idPredmeta = rset.getInt(6);
				//ako student nema predmete koje pohadja, idPredmeta je 0, a 
				//nazivPredmeta je null
				if (idPredmeta != 0) { 
					String nazivPredmeta = rset.getString(7);
					Predmet p = new Predmet(idPredmeta, nazivPredmeta);
					student.getPredmeti().add(p);
				}
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// sada imamo napunjenu listu studenata u memoriji 
		// Prolazimo kroz listu i ispisujemo podatke o svakom studentu
		for (Student s: studenti) {
			System.out.println(s);
		}
	}
	
	private static Student dodajIliPreuzmiStudenta(List<Student> studenti, 
				Student noviStudent) {
		for (Student s: studenti) {
			if (s.getId() == noviStudent.getId()) {
				return s; //vec postoji taj student u listi. Vracamo taj objekat iz liste
			}				
		}
		studenti.add(noviStudent); //student ne postoji vec u listi. Dodajemo ga
		return noviStudent; //vracamo taj novi objekat iz liste
	}

	
	private static void unosStudenta(Connection conn) {
		System.out.print("Unesi indeks:");
		String stIndex = scanner.nextLine();
		System.out.print("Unesi ime:");
		String stIme = scanner.nextLine();
		System.out.print("Unesi prezime:");
		String stPrezime = scanner.nextLine();
		System.out.print("Unesi grad:");
		String stGrad = scanner.nextLine();
		//kreiramo objekat student u memoriji
		Student student = new Student(0, stIndex, stIme, stPrezime, stGrad);
		//sadrzaj objekta ubacimo u bazu podataka
		try {
			String update = "INSERT INTO studenti (indeks, ime, prezime, grad) " +
					"values (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.setString(1, student.getIndeks());
			pstmt.setString(2, student.getIme());
			pstmt.setString(3, student.getPrezime());
			pstmt.setString(4, student.getGrad());
			if(pstmt.executeUpdate() == 1) {
				System.out.println("Student je uspesno dodan.");
			} else {
				System.out.println("Greska pri dodavanju studenta.");
			}
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Greska pri dodavanju studenta.");
		}
	}
}
