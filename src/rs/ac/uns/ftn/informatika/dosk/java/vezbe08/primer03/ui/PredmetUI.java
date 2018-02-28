package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.ui;

import java.util.List;

import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.dao.PredmetDAO;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model.Predmet;
import rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.utils.ScannerWrapper;

public class PredmetUI {

	public static void menu(){
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
				ispisiSvePredmete();
				break;
			case 2:
				unosNovogPredmeta();
				break;
			case 3:
				izmenaPodatakaOPredmetu();
				break;
			case 4:
				brisanjePodatakaOPredmetu();
				break;
			default:
				System.out.println("Nepostojeca komanda");
				break;
			}
		}
	}
	
	public static void ispisiMenu() {
		System.out.println("Rad sa predmetima - opcije:");
		System.out.println("\tOpcija broj 1 - ispis svih Predmeta");
		System.out.println("\tOpcija broj 2 - unos novog Predmeta");
		System.out.println("\tOpcija broj 3 - izmena naziva Predmeta");
		System.out.println("\tOpcija broj 4 - brisanje Predmeta");
		System.out.println("\t\t ...");
		System.out.println("\tOpcija broj 0 - IZLAZ");	
	}
	
	/** METODE ZA ISPIS PREDMETA ****/
	// ispisi sve predmete
	public static void ispisiSvePredmete() {
		List<Predmet> sviPredmeti = PredmetDAO.getAll(ApplicationUI.conn);
		for (int i = 0; i < sviPredmeti.size(); i++) {
			System.out.println(sviPredmeti.get(i));
		}
	}
	
	/** METODE ZA PRETRAGU PREDMETA ****/
	// pronadji predmet
	public static Predmet pronadjiPredmet() {
		Predmet retVal = null;
		System.out.print("Unesi id predmeta:");
		int id = ScannerWrapper.ocitajCeoBroj();
		retVal = pronadjiPredmet(id);
		if (retVal == null)
			System.out.println("Predmet sa id-om " + id
					+ " ne postoji u evidenciji");
		return retVal;
	}

	// pronadji predmet
	public static Predmet pronadjiPredmet(int id) {
		Predmet retVal = PredmetDAO.getPredmetById(ApplicationUI.conn, id);
		return retVal;
	}
	
	/** METODE ZA UNOS, IZMENU I BRISANJE PREDMETA ****/
	// unos novog predmeta
	public static void unosNovogPredmeta() {
		System.out.print("Naziv:");
		String prNaziv= ScannerWrapper.ocitajTekst();
		
		Predmet pred = new Predmet(0, prNaziv);
		//ovde se moze proveravati i povratna vrednost i onda ispisivati poruka
		PredmetDAO.add(ApplicationUI.conn, pred);
	}
	
	// izmena predmeta
	public static void izmenaPodatakaOPredmetu() {
		Predmet pred = pronadjiPredmet();
		if(pred != null){
			System.out.print("Unesi novi naziv :");
			String prNaziv = ScannerWrapper.ocitajTekst();
			pred.setNaziv(prNaziv);
			PredmetDAO.update(ApplicationUI.conn, pred);
		}
	}

	//brisanje predmeta
	public static void brisanjePodatakaOPredmetu(){
		Predmet pred  = pronadjiPredmet();
		if(pred != null){
			PredmetDAO.delete(ApplicationUI.conn, pred.getId());
		}
	}	
}
