package rs.ac.uns.ftn.informatika.dosk.java.vezbe08.primer03.model;

import java.sql.Date;

public class IspitniRok {

	private int id;
	private String naziv;
	private Date pocetak;
	private Date kraj;

	public IspitniRok() {
	}
	
	public IspitniRok(int id, String naziv, Date pocetak, Date kraj) {
		this.id = id;
		this.naziv = naziv;
		this.pocetak = pocetak;
		this.kraj = kraj;
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

	public Date getPocetak() {
		return pocetak;
	}

	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}

	public Date getKraj() {
		return kraj;
	}

	public void setKraj(Date kraj) {
		this.kraj = kraj;
	}

	@Override
	public String toString() {
		return "Rok [id=" + id + ", naziv=" + naziv + ", pocetak=" + pocetak
				+ ", kraj=" + kraj + "]";
	}

}
