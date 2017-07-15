package dimogdroid.com.dimeeltiempo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dimogdroid.com.api.Conexion;
import dimogdroid.com.api.ConnectionException;
import dimogdroid.com.bd.ProveedorBd;
import dimogdroid.com.bd.ProveedorBdImpl;
import dimogdroid.com.entidades.Favoritos;
import dimogdroid.com.entidades.Municipio;
import dimogdroid.com.entidades.Provincia;
import dimogdroid.com.util.Utilidades;

import static android.R.attr.fillColor;
import static dimogdroid.com.dimeeltiempo.R.id.spnMunicipios;
import static dimogdroid.com.dimeeltiempo.R.id.spnProvincias;

/**
 * Created by DDAVILA on 19/06/2017.
 */


public class TabSpania extends Fragment  implements TextToSpeech.OnInitListener{
    ProveedorBd proveedor;
    List<Provincia> lstProvincias;
    List<Municipio> lstMunicipios;

    Spinner spnProv;
    Spinner spnMun;
    CircularProgressButton btnSpaniaHablar;
    CircularProgressButton btnSpaniaFav;

    public String lugar;


    public String provinciaElegida;
    public String muniElegido;
    public String urlActiva;
    ImageView imgFavSpania;

    private static TextoHablado task;
    private TextToSpeech mTts;
    Utilidades utiles;

