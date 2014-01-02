package es.myapps.findthepartner;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Matrix matrix;
	private Chronometer chronometer;
	private int filas, columnas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// you can change it, to make it bigger.
		filas = 3;
		columnas = 2;
		setContentView(R.layout.activity_main);
		
		comenzarTodo();
	}

	private void inicializarBoton() {
		chronometer = (Chronometer) findViewById(R.id.chronometer);

		botonExit = (ImageButton) findViewById(R.id.buttonExit);

		botonExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				// TODO Auto-generated method stub
				// MainActivity.finalize();
			}
		});

	}

	private void comenzarTodo() {
		inicializarBoton();
		matrix = new Matrix(filas, columnas);
		matrix.setAleatoria();
		rellenarCasillas();
		
		// ****************
		ocultarCasillas();
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
		
		/*
		desactivarBotones(false);
		// show all at first
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				desactivarBotones(true);
				ocultarCasillas();
				chronometer.setBase(SystemClock.elapsedRealtime());
				chronometer.start();
			}
		}, 3000);*/

		configurarBotones();
	}

	private ImageView imageViewBotones;
	private ImageButton botonExit;
	private int resDrawable;
	private int i, j;
	private int pulsaciones = 0;

	private void configurarBotones() {

		int resID;
		Resources res = getApplicationContext().getResources();
		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++) {

				resID = res.getIdentifier("imageView" + i + j, "id",
						getApplicationContext().getPackageName());

				resDrawable = res.getIdentifier("img" + matrix.getResId(i, j),
						"drawable", getApplicationContext().getPackageName());

				imageViewBotones = (ImageView) findViewById(resID);

				imageViewBotones.setClickable(true);

				imageViewBotones.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String tag = (String) v.getTag();
						String x[] = tag.split(",");

						int valorI, valorJ;
						valorI = Integer.parseInt(x[0]);
						valorJ = Integer.parseInt(x[1]);
						Resources res = getApplicationContext().getResources();
						int resID = res.getIdentifier("imageView" + valorI
								+ valorJ, "id", getApplicationContext()
								.getPackageName());

						if(matrix.getEsPartner(valorI, valorJ)){
							resDrawable = res.getIdentifier(
									"img" + matrix.getResId(valorI, valorJ) + "p",
									"drawable", getApplicationContext()
											.getPackageName());	
							
						}else
							resDrawable = res.getIdentifier(
									"img" + matrix.getResId(valorI, valorJ),
									"drawable", getApplicationContext()
											.getPackageName());
						
						

						imageViewBotones = (ImageView) findViewById(resID);
						imageViewBotones.setImageResource(resDrawable);

						if (!matrix.getVisible(valorI, valorJ))
							if (pulsaciones == 1) {
								fil2 = valorI;
								col2 = valorJ;
								pulsaciones = 0;
								matrix.setVisible(valorI, valorJ);
								determinarSiSonIguales(fil1, col1, fil2, col2);
							} else {
								fil1 = valorI;
								col1 = valorJ;
								pulsaciones++;
								matrix.setVisible(valorI, valorJ);
							}
					}
				});

			}

	}

	private void ocultarImagen(int fil1, int col1) {

		Resources res = getApplicationContext().getResources();
		int resID = res.getIdentifier("imageView" + fil1 + col1, "id",
				getApplicationContext().getPackageName());
		imageViewBotones = (ImageView) findViewById(resID);
		imageViewBotones.setImageResource(R.drawable.pinza);

	}

	int fil1;
	int col1;
	int fil2;
	int col2;
	int igualFil1, igualFil2, igualCol1, igualCol2;
	TextView texto;

	private void determinarSiSonIguales(int fil1, int col1, int fil2, int col2) {
		if (!matrix.isEqual(fil1, col1, fil2, col2)) {
			matrix.setInvisible(fil1, col1);
			matrix.setInvisible(fil2, col2);
			igualFil1 = fil1;
			igualFil2 = fil2;
			igualCol1 = col1;
			igualCol2 = col2;

			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					ocultarImagen(igualFil1, igualCol1);
					ocultarImagen(igualFil2, igualCol2);
					desactivarBotones(true);
				}
			}, 1000);

			desactivarBotones(false);

		} else {
			matrix.setVisible(fil1, col1);
			matrix.setVisible(fil2, col2);

			if (comprobarSiHasGanado()) {
				texto = (TextView) findViewById(R.id.textView1);
				texto.setText("YOU WON");
				chronometer.stop();
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						texto.setText("");
						comenzarTodo();

					}
				}, 2000);

			}

		}
	}

	private void desactivarBotones(boolean activar) {
		int resID;
		Resources res = getApplicationContext().getResources();
		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++) {

				resID = res.getIdentifier("imageView" + i + j, "id",
						getApplicationContext().getPackageName());

				imageViewBotones = (ImageView) findViewById(resID);

				imageViewBotones.setClickable(activar);
				imageViewBotones.setEnabled(activar);
			}

	}

	private boolean comprobarSiHasGanado() {
		return matrix.isWinner();
	}

	private void rellenarCasillas() {

		ImageView imageView;
		int resID, resDrawable;
		Resources res = getApplicationContext().getResources();
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++) {

				resID = res.getIdentifier("imageView" + i + j, "id",
						getApplicationContext().getPackageName());

				if (matrix.getEsPartner(i, j)) {
					resDrawable = res.getIdentifier(
							"img" + matrix.getResId(i, j), "drawable",
							getApplicationContext().getPackageName());

				} else {

					resDrawable = res.getIdentifier(
							"img" + matrix.getResId(i, j) + "p", "drawable",
							getApplicationContext().getPackageName());
				}

				imageView = (ImageView) findViewById(resID);
				imageView.setImageResource(resDrawable);

			}

	}

	private void ocultarCasillas() {

		ImageView imageView;
		int resID;
		Resources res = getApplicationContext().getResources();
		for (int i = 0; i < filas; i++)
			for (int j = 0; j < columnas; j++) {

				resID = res.getIdentifier("imageView" + i + j, "id",
						getApplicationContext().getPackageName());

				imageView = (ImageView) findViewById(resID);
				imageView.setImageResource(R.drawable.pinza);

			}

	}

}
