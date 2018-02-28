package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model;

public class IspitnaPrijava {
	private Predmet predmet;
	private IspitniRok rok;
	Student student;
	private int teorija;
	private int zadaci;

	public IspitnaPrijava() {
	}

	public IspitnaPrijava(Predmet predmet, Student student, IspitniRok rok,
			int teorija, int zadaci) {
		this.predmet = predmet;
		this.student = student;
		this.rok = rok;
		this.teorija = teorija;
		this.zadaci = zadaci;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}

	public IspitniRok getRok() {
		return rok;
	}

	public void setRok(IspitniRok rok) {
		this.rok = rok;
	}

	public int getTeorija() {
		return teorija;
	}

	public void setTeorija(int teorija) {
		this.teorija = teorija;
	}

	public int getZadaci() {
		return zadaci;
	}

	public void setZadaci(int zadaci) {
		this.zadaci = zadaci;
	}

	@Override
	public String toString() {
		return "IspitnaPrijava [predmet=" + predmet + ", rok=" + rok
				+ ", teorija=" + teorija + ", zadaci=" + zadaci + "]";
	}

	public int sracunajOcenu() {
		int bodovi = teorija + zadaci;
		int ocena;
		if (bodovi >= 95)
			ocena = 10;
		else if (bodovi >= 85)
			ocena = 9;
		else if (bodovi >= 75)
			ocena = 8;
		else if (bodovi >= 65)
			ocena = 7;
		else if (bodovi >= 55)
			ocena = 6;
		else
			ocena = 5;

		return ocena;
	}
}
