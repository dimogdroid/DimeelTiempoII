package dimogdroid.com.dimeeltiempo;

/**
 * Created by DDAVILA on 19/06/2017.
 */
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import dimogdroid.com.util.AlarmaManager;
import dimogdroid.com.util.ClickListener;
import dimogdroid.com.util.EntryReceiver;
import dimogdroid.com.util.RecyclerItemClickListener;
import dimogdroid.com.util.Utilidades;

import static android.content.Context.ALARM_SERVICE;


public class TabFavoritos extends Fragment   implements TextToSpeech.OnInitListener, TimePickerDialog.OnTimeSetListener {

    private static String LOG_TAG = "TabFavoritos";

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<Favoritos> lstFavoritos;

    public String provinciaElegida;
    public String muniElegido;
    public String urlActiva;
    public String idFavoritoElegido;
    ImageView imgTrash;
    ImageView imgAlarm;
    public String lugar;

    private static TextoHablado task;
    Typeface tf_regular;
    private TextToSpeech mTts;

    Utilidades utiles;

    ProveedorBd proveedor;

    boolean estamosEnAlarma ;
    TextView txtDias;
    TextView txtHora;
    int mDay;
    int hora;
    int minutos;

    //Dias Semana
    TextView txtLunes;
    TextView txtMartes;
    TextView txtMiercoles;
    TextView txtJueves;
    TextView txtViernes;
    TextView txtSabado;
    TextView txtDomingo;

    ListView listDiasMarcados;
    List<CharSequence> listDias = new ArrayList<CharSequence>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_favoritos, container, false);

       // mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
       // mRecyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(this);
       // mRecyclerView.setLayoutManager(mLayoutManager);
       // mAdapter = new FavRecyclerViewAdapter(getDataSet());
       // mRecyclerView.setAdapter(mAdapter);


        proveedor = ProveedorBdImpl.getProveedor(getContext());

        mTts = new TextToSpeech(getContext(), this);

        utiles = new Utilidades();

        estamosEnAlarma = false;

       /* items.add(new Favoritos(1,"Jaen", "Arjona", "url1"));
        items.add(new Favoritos(2,"Granada", "Churriana", "url2"));
        items.add(new Favoritos(3,"Reino Unido", "Londres", "url3"));*/




// Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        cargarFavoritos();


       /* recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TextView tvwUrlActiva = (TextView) view.findViewById(R.id.urlfav);
                        urlActiva = tvwUrlActiva.getText().toString();
                        TextView tvwMunicipioActiva = (TextView) view.findViewById(R.id.txtmunicipiofav);
                        muniElegido = tvwMunicipioActiva.getText().toString();
                        TextView tvwProvinciaActiva = (TextView) view.findViewById(R.id.txtprovfav);
                        provinciaElegida = tvwProvinciaActiva.getText().toString();
                        TextView tvwfavoritoElegido = (TextView) view.findViewById(R.id.idfavorito);
                        idFavoritoElegido = tvwfavoritoElegido.getText().toString();

                        LlamarTextoHablado();

                        imgTrash = (ImageView) view.findViewById(R.id.trashfav);
                        imgTrash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Snackbar.make(v, "Clicked " + idFavoritoElegido, Snackbar.LENGTH_LONG).show();

                            }
                        });

                       /* Intent intent = new Intent(getContext(), DetalleArticuloActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("user_id", userId);
                        bundle.putInt("item_id", itemList.get(position - 1).getArticuloId());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                })
        );*/



        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                recycler, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                TextView tvwUrlActiva = (TextView) view.findViewById(R.id.urlfav);
                urlActiva = tvwUrlActiva.getText().toString();
                TextView tvwProvinciaActiva = (TextView) view.findViewById(R.id.txtprovfav);
                provinciaElegida = tvwProvinciaActiva.getText().toString();
                TextView tvwMunicipioActiva = (TextView) view.findViewById(R.id.txtmunicipiofav);
                muniElegido = tvwMunicipioActiva.getText().toString();
                tvwMunicipioActiva.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LlamarTextoHablado();
                    }
                });

                TextView tvwfavoritoElegido = (TextView) view.findViewById(R.id.idfavorito);
                idFavoritoElegido = tvwfavoritoElegido.getText().toString();

                imgTrash = (ImageView) view.findViewById(R.id.trashfav);
                imgTrash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getString(R.string.estasseguro))
                                .setCancelText(getString(R.string.cancelar))
                                .setConfirmText(getString(R.string.aceptar))
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        sDialog.cancel();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        proveedor.eliminaFavorito(Integer.parseInt(idFavoritoElegido));
                                        cargarFavoritos();
                                        sweetAlertDialog.cancel();

                                    }
                                })
                                .show();

                        Snackbar.make(v, "Clicked favorito:" + idFavoritoElegido, Snackbar.LENGTH_LONG).show();
                    }
                });

                imgAlarm = (ImageView) view.findViewById(R.id.alarm);
                imgAlarm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CrearNuevaAlarma();
                    }
                });




            }

            @Override
            public void onLongClick(View view, int position) {
                Snackbar.make(view, "Clicked longgg", Snackbar.LENGTH_LONG).show();
            }
        }));

        //Eliminar Favoritos
      /*  imgTrash = (ImageView) view.findViewById(R.id.trashfav);
        imgTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Favoritos favorito = (Favoritos) spnFavoritos.getSelectedItem();
                rotarFavorito(imgTrash);
                if (favorito != null) {
                    proveedor.eliminaFavorito(favorito.getIdFavoritos());
                    recycler.ref
                }

            }
        });*/

        return view;

    }



    public void cargarFavoritos(){
        lstFavoritos = proveedor.lstFavoritos();
        adapter = new FavRecyclerViewAdapter(lstFavoritos, getContext());
        recycler.setAdapter(adapter);
    }

    private void LlamarTextoHablado(){
        task = new TextoHablado();
        task.execute();
    }


    private ArrayList<Favoritos> getDataSet() {
        ArrayList results = new ArrayList<Favoritos>();
        for (int index = 0; index < 20; index++) {
            Favoritos obj = new Favoritos();
            obj.setProvincia("provinciaa");
            results.add(index, obj);
        }
        return results;
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

        final Dialog myDialog = new Dialog(getContext());
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
                alarma.setDias("D");
                alarma.setHora(String.valueOf(hora));
                alarma.setMinutos(String.valueOf(minutos));

                AlarmaManager almManager = new AlarmaManager();
                cal = almManager.AlarmaOn(alarma);

                //--------------

                if (cal.compareTo(current) <= 0) {
                    // The set Date/Time already passed
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(getActivity());
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
                TabFavoritos.this,
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
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");

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
        new SweetAlertDialog(getContext())
                .setTitleText(getString(R.string.notifica))
                .setContentText(getString(R.string.notificacreada) + " " + formatted)
                .show();


//        Toast.makeText(this,
//				getString(R.string.notificacreada) + " " + formatted,
//				Toast.LENGTH_LONG).show();

        Bundle b = new Bundle();
        b.putString("provincia", provinciaElegida);
        b.putString("municipio", muniElegido);
        b.putString("urlactiva", urlActiva);

        Intent intent = new Intent(getContext(), EntryReceiver.class);
        intent.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
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


    class TextoHablado extends AsyncTask<String, Integer, String> {

        SweetAlertDialog pDialog;
        String tiempo;

        @Override
        protected void onPreExecute() {

             pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
              pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
              pDialog.setTitleText(getString(R.string.Cargando));
              pDialog.setCancelable(false);
              pDialog.show();

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


            //      btnSpaniaFav.revertAnimation();

            pDialog.cancel();

            mostrarDialogo();

            HablarTexto(tiempo);



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



    public void deleteList(View view){

        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.estasseguro))
                .setCancelText(getString(R.string.cancelar))
                .setConfirmText(getString(R.string.aceptar))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.cancel();
                    }
                })
                .show();

    }



    /*



    public static ContentFragment newInstance(String contIndex) {
        ContentFragment cf = new ContentFragment();

        Bundle args = new Bundle();
        args.putString("contIndex", contIndex);
        cf.setArguments(args);
        return cf;
    }*/



}
