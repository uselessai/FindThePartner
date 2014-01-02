package es.myapps.findthepartner;

public class Casilla {

	private int resId, identificador;
	private boolean esVisible, estaDescubierto;
	private boolean esThePartner = false;

	public Casilla(int resID, int identificador, boolean esVisible) {
		this.resId = resID;
		this.identificador = identificador;
		this.esVisible = esVisible;
		this.estaDescubierto = false;
	}

	public Casilla() {
		this.resId = 0;
		this.identificador = 0;
		this.esVisible = false;
		this.estaDescubierto = false;
	}

	public boolean getEsThePartner() {
		return esThePartner;
	}

	public void setEsThePartner(boolean thePartner) {
		esThePartner = thePartner;
	}

	public int getResId() {
		return resId;

	}

	public boolean getEsVisible() {
		return esVisible;

	}

	public boolean getEstaDescubierto() {
		return estaDescubierto;

	}

	public int getIdentificador() {
		return identificador;
	}

	public void putResId(int resId) {
		this.resId = resId;

	}

	public void putIdentificador(int identificador) {
		this.identificador = identificador;

	}

	public void putEsVisible(boolean esVisible) {
		this.esVisible = esVisible;

	}

	public void putEstaDescubierto(boolean estaDescubierto) {
		this.estaDescubierto = estaDescubierto;
	}
}
