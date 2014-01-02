package es.myapps.findthepartner;

import java.util.Random;

public class Matrix {
	private final int filas; // number of rows
	private final int columnas; // number of columns
	private final Casilla[][] data; // M-by-N array
	private final int[] dataNum;
	private Random r = new Random();

	// create M-by-N matrix of 0's
	public Matrix(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		data = new Casilla[filas][columnas];
		dataNum = new int[filas * columnas];

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++)
				data[i][j] = new Casilla();
		}

		for (int j = 0; j < filas * columnas; j++)
			dataNum[j] = -1;

	}

	public Matrix() {

		this.filas = 0;
		this.columnas = 0;
		this.data = new Casilla[1][1];
		this.dataNum = new int[1];
	}

	private int getAleatorio() {
		int min = 0;
		int max = ((filas * columnas) / 2) - 1;

		int i1 = r.nextInt(max - min + 1) + min;
		int num_total = 0;
		for (int i = 0; i < dataNum.length; i++) {
			if (dataNum[i] == i1) {
				num_total++;
			}
			if (dataNum[i] == -1) {
				if (num_total < 2) {
					dataNum[i] = i1;
				}

				break;
			}

		}

		if (num_total >= 2) {
			i1 = getAleatorio();
		}

		return i1;
	}

	public void setAleatoria() {
		setRandom();
		setPartner();
	}

	public boolean getEsPartner(int i, int j) {
		return data[i][j].getEsThePartner();
	}

	private void setPartner() {

		for (int k = 0; k < filas; k++)

			if (filas % 2 == 0) {
				mainloop:
				for (int i = 0; i < filas; i++)
					for (int j = 0; j < columnas; j++) {
						if (getResId(i, j) == k) {
							data[i][j].setEsThePartner(true);
							break mainloop;

						}

					}
			} else {
				mainloop2:
				for (int i = filas - 1; i >= 0; i--)
					for (int j = columnas - 1; j >= 0; j--) {
						if (getResId(i, j) == k) {
							data[i][j].setEsThePartner(true);
							break mainloop2;
						}

					}
			}

	}

	private void setRandom() {
		int valor;
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++) {
				valor = getAleatorio();
				data[i][j].putResId(valor);
			}

	}

	public void setVisible(int fil, int col) {
		data[fil][col].putEsVisible(true);
	}

	public void setInvisible(int fil, int col) {
		data[fil][col].putEsVisible(false);
	}

	public boolean getVisible(int fil, int col) {
		return data[fil][col].getEsVisible();
	}

	public int getResId(int fil, int col) {
		return data[fil][col].getResId();
	}

	public boolean isEqual(int fil1, int col1, int fil2, int col2) {
		return (data[fil1][col1].getResId() == data[fil2][col2].getResId());
	}

	public boolean isWinner() {

		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++) {
				if (!data[i][j].getEsVisible()) {
					return false;
				}
			}

		return true;
	}

}