    //private AdView adView;
    Typeface tf_regular;
    boolean estamosEnAlarma ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_spania, container, false);

        spnProv = (Spinner) view.findViewById(R.id.spnProvincias);
        spnMun = (Spinner) view.findViewById(spnMunicipios);
        //btnSpaniaHablar = (FloatingTextButton) view.findViewById(R.id.btnSpania);

        btnSpaniaHablar = (CircularProgressButton) view.findViewById(R.id.btn_id);
        imgFavSpania = (ImageView)  view.findViewById(R.id.imgaddfavesp);


        proveedor = ProveedorBdImpl.getProveedor(getContext());
        //Cargamos las provincias
        lstProvincias = proveedor.lstProvincias();
        if (lstProvincias != null) {

            ArrayAdapter<Provincia> spnAdapterProv = new ArrayAdapter<Provincia>(
                    getContext(),
                    R.layout.spinner_textview,
                    lstProvincias);
            spnProv.setAdapter(spnAdapterProv);
        }
        provinciaElegida = lstProvincias.get(0).getProvincia();






       // spnProv.setItems(lstProvincias);


        //Cargamos la primera provincia, que es A Coruña
        lstMunicipios = proveedor.lstMunicipios(lstProvincias.get(0).getIdProvincia());
        //spnMun.setItems(lstMunicipios);
        muniElegido = lstMunicipios.get(0).getMunicipio();
        urlActiva = lstMunicipios.get(0).getUrl();

        mTts = new TextToSpeech(getContext(), this);

        tf_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/goeo.ttf");

        utiles = new Utilidades();

        estamosEnAlarma = false;

        spnProv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), position + "is selected " + id, Toast.LENGTH_SHORT).show();

                //La posicion empieza por 0
               // provParaFav = lstProvincias.get(position).getProvincia();


                lstMunicipios = proveedor.lstMunicipios(lstProvincias.get(position).getIdProvincia());
                cargarMunicipios(lstProvincias.get(position).getIdProvincia());
                urlActiva = lstMunicipios.get(0).getUrl();
                provinciaElegida = lstProvincias.get(position).getProvincia();
              //  Snackbar.make(view, "Clicked " + lstProvincias.get(position).getProvincia(), Snackbar.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    /*    spnProv.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<Provincia>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, Provincia item) {
                lstMunicipios = proveedor.lstMunicipios(item.getIdProvincia());
                spnMun.setItems(lstMunicipios);
                urlActiva = lstMunicipios.get(0).getUrl();
                provinciaElegida = item.getProvincia();
                Snackbar.make(view, "Clicked " + item.getProvincia(), Snackbar.LENGTH_LONG).show();
            }
        });*/
        spnMun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                muniElegido = lstMunicipios.get(position).getMunicipio();
                urlActiva = lstMunicipios.get(position).getUrl();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



       btnSpaniaHablar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new TextoHablado();
                task.execute();

            }
        });

        imgFavSpania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Snackbar.make(v, getString(R.string.addfav), Snackbar.LENGTH_LONG).show();
                    rotarFavorito(imgFavSpania);
                    Favoritos fav = new Favoritos();
                    fav.setProvincia(provinciaElegida);
                    fav.setMunicipio(muniElegido);
                    fav.setUrl(urlActiva);
                    proveedor.insertarFavorito(fav);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        return view;
    }

    private void cargarMunicipios(int id) {

        lstMunicipios = proveedor.lstMunicipios(id);
        if (lstMunicipios != null) {
            ArrayAdapter<Municipio> spnAdapterMun = new ArrayAdapter<Municipio>(
                    getContext(),
                    R.layout.spinner_textview,
                    lstMunicipios);
            spnMun.setAdapter(spnAdapterMun);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTts.shutdown();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.getDefault());

        }
    }

    private void mostrarDialogo() {

        final Dialog dialog = new Dialog(getContext());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View main = inflater.inflate(R.layout.custom_dialog, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(main);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        // Añadirle adView.

        //TODO Publicidad, VALIDO !!
        /*final FrameLayout frmlayoutPubli = (FrameLayout) main.findViewById(R.id.publicidad);
        try {
            frmlayoutPubli.addView(adView);
        } catch (Exception e) {
            ((ViewGroup) adView.getParent()).removeView(adView);
            frmlayoutPubli.addView(adView);
        }*/

        dialog.setCancelable(true);


        final RippleBackground rippleBackground = (RippleBackground) main.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        TextView txtProvhablado = (TextView) main.findViewById(R.id.txtprovhablado);
        TextView txtMunhablado = (TextView) main.findViewById(R.id.txtmunhablado);
        txtProvhablado.setTypeface(tf_regular);
        txtMunhablado.setTypeface(tf_regular);

        txtProvhablado.setText(provinciaElegida);
        txtMunhablado.setText(muniElegido);


        //set up button
        ImageView button = (ImageView) dialog.findViewById(R.id.imgStop);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rippleBackground.stopRippleAnimation();
                //TODO Publicidad, valido !!
                //frmlayoutPubli.removeView(adView);
                mTts.stop();
                //enDialog = false;
                if (!estamosEnAlarma){
                    dialog.dismiss();
                }else{
                    getActivity().finish();
                }
                //dialog.dismiss();

            }
        });

        dialog.show();
        //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);
        dialog.getWindow().setAttributes(lp);

    }

    //TODO  Publicidad, VALIDO !!
   /*
    private void cargarPublicidad(){
        //Publicidad
        adView = new AdView(this);
        adView.setAdUnitId(MY_AD_UNIT_ID);
        adView.setAdSize(AdSize.BANNER);

        // Iniciar una solicitud genérica. VALIDA
        //AdRequest adRequestB = new AdRequest.Builder().build();

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Todos los emuladores
                .addTestDevice("245D7901D683E021EDC7CC0CEA869DFC") // Mi teléfono de   prueba Galaxy Nexus
                .build();

        // Cargar adView con la solicitud de anuncio.
        adView.loadAd(adRequest);
    }*/

    @SuppressWarnings("deprecation")
    private void HablarTexto(String TextoHablado) {

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());

        String aux = formattedDate.substring(0, 2);
        String aux2 = formattedDate.substring(3);

        int auxInt = Integer.valueOf(aux);
        int aux2Int = Integer.valueOf(aux2);

        String cadenaSaludo = "";

        if ((auxInt >= 4) && (auxInt <= 12)) {
            cadenaSaludo = " Buenas Días. ";
        } else {
            if ((auxInt > 12) && (auxInt <= 20)) {
                cadenaSaludo = " Buenas Tardes. ";
            } else {

                cadenaSaludo = " Buenas Noches. ";
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTts.speak(cadenaSaludo + " Son las " + auxInt + " Horas y " + aux2Int
                    + " minutos.", TextToSpeech.QUEUE_FLUSH, null, null);

            mTts.speak(TextoHablado, TextToSpeech.QUEUE_ADD, null, null);

        } else {
            mTts.speak(cadenaSaludo + " Son las " + auxInt + " Horas y " + aux2Int
                    + " minutos.", TextToSpeech.QUEUE_FLUSH, null);
            mTts.speak(TextoHablado, TextToSpeech.QUEUE_ADD, null);
        }

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * AsyncTask TextoHablado
     */

    class TextoHablado extends AsyncTask<String, Integer, String> {

        //SweetAlertDialog pDialog;
        String tiempo;

        @Override
        protected void onPreExecute() {

            btnSpaniaHablar.startAnimation();

       //     btnSpaniaFav.startAnimation();

          //  pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
          //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
          //  pDialog.setTitleText(getString(R.string.Cargando));
          //  pDialog.setCancelable(false);
          //  pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            Conexion conexion = new Conexion();
            InputStream resultado = null;

            BufferedReader br = null;
            String line = null;

            try {
                resultado = conexion.conectar(getContext(), urlActiva);

                br = new BufferedReader(new InputStreamReader(resultado,
                        "UTF-8"));

                boolean Encontrado = false;

                //Hemos de encontrar 2 veces esta cadena
                while (((line = br.readLine()) != null) && !Encontrado) {
                    if (line.indexOf("<div class=\"section\">") > 0) {
                        Encontrado = true;
                    }
                }

                Encontrado = false;
                //Hemos de encontrar 2 veces esta cadena
                while (((line = br.readLine()) != null) && !Encontrado) {
                    if (line.indexOf("<div class=\"section\">") > 0) {
                        Encontrado = true;
                    }
                }
                if (Encontrado) {
                    Encontrado = false;
                    while (((line = br.readLine()) != null) && !Encontrado) {
                        if (line.indexOf("<div class=\"body\">") > 0) {
                            Encontrado = true;
                        }
                    }

                    //Cuando sale de aqui tiene la previsión
                    if (Encontrado) {

                        tiempo = utiles.QuitarEtiquetas(line);

                    }


                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ConnectionException e) {
                e.printStackTrace();
            }
            return tiempo;
        }

        protected void onPostExecute(String prediccion) {

            //findViewById(R.id.cargando).setVisibility(View.GONE);

            //textoHablado();

            btnSpaniaHablar.revertAnimation();
      //      btnSpaniaFav.revertAnimation();

            mostrarDialogo();

            HablarTexto(tiempo);



        }


    }

    private void rotarFavorito(ImageView imgFavorito) {


        RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(700);
        imgFavorito.startAnimation(anim);
    }
}
