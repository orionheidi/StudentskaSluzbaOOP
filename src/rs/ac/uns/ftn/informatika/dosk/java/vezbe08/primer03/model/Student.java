package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

	protected int id;
	protected String ime;
	protected String prezime;
	protected String grad;
	protected String indeks;
	protected List<IspitnaPrijava> prijave = new ArrayList<IspitnaPrijava>();
	protected List<Predmet> predmeti = new ArrayList<Predmet>();

	public Student(int id, String indeks, String ime, String prezime,
			String grad) {
		this.id = id;
		this.indeks = indeks;
		this.ime = ime;
		this.prezime = prezime;
		this.grad = grad;
	}

	// prebacivanje objekta Student u string reprezentaciju
	@Override
	public String toString() {
		String s = "Student [" + indeks + " " + ime + " " + prezime + " "
				+ grad + "]";
		for (Predmet p : predmeti)
			s += p.getNaziv() + ",";

		return s;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getIndeks() {
		return indeks;
	}

	public void setIndeks(String index) {
		this.indeks = index;
	}

	public List<IspitnaPrijava> getPrijave() {
		return prijave;
	}

	public void setPrijave(List<IspitnaPrijava> prijave) {
		this.prijave = prijave;
	}

	public List<Predmet> getPredmeti() {
		return predmeti;
	}

	public void setPredmeti(List<Predmet> predmeti) {
		this.predmeti = predmeti;
	}
}
