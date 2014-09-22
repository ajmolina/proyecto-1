package mobile.promat;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class FragmentNotas extends Fragment{
	
	EditText editNotas1;	
	EditText editNotas2;
	EditText editNotas3;
	EditText editNotas4;
	EditText editPorcentaje1;
	EditText editPorcentaje2;
	EditText editPorcentaje3;
	EditText editPorcentaje4;
	
	private Spinner spinnerAsignaturas;
	private ArrayList<String> nombreAsignaturas;
	SQLiteDatabase database;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_notas, container, false);
		
		spinnerAsignaturas = (Spinner)view.findViewById(R.id.spinnerAsignaturas);
		nombreAsignaturas = new ArrayList<>();
		database = getActivity().openOrCreateDatabase("HistorialNotas", Context.MODE_PRIVATE, null);
		Cursor c = database.rawQuery("SELECT * FROM materias", null);
		
		if (c.moveToFirst()){
			do{
				nombreAsignaturas.add(c.getString(1));
			}while(c.moveToNext());
		}
		
		database.close();		
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,nombreAsignaturas);
		spinnerAsignaturas.setAdapter(dataAdapter);
		addListenerOnSpinnerItemSelection();
		return view;
	}
	
	public void addListenerOnSpinnerItemSelection()
	{		
        spinnerAsignaturas.setOnItemSelectedListener(new OnItemSelectedListener() 
		{   @Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) 
		    {
			
				cleanEditTextFields();
				editNotas1 = (EditText) getActivity().findViewById(R.id.editNotas1);
				editNotas2 = (EditText) getActivity().findViewById(R.id.editNotas2);
				editNotas3 = (EditText) getActivity().findViewById(R.id.editNotas3);
				editNotas4 = (EditText) getActivity().findViewById(R.id.editNotas4);
				editPorcentaje1 = (EditText) getActivity().findViewById(R.id.editPorcentaje1);
				editPorcentaje2 = (EditText) getActivity().findViewById(R.id.editPorcentaje2);
				editPorcentaje3 = (EditText) getActivity().findViewById(R.id.editPorcentaje3);
				editPorcentaje4 = (EditText) getActivity().findViewById(R.id.editPorcentaje4);
				
				String nombreMateria = parent.getItemAtPosition(position).toString();
				
				database = getActivity().openOrCreateDatabase("HistorialNotas", Context.MODE_PRIVATE, null);
				Cursor c = database.rawQuery(
						"SELECT notas.valor,notas.porcentaje FROM notas,materias " +
						"WHERE notas.idMateria=materias.id " +
						"AND materias.nombre='"+nombreMateria+"'", null);
				
				
				if (c.moveToFirst()){
					do{
						//La implementación es tal que muestra máximo 4 notas por materia.
						switch(c.getPosition()){
						case 0:								
							editNotas1.setText(c.getString(0));								
							editPorcentaje1.setText(c.getString(1));
							break;
						case 1:								
							editNotas2.setText(c.getString(0));								
							editPorcentaje2.setText(c.getString(1));
							break;
						case 2:								
							editNotas3.setText(c.getString(0));								
							editPorcentaje3.setText(c.getString(1));
							break;
						case 3:								
							editNotas4.setText(c.getString(0));								
							editPorcentaje4.setText(c.getString(1));
							break;
						default:
							break;
						}
						
					}while(c.moveToNext());
				}else{
					Toast.makeText(parent.getContext(), 
			                "No se han ingresado notas para esta materia.",
			                Toast.LENGTH_LONG).show();
				}
				
				database.close();

				
    		}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				 
			}
		});
	}
	
	public void cleanEditTextFields(){
		editNotas1 = (EditText) getActivity().findViewById(R.id.editNotas1);
		editNotas2 = (EditText) getActivity().findViewById(R.id.editNotas2);
		editNotas3 = (EditText) getActivity().findViewById(R.id.editNotas3);
		editNotas4 = (EditText) getActivity().findViewById(R.id.editNotas4);
		editPorcentaje1 = (EditText) getActivity().findViewById(R.id.editPorcentaje1);
		editPorcentaje2 = (EditText) getActivity().findViewById(R.id.editPorcentaje2);
		editPorcentaje3 = (EditText) getActivity().findViewById(R.id.editPorcentaje3);
		editPorcentaje4 = (EditText) getActivity().findViewById(R.id.editPorcentaje4);
		editNotas1.setText("");
		editNotas2.setText("");
		editNotas3.setText("");
		editNotas4.setText("");
		editPorcentaje1.setText("");
		editPorcentaje2.setText("");
		editPorcentaje3.setText("");
		editPorcentaje4.setText("");
	}
}
