package mobile.promat;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String TAG_FRAGMENT = "TAG_FRAGMENT";
	SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.container);

		if (fragment == null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		
		database = openOrCreateDatabase("HistorialNotas", Context.MODE_PRIVATE, null);
		//database.execSQL("DROP TABLE IF EXISTS materias");
		//database.execSQL("DROP TABLE IF EXISTS notas");
		database.execSQL("CREATE TABLE IF NOT EXISTS materias(id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT NOT NULL,creditos INTEGER NOT NULL,nota REAL);");
		database.execSQL("CREATE TABLE IF NOT EXISTS notas(id INTEGER PRIMARY KEY AUTOINCREMENT,valor REAL,porcentaje REAL,idMateria INTEGER NOT NULL,FOREIGN KEY(idMateria) REFERENCES materias(id));");
		database.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void verVistaAsignaturas(View v){
		// Begin the transaction
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		ft.replace(R.id.container, new FragmentAsignaturas()).addToBackStack(TAG_FRAGMENT);
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
	}
	
	public void verVistaPromedio(View v){
		// Begin the transaction
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		ft.replace(R.id.container, new FragmentPromedio()).addToBackStack(TAG_FRAGMENT);
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
	}
	
	public void ingresarAsignatura(View v){
		
		//AGREGAR A LA BASE DE DATOS
		EditText editAsignatura1 = (EditText) findViewById(R.id.editAsignatura1);
		EditText editAsignatura2 = (EditText) findViewById(R.id.editAsignatura2);
		EditText editAsignatura3 = (EditText) findViewById(R.id.editAsignatura3);
		EditText editCreditos1 = (EditText) findViewById(R.id.editCreditos1);
		EditText editCreditos2 = (EditText) findViewById(R.id.editCreditos2);
		EditText editCreditos3 = (EditText) findViewById(R.id.editCreditos3);
		boolean nuevaMateria = false;
		boolean campoUsado1 = false;
		boolean campoUsado2 = false;
		boolean campoUsado3 = false;

		database = openOrCreateDatabase("HistorialNotas", Context.MODE_PRIVATE, null);
		if (editAsignatura1.getText().toString().trim().length() != 0 &&
				editCreditos1.getText().toString().trim().length() != 0){			
			database.execSQL("INSERT INTO materias (nombre,creditos,nota) VALUES('"
					+ editAsignatura1.getText() + "',"
					+ editCreditos1.getText() + ",0);");
			nuevaMateria = true;
			campoUsado1 = true;
		}
		
		if (editAsignatura2.getText().toString().trim().length() != 0 &&
				editCreditos2.getText().toString().trim().length() != 0){
			database.execSQL("INSERT INTO materias (nombre,creditos,nota) VALUES('"
					+ editAsignatura2.getText() + "',"
					+ editCreditos2.getText() + ",0);");
			nuevaMateria = true;
			campoUsado2 = true;
		}
		
		database.close();
		
		if (editAsignatura3.getText().toString().trim().length() != 0 &&
				editCreditos3.getText().toString().trim().length() != 0){
			database.execSQL("INSERT INTO materias (nombre,creditos,nota) VALUES('"
					+ editAsignatura3.getText() + "',"
					+ editCreditos3.getText() + ",0);");
			nuevaMateria = true;
			campoUsado3 = true;
		}
		
		if (nuevaMateria == false){
			Toast.makeText(this, "No se agregaron nuevas materias a la base de datos.",
					Toast.LENGTH_SHORT).show();
		}else{			
			if (campoUsado1 == true){
				Toast.makeText(this, 
						editAsignatura1.getText()+" , con "+editCreditos1.getText()+" creditos, fue agregado" +
								"satisfactoriamente.",
						Toast.LENGTH_SHORT).show();
			}
			if (campoUsado2 == true){
				Toast.makeText(this, 
						editAsignatura2.getText()+" , con "+editCreditos2.getText()+" creditos, fue agregado" +
								"satisfactoriamente.",
						Toast.LENGTH_SHORT).show();
			}
			if (campoUsado3 == true){
				Toast.makeText(this, 
						editAsignatura3.getText()+" , con "+editCreditos3.getText()+" creditos, fue agregado" +
								"satisfactoriamente.",
						Toast.LENGTH_SHORT).show();
			}
			
		}
		
		editAsignatura1.setText("");
		editAsignatura2.setText("");
		editAsignatura3.setText("");
		editCreditos1.setText("");
		editCreditos2.setText("");
		editCreditos3.setText("");
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.container, new FragmentNotas()).addToBackStack(TAG_FRAGMENT);
		ft.commit();
		
	}

	public void ingresarNotas(View v){
		Spinner spinnerAsignaturas = (Spinner) findViewById(R.id.spinnerAsignaturas);
		EditText editNotas1 = (EditText) findViewById(R.id.editNotas1);
		EditText editNotas2 = (EditText) findViewById(R.id.editNotas2);
		EditText editNotas3 = (EditText) findViewById(R.id.editNotas3);
		EditText editNotas4 = (EditText) findViewById(R.id.editNotas4);
		EditText editPorcentaje1 = (EditText) findViewById(R.id.editPorcentaje1);
		EditText editPorcentaje2 = (EditText) findViewById(R.id.editPorcentaje2);
		EditText editPorcentaje3 = (EditText) findViewById(R.id.editPorcentaje3);
		EditText editPorcentaje4 = (EditText) findViewById(R.id.editPorcentaje4);
		boolean nuevaNota = false;
		boolean caso1 = false;
		boolean caso2 = false;
		boolean caso3 = false;
		boolean caso4 = false;
		
		database = openOrCreateDatabase("HistorialNotas", Context.MODE_PRIVATE, null);
		String nombreAsignatura = spinnerAsignaturas.getSelectedItem().toString();
		Cursor c = database.rawQuery(
				"SELECT id FROM materias " +
				"WHERE nombre ='"+ nombreAsignatura + "'", null);
		
		if (c.moveToFirst()){
			String id = c.getString(0);
			database.execSQL(
					"DELETE FROM notas " +
					"WHERE id="+id+";"
					);
			
			if (editPorcentaje1.getText().toString().trim().length() != 0){
				nuevaNota=true;
				if (editNotas1.getText().toString().trim().length() != 0){
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES(" +
							+ Double.parseDouble(editNotas1.getText().toString()) + "," 
							+ Double.parseDouble(editPorcentaje1.getText().toString()) + ","
							+"'"+ id +"');");
				}else{
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES("
							+ "null" + "," 
							+ Double.parseDouble(editPorcentaje1.getText().toString()) + ","
							+"'"+ id +"');");
				}			

			}
			
			if (editPorcentaje2.getText().toString().trim().length() != 0){
				nuevaNota=true;
				if (editNotas2.getText().toString().trim().length() != 0){
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES(" +
							+ Double.parseDouble(editNotas2.getText().toString()) + "," 
							+ Double.parseDouble(editPorcentaje2.getText().toString()) + ","
							+"'"+ id +"');");
				}else{
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES("
							+ "null" + "," 
							+ Double.parseDouble(editPorcentaje2.getText().toString()) + ","
							+"'"+ id +"');");
				}			

			}
			
			if (editPorcentaje3.getText().toString().trim().length() != 0){
				nuevaNota=true;
				if (editNotas3.getText().toString().trim().length() != 0){
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES(" +
							+ Double.parseDouble(editNotas3.getText().toString()) + "," 
							+ Double.parseDouble(editPorcentaje3.getText().toString()) + ","
							+"'"+ id +"');");
				}else{
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES("
							+ "null" + "," 
							+ Double.parseDouble(editPorcentaje3.getText().toString()) + ","
							+"'"+ id +"');");
				}			

			}
			
			if (editPorcentaje4.getText().toString().trim().length() != 0){
				nuevaNota=true;
				if (editNotas4.getText().toString().trim().length() != 0){
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES(" +
							+ Double.parseDouble(editNotas4.getText().toString()) + "," 
							+ Double.parseDouble(editPorcentaje4.getText().toString()) + ","
							+"'"+ id +"');");
				}else{
					database.execSQL("INSERT INTO notas (valor,porcentaje,idMateria) VALUES("
							+ "null" + "," 
							+ Double.parseDouble(editPorcentaje4.getText().toString()) + ","
							+"'"+ id +"');");
				}			

			}
		}
		
		if (nuevaNota==false){
			Toast.makeText(this, "No se actualizaron notas.",
					Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "Se han actualizado notas satisfactoriamente.",
					Toast.LENGTH_SHORT).show();
		}
		
		database.close();
	}
	
	public void calcularNotas(View v){
		
		Spinner spinnerAsignaturas = (Spinner) findViewById(R.id.spinnerAsignaturas);
		EditText editNotas1 = (EditText) findViewById(R.id.editNotas1);
		EditText editNotas2 = (EditText) findViewById(R.id.editNotas2);
		EditText editNotas3 = (EditText) findViewById(R.id.editNotas3);
		EditText editNotas4 = (EditText) findViewById(R.id.editNotas4);
		EditText editPorcentaje1 = (EditText) findViewById(R.id.editPorcentaje1);
		EditText editPorcentaje2 = (EditText) findViewById(R.id.editPorcentaje2);
		EditText editPorcentaje3 = (EditText) findViewById(R.id.editPorcentaje3);
		EditText editPorcentaje4 = (EditText) findViewById(R.id.editPorcentaje4);
		
		database = openOrCreateDatabase("HistorialNotas", Context.MODE_PRIVATE, null);
		String nombreAsignatura = spinnerAsignaturas.getSelectedItem().toString();
		
		Double notaDeseada = 3.0;
		Double notaActual = 0.0;
		Double notaFaltante;
		Double notaLibre = 0.0;
		Double sobrante;
		
		ArrayList<Datos> datos = new ArrayList<>();
		
		Cursor c = database.rawQuery(
				"SELECT notas.valor,notas.porcentaje,(notas.valor*notas.porcentaje)/100,(5*notas.porcentaje)/100 " +
				"FROM notas,materias " +
				"WHERE notas.idMateria=materias.id " +
				"AND materias.nombre ='"+ nombreAsignatura + "'", null);
		
		if (c.moveToFirst()){
			do{
											
				if (c.isNull(0)){
					datos.add(new Datos(
							0.0,
							Double.parseDouble(c.getString(1)),
							0.0,
							Double.parseDouble(c.getString(3)),
							false,
							true));
					datos.get(c.getPosition()).setValorNull(true);
					notaLibre = notaLibre + datos.get(c.getPosition()).getValorMax();
				}else{
					datos.add(new Datos(
							Double.parseDouble(c.getString(0)),
							Double.parseDouble(c.getString(1)),
							Double.parseDouble(c.getString(2)),
							Double.parseDouble(c.getString(3)),
							true,
							false));
				}				
				notaActual = notaActual + datos.get(c.getPosition()).getValorReal();
			
			}while(c.moveToNext());
			
		}		
		
		database.close();
		
		notaFaltante = notaDeseada - notaActual;
		if (notaLibre<=0.0){
			Toast.makeText(this, "Ya has alcanzado el 3.0.",
					Toast.LENGTH_SHORT).show();
		}else{
			sobrante = (notaActual-notaLibre)+notaDeseada;
			if (sobrante>=0){
				//Se puede alcanzar el 3
				while (notaFaltante>0){
					
					for (int i=0;i<datos.size();i++){
						if (datos.get(i).isValorNull()){
							if (notaFaltante-datos.get(i).getValorMax()<=0){
								//Si el estudiante sacara 5.0 en esta nota.
								//No dependería de otra nota para pasar.
								//Hay que adicionar lo que falte en la nota correspondiente.
								//Parar el proceso con notaFaltante=0
								datos.get(i).setValorNuevo((notaFaltante/(datos.get(i).getPorcentaje()/100)));
								notaFaltante = 0.0;
							}else{
								//No es suficiente.
								//El estudiante requiere de otra nota para pasar.
								//Repetir el proceso
								datos.get(i).setValorNuevo(5.0);
								notaFaltante = notaFaltante - datos.get(i).getValorMax();
							}
						}
					}
				}
				
				//MOSTRAR NOTAS FALTANTES:
				for (int i=0;i<datos.size();i++){
					if (datos.get(i).isValorNull()==true){
						switch(i){
						case 0:
							editNotas1.setText(""+datos.get(i).getValorNuevo());
							break;
						case 1:
							editNotas2.setText(""+datos.get(i).getValorNuevo());
							break;
						case 2:
							editNotas3.setText(""+datos.get(i).getValorNuevo());
							break;
						case 3:
							editNotas4.setText(""+datos.get(i).getValorNuevo());
							break;
						default: break;
						}
					}
					
				}
				
			}else{
				//Es imposible alcanzar el 3
				Toast.makeText(this, "Ya has alcanzado el 3.0.",
						Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
