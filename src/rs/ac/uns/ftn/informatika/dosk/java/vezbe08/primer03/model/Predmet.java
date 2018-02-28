package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model;

import java.util.ArrayList;
import java.util.List;

public class Predmet {
	private int id;
	private String naziv;
	
	private List<Student> studenti;
	
	public Predmet(int id, String naziv) {
		this.id = id;
		this.naziv = naziv;
		studenti = new ArrayList<Student>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<Student> getStudenti() {
		return studenti;
	}

	public void setStudenti(List<Student> studenti) {
		this.studenti = studenti;
	}

	@Override
	public String toString() {
		return "Predmet [id=" + id + ", naziv=" + naziv + "]";
	}
}
