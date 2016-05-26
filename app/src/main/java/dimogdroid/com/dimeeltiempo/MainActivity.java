package dimogdroid.com.dimeeltiempo;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.skyfishjy.library.RippleBackground;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dimogdroid.com.api.Conexion;
import dimogdroid.com.api.ConnectionException;
import dimogdroid.com.bd.ProveedorBd;
import dimogdroid.com.bd.ProveedorBdImpl;
import dimogdroid.com.entidades.Alarma;
import dimogdroid.com.entidades.Favoritos;
import dimogdroid.com.entidades.Municipio;
import dimogdroid.com.entidades.Provincia;
import dimogdroid.com.util.AlarmaManager;
import dimogdroid.com.util.CustomLog;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener,
        TimePickerDialog.OnTimeSetListener {

    //TODO Banner
    //Hacerle uno nuevo
    private static final String MY_AD_UNIT_ID = "ca-app-pub-7866637665636353/8934999021";

    LinearLayout mLinearLayout;
    LinearLayout mLinearLayoutHeader;
    ValueAnimator mAnimator;
    RadioButton rdEspania;

    private static TextoHablado task;
    Context context = this;
    public String lugar;

    private TextToSpeech mTts;

    Typeface tf_regular;

    ProveedorBd proveedor;
    Spinner spnFavoritos;
    Spinner spnProvincias;
    Spinner spnMunicipios;
    Spinner spnContinentes;
    Spinner spnCiudades;

    TextView txtSpinner;

    ImageView imgFavSpania;
    ImageView imgFavResto;
    ImageView imgTrash;

    ImageView imgAlarmaGo;
    TextView txtAlarma;
    ImageView imgAlarmaCancel;

    TextView txtDias;
    TextView txtHora;

    static boolean enDialog = false;


    int cardActiva = 0; // 0= favoritos, 1= Spania, 2= Resto

    String urlActiva = null;

    String provParaFav = null;
    String munParaFav = null;
    String provParaDialog = null;
    String munParaDialog = null;

    ArrayAdapter<CharSequence> adapter;

    //Listas
    List<Favoritos> lstFavoritos;
    List<Provincia> lstProvincias;
    List<Municipio> lstMunicipios;
    List<Provincia> lstContinentes;
    List<Municipio> lstCiudades;

    List<CharSequence> listDias = new ArrayList<CharSequence>();

    int mDay;
    int hora;
    int minutos;

    private AdView adView;

    //Dias Semana
    TextView txtLunes;
    TextView txtMartes;
    TextView txtMiercoles;
    TextView txtJueves;
    TextView txtViernes;
    TextView txtSabado;
    TextView txtDomingo;

    ListView listDiasMarcados;

    boolean estamosEnAlarma ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);



        mTts = new TextToSpeech(this, this);
        proveedor = ProveedorBdImpl.getProveedor(this);

        tf_regular = Typeface.createFromAsset(getAssets(), "fonts/goeo.ttf");

        cargarPublicidad();

        estamosEnAlarma = false;
        //Si venimos de una Alarma
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String provincia = "";
        String municipio = "";
        String urlAlarma = "";
        if (bundle!=null){
            provincia = bundle.getString("provincia");
            municipio = bundle.getString("municipio");
            urlAlarma = bundle.getString("urlactiva");
        }
        if (!provincia.equalsIgnoreCase("") && !municipio.equalsIgnoreCase("") &&
                !urlAlarma.equalsIgnoreCase("")) {

            provParaDialog = provincia;
            munParaDialog = municipio;
            urlActiva = urlAlarma;

            estamosEnAlarma =true;

            mostrarDialogo();


        } else {
            cargarInicio();
        }

    }

    private void cargarInicio(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActualizamosVariables();

                //VALIDO !!! NO BORRARR
                task = new TextoHablado();
                task.execute();

                //mostrarDialogo();

                //Todo Poner datos de lectura
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });

        //Cabeceras
        final RippleView rplFavoritos = (RippleView) findViewById(R.id.rplfavoritos);
        rplFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerLayoutActivo("Favoritos");
            }
        });
        final RippleView rplSpania = (RippleView) findViewById(R.id.rplespania);
        rplSpania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerLayoutActivo("Spania");
            }
        });
        final RippleView rplResto = (RippleView) findViewById(R.id.rplresto);
        rplResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponerLayoutActivo("Resto");
            }
        });


        //Cargo favoritos
        spnFavoritos = (Spinner) findViewById(R.id.spnfav);
        cargarFavoritos();

        //Añadir aFavoritos

        imgFavSpania = (ImageView) findViewById(R.id.imgaddfavesp);
        imgFavSpania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                provParaFav = String.valueOf(spnProvincias.getSelectedItem());
                munParaFav = String.valueOf(spnMunicipios.getSelectedItem());
                rotarFavorito(imgFavSpania);
                if ((provParaFav != null && !provParaFav.equalsIgnoreCase(""))
                        &&
                        (munParaFav != null && !munParaFav.equalsIgnoreCase(""))) {
                    GuardarFavorito();
                    cargarFavoritos();
                }
            }
        });

        imgFavResto = (ImageView) findViewById(R.id.imgaddfavrest);
        imgFavResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                provParaFav = String.valueOf(spnContinentes.getSelectedItem());
                munParaFav = String.valueOf(spnCiudades.getSelectedItem());
                rotarFavorito(imgFavResto);
                if ((provParaFav != null && !provParaFav.equalsIgnoreCase(""))
                        &&
                        (munParaFav != null && !munParaFav.equalsIgnoreCase(""))) {
                    GuardarFavorito();
                    cargarFavoritos();
                }
            }
        });


        //Eliminar Favoritos
        imgTrash = (ImageView) findViewById(R.id.imgdelfav);
        imgTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favoritos favorito = (Favoritos) spnFavoritos.getSelectedItem();
                rotarFavorito(imgTrash);
                if (favorito != null) {
                    EliminarFavorito(favorito);
                    cargarFavoritos();
                }

            }
        });

        //Cargo las provincias
        spnProvincias = (Spinner) findViewById(R.id.spnspainprov);
        spnMunicipios = (Spinner) findViewById(R.id.spnspainmun);
        lstProvincias = proveedor.lstProvincias();
        if (lstProvincias != null) {

            ArrayAdapter<Provincia> spnAdapterProv = new ArrayAdapter<Provincia>(
                    this,
                    R.layout.spinner_textview,
                    lstProvincias);


            spnProvincias.setAdapter(spnAdapterProv);
        }
        spnProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), position + "is selected " + id, Toast.LENGTH_SHORT).show();

                //La posicion empieza por 0
                provParaFav = lstProvincias.get(position).getProvincia();
                cargarMunicipios(position + 1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spnMunicipios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                munParaFav = lstMunicipios.get(position).getMunicipio();
                urlActiva = lstMunicipios.get(position).getUrl();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //Cargo los continentes
        spnContinentes = (Spinner) findViewById(R.id.spnrestocontinente);
        spnCiudades = (Spinner) findViewById(R.id.spnrestociudad);
        lstContinentes = proveedor.lstContinentes();
        if (lstContinentes != null) {
            ArrayAdapter<Provincia> spnAdapterCont = new ArrayAdapter<Provincia>(
                    this,
                    R.layout.spinner_textview_res,
                    lstContinentes);
            spnContinentes.setAdapter(spnAdapterCont);
        }
        spnContinentes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                provParaFav = lstContinentes.get(position).getProvincia();
                cargarCiudades(position + 1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        spnCiudades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (lstCiudades != null) {
                    munParaFav = lstCiudades.get(position).getMunicipio();
                    urlActiva = lstCiudades.get(position).getUrl();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //Alarma

        //TODO Recuperar alarma si existe.

        txtAlarma = (TextView) findViewById(R.id.txtalarma);
        txtAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearNuevaAlarma();
            }
        });

        ActualizamosVariables();




    }

    private void cargarPublicidad(){
        //Publicidad
        adView = new AdView(this);
        adView.setAdUnitId(MY_AD_UNIT_ID);
        adView.setAdSize(AdSize.BANNER);

        // Iniciar una solicitud genérica. VALIDA
        //AdRequest adRequestB = new AdRequest.Builder().build();

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // Todos los emuladores
                .addTestDevice("A31D280E26B4043E35F3B89D12C66B27") // Mi teléfono de   prueba Galaxy Nexus
                .build();

        // Cargar adView con la solicitud de anuncio.
        adView.loadAd(adRequest);
    }


    private void cargarFavoritos() {

        lstFavoritos = proveedor.lstFavoritos();
        if (lstFavoritos != null) {
            ArrayAdapter<Favoritos> spnAdapterFav = new ArrayAdapter<Favoritos>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    lstFavoritos);
            spnFavoritos.setAdapter(spnAdapterFav);
        }
        spnFavoritos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(), position + "is selected " + id, Toast.LENGTH_SHORT).show();
                //La posicion empieza por 0


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }


    private void rotarFavorito(ImageView imgFavorito) {


        RotateAnimation anim = new RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());
        //anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);

        imgFavorito.startAnimation(anim);

        // imgFavorito.setAnimation(null);
    }


    private void EliminarFavorito(Favoritos favDel) {
        proveedor.eliminaFavorito(favDel.getIdFavoritos());
    }


    private void GuardarFavorito() {

        long insertar = proveedor.insertarFavorito(provParaFav, munParaFav, urlActiva);

        if (insertar == 0) {
            Log.e("GuardarFavorito", "Error al insertar");
        }

    }

    private void ponerLayoutActivo(String lytActivo) {

        LinearLayout lytFav = (LinearLayout) findViewById(R.id.lytactivofav);
        LinearLayout lytSpa = (LinearLayout) findViewById(R.id.lytactivoesp);
        LinearLayout lytRes = (LinearLayout) findViewById(R.id.lytactivoresto);
        if (lytActivo.equalsIgnoreCase("Favoritos")) {
            lytFav.setBackgroundColor(Color.parseColor("#C987E2"));
            lytSpa.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lytRes.setBackgroundColor(Color.parseColor("#FFFFFF"));
            cardActiva = 0;
        }
        if (lytActivo.equalsIgnoreCase("Spania")) {
            lytFav.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lytSpa.setBackgroundColor(Color.parseColor("#C987E2"));
            lytRes.setBackgroundColor(Color.parseColor("#FFFFFF"));
            cardActiva = 1;
        }
        if (lytActivo.equalsIgnoreCase("Resto")) {
            lytFav.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lytSpa.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lytRes.setBackgroundColor(Color.parseColor("#C987E2"));
            cardActiva = 2;
        }
    }


    private void mostrarDialogo() {

        if (!estamosEnAlarma){
            ActualizamosVariables();
        }

        final Dialog dialog = new Dialog(this);
        //dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        //dialog.setContentView(R.layout.custom_dialog);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View main = inflater.inflate(R.layout.custom_dialog, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(main);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        // Añadirle adView.
        final FrameLayout frmlayoutPubli = (FrameLayout) main.findViewById(R.id.publicidad);
        try {
            frmlayoutPubli.addView(adView);
        } catch (Exception e) {
            ((ViewGroup) adView.getParent()).removeView(adView);
            frmlayoutPubli.addView(adView);
        }

        dialog.setCancelable(true);


        final RippleBackground rippleBackground = (RippleBackground) main.findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        TextView txtProvhablado = (TextView) main.findViewById(R.id.txtprovhablado);
        TextView txtMunhablado = (TextView) main.findViewById(R.id.txtmunhablado);
        txtProvhablado.setTypeface(tf_regular);
        txtMunhablado.setTypeface(tf_regular);

        txtProvhablado.setText(provParaDialog);
        txtMunhablado.setText(munParaDialog);


        //set up button
        ImageView button = (ImageView) dialog.findViewById(R.id.imgStop);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rippleBackground.stopRippleAnimation();
                frmlayoutPubli.removeView(adView);
                mTts.stop();
                enDialog = false;
                if (!estamosEnAlarma){
                    dialog.dismiss();
                }else{
                    finish();
                }
                //dialog.dismiss();

            }
        });

        dialog.show();
        //dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);
        dialog.getWindow().setAttributes(lp);

    }

    private void ActualizamosVariables() {


        if (cardActiva == 0) { //Favoritos
            if ((spnFavoritos.getSelectedItem()!=null)) {


                String completo = String.valueOf(spnFavoritos.getSelectedItem());

                provParaDialog = completo.substring(completo.indexOf("(") + 1, completo.indexOf(")"));
                munParaDialog = completo.substring(0, completo.indexOf("(") - 1);

                //TODO Eventual !! .. esto hay que quitarlo
                urlActiva = lstFavoritos.get(0).getUrl();

            }
        }
        if (cardActiva == 1) { //Spania
            provParaDialog = String.valueOf(spnProvincias.getSelectedItem());
            munParaDialog = String.valueOf(spnMunicipios.getSelectedItem());
        }
        if (cardActiva == 2) { //Resto
            provParaDialog = String.valueOf(spnContinentes.getSelectedItem());
            munParaDialog = String.valueOf(spnCiudades.getSelectedItem());
        }

    }

    private void cargarCiudades(int i) {
        lstCiudades = proveedor.lstCiudades(i);
        if (lstCiudades != null) {
            ArrayAdapter<Municipio> spnAdapterCiudades = new ArrayAdapter<Municipio>(
                    this,
                    R.layout.spinner_textview_res,
                    lstCiudades);
            spnCiudades.setAdapter(spnAdapterCiudades);
        }

    }

    private void cargarMunicipios(int i) {

        lstMunicipios = proveedor.lstMunicipios(i);
        if (lstMunicipios != null) {
            ArrayAdapter<Municipio> spnAdapterMun = new ArrayAdapter<Municipio>(
                    this,
                    R.layout.spinner_textview,
                    lstMunicipios);
            spnMunicipios.setAdapter(spnAdapterMun);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTts.shutdown();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.getDefault());


        }
    }

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

    private void CrearNuevaAlarma() {

        final Calendar c = Calendar.getInstance();

        mDay = c.get(Calendar.DAY_OF_MONTH);
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        final Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
        myDialog.setContentView(R.layout.recordatorio);
        myDialog.setTitle(getString(R.string.notifica));

        myDialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                R.drawable.ic_launcher);

        myDialog.setCancelable(false);

        txtLunes = (TextView) myDialog.findViewById(R.id.txtlunes);
        txtMartes = (TextView) myDialog.findViewById(R.id.txtmartes);
        txtMiercoles = (TextView) myDialog.findViewById(R.id.txtmiercoles);
        txtJueves = (TextView) myDialog.findViewById(R.id.txtjueves);
        txtViernes = (TextView) myDialog.findViewById(R.id.txtviernes);
        txtSabado = (TextView) myDialog.findViewById(R.id.txtsabado);
        txtDomingo = (TextView) myDialog.findViewById(R.id.txtdomingo);



        txtDias = (TextView) myDialog
                .findViewById(R.id.txtdiassemana);
        final Button btnDias = (Button) myDialog.findViewById(R.id.btndias);
        btnDias.setText(getString(R.string.dias));
        btnDias.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cargarDias();
                mostrarDias();
            }
        });


        txtHora = (TextView) myDialog.findViewById(R.id.txthora);

        final Button btnHora = (Button) myDialog.findViewById(R.id.btnhora);
        txtHora.setText(new StringBuilder().append(pad(hora)).append(":")
                .append(pad(minutos))
                + " H");
        btnHora.setText(getString(R.string.hora));
        btnHora.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                mostrarHora();
            }
        });

        Button btn_cancelar = (Button) myDialog.findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        Button btn_aceptar = (Button) myDialog.findViewById(R.id.btn_aceptar);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar current = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();

                //Todo Buscar el siguiente día a tocar!!
                cal.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), 23, hora, minutos, 00);

                //TODO pruebas !!
                Alarma alarma = new Alarma();
                alarma.setDias("L,M");
                alarma.setHora(String.valueOf(hora));
                alarma.setMinutos(String.valueOf(minutos));

                AlarmaManager almManager = new AlarmaManager();
                cal = almManager.AlarmaOn(alarma);

                //--------------

                if (cal.compareTo(current) <= 0) {
                    // The set Date/Time already passed
                    new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(getString(R.string.dateinvalid))
                            .show();
                } else {
                    setAlarm(cal);
                }

                myDialog.dismiss();
            }
        });

        myDialog.show();

    }

    private void mostrarDias() {

        final CharSequence[] dialogList = listDias.toArray(new CharSequence[listDias.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(MainActivity.this);
        builderDialog.setTitle(getString(R.string.titulodias));
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count]; // set is_checked boolean false;

        if (listDiasMarcados!=null){
            listDiasMarcados.setAdapter(null);
        }

        ponerTodoRojo();

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton, boolean isChecked) {

                        if (listDiasMarcados!=null){
                            for (int i = 0; i < listDiasMarcados.getCount(); i++) {
                                boolean checked = listDiasMarcados.isItemChecked(i);

                                if (checked) {
                                    switch (i) {
                                        case 0:
                                            txtLunes.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;
                                        case 1:
                                            txtMartes.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;
                                        case 2:
                                            txtMiercoles.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;
                                        case 3:
                                            txtJueves.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;
                                        case 4:
                                            txtViernes.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;
                                        case 5:
                                            txtSabado.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;
                                        case 6:
                                            txtDomingo.setBackgroundColor(Color.parseColor("#23E085"));
                                            break;

                                    }

                                }
                            }
                        }


                    }
                });

        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listDiasMarcados = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < listDiasMarcados.getCount(); i++) {
                            boolean checked = listDiasMarcados.isItemChecked(i);

                            if (checked) {
                                switch (i) {
                                    case 0:
                                        txtLunes.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;
                                    case 1:
                                        txtMartes.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;
                                    case 2:
                                        txtMiercoles.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;
                                    case 3:
                                        txtJueves.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;
                                    case 4:
                                        txtViernes.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;
                                    case 5:
                                        txtSabado.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;
                                    case 6:
                                        txtDomingo.setBackgroundColor(Color.parseColor("#23E085"));
                                        break;

                                }

                            }
                        }

                    }

                });
        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();
    }

    private void ponerTodoRojo() {

        txtLunes.setBackgroundColor(Color.parseColor("#EA0E20"));
        txtMartes.setBackgroundColor(Color.parseColor("#EA0E20"));
        txtMiercoles.setBackgroundColor(Color.parseColor("#EA0E20"));
        txtJueves.setBackgroundColor(Color.parseColor("#EA0E20"));
        txtViernes.setBackgroundColor(Color.parseColor("#EA0E20"));
        txtSabado.setBackgroundColor(Color.parseColor("#EA0E20"));
        txtDomingo.setBackgroundColor(Color.parseColor("#EA0E20"));

    }

    private void cargarDias() {

        listDias.clear();
        listDias.add(getString(R.string.lunes));
        listDias.add(getString(R.string.martes));
        listDias.add(getString(R.string.miercoles));
        listDias.add(getString(R.string.jueves));
        listDias.add(getString(R.string.viernes));
        listDias.add(getString(R.string.sabado));
        listDias.add(getString(R.string.domingo));

    }

    private void mostrarHora() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);

        //tpd.setThemeDark(modeDarkTime.isChecked());
        //tpd.vibrate(vibrateTime.isChecked());
        //tpd.dismissOnPause(dismissTime.isChecked());
        tpd.enableSeconds(false);
        //if (modeCustomAccentTime.isChecked()) {
        tpd.setAccentColor(Color.parseColor("#2980b9"));
        //}
        //if (titleTime.isChecked()) {
        tpd.setTitle("Dime el Tiempo");
        //}
        //if (limitTimes.isChecked()) {
        //tpd.setTimeInterval(2, 5, 10);
        //}
        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + " : " + minuteString ;
        txtHora.setText(time);

        hora = hourOfDay;
        minutos = minute;

    }


    private void setAlarm(Calendar targetCal) {

        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm '  ' dd/MM/yyyy");
        String formatted = format1.format(targetCal.getTime());
        targetCal.set(Calendar.SECOND, 0);


        //TODO poner hora próxima alarma !
        new SweetAlertDialog(this)
                .setTitleText(getString(R.string.notifica))
                .setContentText(getString(R.string.notificacreada) + " " + formatted)
                .show();


//        Toast.makeText(this,
//				getString(R.string.notificacreada) + " " + formatted,
//				Toast.LENGTH_LONG).show();

        Bundle b = new Bundle();
        b.putString("provincia", provParaDialog);
        b.putString("municipio", munParaDialog);
        b.putString("urlactiva", urlActiva);

        Intent intent = new Intent(this, EntryReceiver.class);
        intent.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
       // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
       //         + (10 * 1000), pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);


      //  AlarmManager am = (AlarmManager) context
      //          .getSystemService(Activity.ALARM_SERVICE);

      //  Intent intent = new Intent(context, EntryReceiver.class);
        //TODO Recuperar Id de la tabla
        //intent.putExtra("alarma", alarma.getId());

        //PendingIntent pendingIntent = PendingIntent.getActivity(context,
        //        alarma.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);

       // PendingIntent pendingIntent = PendingIntent.getActivity(context,
       //         8, intent, PendingIntent.FLAG_CANCEL_CURRENT);

       // am.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);


    }


    /**
     * AsyncTask TextoHablado
     */

    class TextoHablado extends AsyncTask<String, Integer, String> {

        SweetAlertDialog pDialog;
        String tiempo;

        @Override
        protected void onPreExecute() {

            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText(getString(R.string.Cargando));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            Conexion conexion = new Conexion(lugar);
            InputStream resultado = null;

            BufferedReader br = null;
            String line = null;

            try {
                resultado = conexion.conectar(context, urlActiva);

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

                        tiempo = QuitarEtiquetas(line);

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

            pDialog.cancel();

            mostrarDialogo();

            HablarTexto(tiempo);



        }


    }

    public String QuitarEtiquetas(String enviada) {
        String limpia = "";

        try {

            if (enviada == null) {
                return null;
            }

            limpia = enviada;

            // String limpia=sucia;
            if (limpia.contains("<p>")) { // grados del satelite
                limpia = limpia.replaceAll("<p>", "");
            }

            if (limpia.contains("ºC")) { // grados del satelite
                limpia = limpia.replaceAll("ºC", getString(R.string.grados));
            }

            if (limpia.contains("km/h")) { // grados del satelite
                limpia = limpia.replaceAll("km/h", "kilometros hora");
            }

            if (limpia.contains("</p>")) { // grados del satelite
                limpia = limpia.replaceAll("</p>", "");
            }
            if (limpia.contains("<br>")) { // grados del satelite
                limpia = limpia.replaceAll("<br>", "");
            }
            if (limpia.contains("<br/>")) { // grados del satelite
                limpia = limpia.replaceAll("<br/>", "");
            }
            if (limpia.contains("<\\t>")) { // grados del satelite
                limpia = limpia.replaceAll("<\\t>", "");
            }
            if (limpia.contains("null'")) { // grados del satelite
                limpia = limpia.replaceAll("null", "");
            }
            // if (limpia.indexOf(",") >= 0) { // grados del satelite
            // limpia = limpia.replaceAll(",", "");
            // }
            if (limpia.contains("</div>")) { // grados del satelite
                limpia = limpia.replaceAll("</div>", "");
            }

            if (limpia.contains("<\\n>")) { // grados del satelite
                limpia = limpia.replaceAll("<\\n>", "");
            }
            if (limpia.contains("<\\t>")) { // grados del satelite
                limpia = limpia.replaceAll("<\\t>", "");
            }

            if (limpia.contains("<strong>")) { // grados del satelite
                limpia = limpia.replaceAll("<strong>", "");
            }
            if (limpia.contains("</strong>")) { // grados del satelite
                limpia = limpia.replaceAll("</strong>", "");
            }
            if (limpia.contains("<dl")) { // grados del satelite
                limpia = limpia.replaceAll("<dl", "");
            }
            if (limpia.contains("<dd>")) { // grados del satelite
                limpia = limpia.replaceAll("<dd>", "");
            }
            if (limpia.contains("-")) { // grados del satelite
                limpia = limpia.replaceAll("-", " ");
            }

            if (limpia.contains("class=\"marginTop0")) { // grados
                // del
                // satelite
                limpia = limpia.replaceAll("class=\"marginTop0", "");
            }

            if (limpia.contains("marginBottom0\">")) { // grados
                // del
                // satelite
                limpia = limpia.replaceAll("marginBottom0\">", "");
            }

            if (limpia.contains("class=\"margintop0")) {
                limpia = limpia.replaceAll("class=\"margintop0", "");
            }

            if (limpia.contains("marginbottom0\">")) {
                limpia = limpia.replaceAll("marginbottom0\">", "");
            }

            if (limpia.contains("</dd>")) { // grados del satelite
                limpia = limpia.replaceAll("</dd>", "");
            }
            if (limpia.contains("</dl>")) { // grados del satelite
                limpia = limpia.replaceAll("</dl>", ". ");
            }
            if (limpia.contains("</td>")) { // grados del satelite
                limpia = limpia.replaceAll("</td>", ".");
            }
            if (limpia.contains("<font")) { // grados del satelite
                limpia = limpia.replaceAll("<font", "");
            }
            if (limpia.contains("color=\"#ab3000\">")) { // grados del
                // satelite
                limpia = limpia.replaceAll("color=\"#ab3000\">", "");
            }
            if (limpia.contains("</font>")) { // grados del satelite
                limpia = limpia.replaceAll("</font>", "");
            }

            if (limpia.contains("/")) { // grados del satelite
                limpia = limpia.replaceAll("/", ",");
            }

        } catch (Exception ex) {
            CustomLog.error("limpiarString", ex.getMessage());
        }

        return limpia;
    }


}
