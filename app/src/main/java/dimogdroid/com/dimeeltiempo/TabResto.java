package dimogdroid.com.dimeeltiempo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

import dimogdroid.com.bd.ProveedorBd;
import dimogdroid.com.bd.ProveedorBdImpl;
import dimogdroid.com.entidades.Ciudad;
import dimogdroid.com.entidades.Continente;
import dimogdroid.com.entidades.Municipio;
import dimogdroid.com.entidades.Pais;

/**
 * Created by DDAVILA on 19/06/2017.
 */

public class TabResto extends Fragment {

    ProveedorBd proveedor;
    List<Continente> lstContinentes;
    List<Pais> lstPaises;
    List<Ciudad> lstCiudades;

    Spinner spnCont;
    Spinner spnPaises;
    Spinner spnCiudad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_resto, container, false);

        spnCont = (Spinner) view.findViewById(R.id.spnContinentes);
        spnPaises = (Spinner) view.findViewById(R.id.spnPaises);
        spnCiudad = (Spinner) view.findViewById(R.id.spnCiudades);

        proveedor = ProveedorBdImpl.getProveedor(getContext());
        lstContinentes = proveedor.lstContinentes();

     //   spnCont.setItems(lstContinentes);

        //Cargamos del primer Continente sus Paises
        lstPaises = proveedor.lstPaises(lstContinentes.get(0).getIdContinente());
     //   spnPaises.setItems(lstPaises);

        //Cargamos del primer Pais sus Ciudades
        lstCiudades = proveedor.lstCiudades(lstPaises.get(0).getIdPais());
     //   spnCiudad.setItems(lstCiudades);



        spnCont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lstPaises = proveedor.lstPaises((lstContinentes.get(position).getIdContinente()));
                cargarPaises(lstContinentes.get(position).getIdContinente());
                //Snackbar.make(view, "Clicked " + item.getContinente(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

/*
        spnPaises.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<Pais>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, Pais item) {
                lstCiudades = proveedor.lstCiudades(item.getIdPais());
                spnCiudad.setItems(lstCiudades);
                Snackbar.make(view, "Clicked " + item.getPais(), Snackbar.LENGTH_LONG).show();
            }
        });

*/
        return view;



    }

    private void cargarPaises(int id) {

        lstPaises = proveedor.lstPaises(id);
        if (lstPaises != null) {
            ArrayAdapter<Pais> spnAdapterMun = new ArrayAdapter<Pais>(
                    getContext(),
                    R.layout.spinner_textview,
                    lstPaises);
            spnPaises.setAdapter(spnAdapterMun);
        }

    }
}
