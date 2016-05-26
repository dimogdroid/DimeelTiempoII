package dimogdroid.com.util;

import android.content.Context;

import java.util.Calendar;

import dimogdroid.com.bd.ProveedorBd;
import dimogdroid.com.bd.ProveedorBdImpl;
import dimogdroid.com.entidades.Alarma;

/**
 * Created by ddavila on 23/05/2016.
 */
public class AlarmaManager {

    ProveedorBd proveedor;
    Context context;

    public AlarmaManager() {
    }

    public AlarmaManager(ProveedorBd proveedor, Context context) {
        this.proveedor = ProveedorBdImpl.getProveedor(context);
        this.context = context;
    }

    /**
     * Devuelve el Calendar de la proxima alarma
     * @param alarma
     * @return
     */
    public Calendar AlarmaOn(Alarma alarma) {

        boolean Encontrada = false;
        int sigDia;

        //Instacnciado con ala hora actual
        Calendar calADevolver = Calendar.getInstance();

        if (ExisteDia(alarma, diaDeHoy())) {
            if (HoraDeAlarmaMayorHoraActual(alarma)) {
                calADevolver.set(Calendar.HOUR_OF_DAY,
                        Integer.valueOf(alarma.getHora()));
                calADevolver.set(Calendar.MINUTE,
                        Integer.valueOf(alarma.getMinutos()));
                calADevolver.set(Calendar.SECOND, 0);
                calADevolver.set(Calendar.DAY_OF_WEEK, diaDeHoy());
                Encontrada = true;
            }
        }
        if (!ExisteDia(alarma, diaDeHoy()) || (!Encontrada)) {

            sigDia = siguienteDia(alarma);
            calADevolver.set(Calendar.HOUR_OF_DAY,
                    Integer.valueOf(alarma.getHora()));
            calADevolver.set(Calendar.MINUTE,
                    Integer.valueOf(alarma.getMinutos()));
            calADevolver.set(Calendar.SECOND, 0);
            calADevolver.add(Calendar.DATE, sigDia);

        }


        return calADevolver;
    }

    private int diaDeHoy() {
        Calendar calFechaActual = Calendar.getInstance();
        int diaDeHoy = calFechaActual.get(Calendar.DAY_OF_WEEK);
        return diaDeHoy;
    }

    /**
     * Mira si existe el dia actual en la cadena repetir.
     *
     * @param alarma
     * @return
     */
    public boolean ExisteDia(Alarma alarma, int dia) {

        boolean existe = false;
        int diaDeAlarma = -1;

        String diasRepetir = alarma.getDias();
        String[] arrayDias = diasRepetir.split(",");

        for (int i = 0; i < arrayDias.length; i++) {

            diaDeAlarma = diaEnInt(arrayDias[i].trim());

            if (dia == diaDeAlarma) {
                existe = true;
            }

        }

        return existe;
    }

    public int siguienteDia(Alarma alarma) {

        int sigDia = -1;
        boolean Encontrada = false;
        boolean EncontradoDia = false;

        int pasos = 0;

        int control = 0;

        if (ExisteDia(alarma, diaDeHoy())) {
            if (HoraDeAlarmaMayorHoraActual(alarma)) {
                sigDia = diaDeHoy();
                Encontrada = true;
            } else {
                Encontrada = false;
            }
        }
        if (!ExisteDia(alarma, diaDeHoy()) || (!Encontrada)) {

            sigDia = diaDeHoy();
            pasos = 0;

            while (!EncontradoDia) {

                sigDia++;
                pasos++;
                if (sigDia > 7) {
                    sigDia = 1;
                }

                if (ExisteDia(alarma, sigDia)) {
                    EncontradoDia = true;
                    // return sigDia;
                }
                control++;
                if (control > 10) {
                    break;
                }

            }

        }
        return pasos;
    }

    private boolean HoraDeAlarmaMayorHoraActual(Alarma alarma) {

        boolean superior = false;

        Calendar calAlarma = Calendar.getInstance();

        calAlarma.set(Calendar.HOUR_OF_DAY, Integer.valueOf(alarma.getHora()));
        calAlarma.set(Calendar.MINUTE, Integer.valueOf(alarma.getMinutos()));

        Calendar calActual = Calendar.getInstance();

        long diferencia = calActual.getTimeInMillis()
                - calAlarma.getTimeInMillis();

        if (diferencia < 0) {
            superior = true;
        }

        return superior;
    }


    private int diaEnInt(String dia){
        if (dia.equalsIgnoreCase("D")){
            return 1;
        }
        if (dia.equalsIgnoreCase("L")){
            return 2;
        }
        if (dia.equalsIgnoreCase("M")){
            return 3;
        }
        if (dia.equalsIgnoreCase("X")){
            return 4;
        }
        if (dia.equalsIgnoreCase("J")){
            return 5;
        }
        if (dia.equalsIgnoreCase("V")){
            return 6;
        }
        if (dia.equalsIgnoreCase("S")){
            return 7;
        }
        return -1;
    }

}
