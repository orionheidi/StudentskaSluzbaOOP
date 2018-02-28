package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.ui;

import java.util.List;

import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.dao.StudentDAO;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model.Student;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.utils.ScannerWrapper;

public class StudentUI {

	public static void menu() {
		int odluka = -1;
		while (odluka != 0) {
			ispisiMenu();
			System.out.print("opcija:");
			odluka = ScannerWrapper.ocitajCeoBroj();
			switch (odluka) {
			case 0:
				System.out.println("Izlaz");
				break;
			case 1:
				ispisiSveStudente();
				break;
			case 2:
				unosNovogStudenta();
				break;
			case 3:
				izmenaPodatakaOStudentu();
				break;
			case 4:
				brisanjePodatakaOStudentu();
				break;
			default:
				System.out.println("Nepostojeca komanda");
				break;
			}
		}
	}

	public static void ispisiMenu() {
		System.out.println("Rad sa studentima - opcije:");
		System.out.println("\tOpcija broj 1 - ispis svih Studenata");
		System.out.println("\tOpcija broj 2 - unos novog Studenta");
		System.out.println("\tOpcija broj 3 - izmena Studenta");
		System.out.println("\tOpcija broj 4 - brisanje Studenta");
		System.out.println("\t\t ...");
		System.out.println("\tOpcija broj 0 - IZLAZ");
	}

	/** METODE ZA ISPIS STUDENATA ****/
	// ispisi sve studente
	public static void ispisiSveStudente() {
		List<Student> sviStudenti = StudentDAO.getAll(ApplicationUI.conn);
		for (int i = 0; i < sviStudenti.size(); i++) {
			System.out.println(sviStudenti.get(i));
		}
	}

	/** METODE ZA PRETRAGU STUDENATA ****/
	// pronadji studenta
	public static Student pronadjiStudenta() {
		Student retVal = null;
		System.out.print("Unesi indeks studenta:");
		String stIndex = ScannerWrapper.ocitajTekst();
		retVal = pronadjiStudenta(stIndex);
		if (retVal == null)
			System.out.println("Student sa indeksom " + stIndex
					+ " ne postoji u evidenciji");
		return retVal;
	}

	// pronadji studenta
	public static Student pronadjiStudenta(String stIndex) {
		Student retVal = StudentDAO.getStudentByIndeks(ApplicationUI.conn,
				stIndex);
		return retVal;
	}

	/** METODE ZA UNOS, IZMENU I BRISANJE STUDENATA ****/
	// unos novog studenta
	public static void unosNovogStudenta() {
		System.out.print("Unesi indeks:");
		String stIndex = ScannerWrapper.ocitajTekst();
		stIndex = stIndex.toUpperCase();
		while (pronadjiStudenta(stIndex) != null) {
			System.out.println("Student sa indeksom " + stIndex
					+ " vec postoji");
			stIndex = ScannerWrapper.ocitajTekst();
		}
		System.out.print("Unesi ime:");
		String stIme = ScannerWrapper.ocitajTekst();
		System.out.print("Unesi prezime:");
		String stPrezime = ScannerWrapper.ocitajTekst();
		System.out.print("Unesi grad:");
		String stGrad = ScannerWrapper.ocitajTekst();

		Student st = new Student(0, stIndex, stIme, stPrezime, stGrad);
		// ovde se moze proveravati i povratna vrednost i onda ispisivati poruka
		StudentDAO.add(ApplicationUI.conn, st);
	}

	// izmena studenta
	public static void izmenaPodatakaOStudentu() {
		Student st = pronadjiStudenta();
		if (st != null) {
			System.out.print("Unesi novi indeks :");
			String stIndeks = ScannerWrapper.ocitajTekst();
			st.setIndeks(stIndeks);

			System.out.print("Unesi ime :");
			String stIme = ScannerWrapper.ocitajTekst();
			st.setIme(stIme);

			System.out.print("Unesi prezime:");
			String stPrezime = ScannerWrapper.ocitajTekst();
			st.setPrezime(stPrezime);
	
			System.out.print("Unesi grad:");
			String stGrad = ScannerWrapper.ocitajTekst();
			st.setGrad(stGrad);

			StudentDAO.update(ApplicationUI.conn, st);
		}
	}

	// brisanje studenta
	public static void brisanjePodatakaOStudentu() {
		Student st = pronadjiStudenta();
		if (st != null) {
			StudentDAO.delete(ApplicationUI.conn, st.getId());
		}
	}

}
