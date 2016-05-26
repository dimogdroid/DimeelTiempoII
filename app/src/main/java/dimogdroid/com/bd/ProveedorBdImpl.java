package dimogdroid.com.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dimogdroid.com.entidades.Favoritos;
import dimogdroid.com.entidades.Municipio;
import dimogdroid.com.entidades.Provincia;

/**
 * Created by ddavila on 09/05/2016.
 */
public class ProveedorBdImpl implements  ProveedorBd{

    private BdDimeElTiempo bdDimeElTiempo;
    private static ProveedorBdImpl bd;

    @SuppressWarnings("static-access")
    String[] camposProv = new String[] {bdDimeElTiempo.COL_ID_PROVINCIA,
                                        bdDimeElTiempo.COL_NOMBRE_PROVINCIA};

    String[] camposCont = new String[] {bdDimeElTiempo.COL_ID_CONTINENTES,
                                        bdDimeElTiempo.COL_NOMBRE_CONTINENTE};

    String[] camposMun = new String[] {bdDimeElTiempo.COL_ID_PROVINCIA,
                                       bdDimeElTiempo.COL_ID_MUNICIPIO,
                                       bdDimeElTiempo.COL_NOMBRE_MUNICIPIO,
                                       bdDimeElTiempo.COL_URL};

    String[] camposCiu = new String[] {bdDimeElTiempo.COL_ID_CIUDADES,
            bdDimeElTiempo.COL_ID_CONTINENTES,
            bdDimeElTiempo.COL_NOMBRE_CIUDAD,
            bdDimeElTiempo.COL_URL};


    String[] camposFav = new String[] {bdDimeElTiempo.COL_ID_FAVORITOS,
                                       bdDimeElTiempo.COL_NOMBRE_PROVINCIA,
                                       bdDimeElTiempo.COL_NOMBRE_MUNICIPIO,
                                       bdDimeElTiempo.COL_URL};


    private ProveedorBdImpl(Context context){
        bdDimeElTiempo = new BdDimeElTiempo(context);
        bdDimeElTiempo.getWritableDatabase();
    }

    public static ProveedorBd getProveedor(Context context){
        if (bd == null){
            bd = new ProveedorBdImpl(context);
        }
        return bd;
    }

    @Override
    public List<Provincia> lstProvincias() {
        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_PROVINCIAS, camposProv, null, null, null,
                null, BdDimeElTiempo.COL_ID_PROVINCIA);

        List<Provincia> lProvincias = new ArrayList<>();
        if (c != null) {
            Provincia provincia;
            while (c.moveToNext()) {
                provincia = new Provincia();
                provincia.setIdProvincia(c.getInt(0));
                provincia.setProvincia(c.getString(1));
                lProvincias.add(provincia);
            }

        }
        closeResource(c);

        return lProvincias;
    }

    @Override
    public List<Provincia> lstContinentes() {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_CONTINENTES, camposCont, null, null, null,
                null, BdDimeElTiempo.COL_ID_CONTINENTES);

        List<Provincia> lProvincias = new ArrayList<>();
        if (c != null) {
            Provincia provincia;
            while (c.moveToNext()) {
                provincia = new Provincia();
                provincia.setIdProvincia(c.getInt(0));

                provincia.setProvincia(c.getString(1));
                lProvincias.add(provincia);
            }

        }
        closeResource(c);

        return lProvincias;
    }

    @Override
    public List<Municipio> lstMunicipios(int idProvincia) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_MUNICIPIOS, camposMun, bdDimeElTiempo.COL_ID_PROVINCIA  + " = ? ",
                new String[] { Integer.toString(idProvincia) }, null,
                null, BdDimeElTiempo.COL_NOMBRE_MUNICIPIO);

        List<Municipio> lMunicipios = new ArrayList<>();
        if (c != null) {
            Municipio municipio;
            while (c.moveToNext()) {
                municipio = new Municipio();
                municipio.setIdProvincia(c.getInt(0));
                municipio.setIdMunicipio(c.getInt(1));
                municipio.setMunicipio(c.getString(2));
                municipio.setUrl(c.getString(3));
                lMunicipios.add(municipio);
            }

        }
        closeResource(c);

        return lMunicipios;


    }

    @Override
    public List<Municipio> lstCiudades(int idContinente) {
        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_CIUDADES, camposCiu, bdDimeElTiempo.COL_ID_CONTINENTES  + " = ? ",
                new String[] { Integer.toString(idContinente) }, null,
                null, BdDimeElTiempo.COL_NOMBRE_CIUDAD);

        List<Municipio> lCiudades = new ArrayList<>();
        if (c != null) {
            Municipio municipio;
            while (c.moveToNext()) {
                municipio = new Municipio();
                municipio.setIdProvincia(c.getInt(0));
                municipio.setIdMunicipio(c.getInt(1));
                municipio.setMunicipio(c.getString(2));
                municipio.setUrl(c.getString(3));
                lCiudades.add(municipio);
            }

        }
        closeResource(c);

        return lCiudades;
    }

    private static void closeResource(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }


    @Override
    public List<Favoritos> lstFavoritos() {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_FAVORITOS, camposFav, null, null, null,
                null, BdDimeElTiempo.COL_NOMBRE_MUNICIPIO);

        List<Favoritos> lFavoritos = new ArrayList<>();
        if (c != null) {
            Favoritos favoritos;
            while (c.moveToNext()) {
                favoritos = new Favoritos();
                favoritos.setIdFavoritos(c.getInt(0));
                favoritos.setProvincia(c.getString(1));
                favoritos.setMunicipio(c.getString(2));
                favoritos.setUrl(c.getString(3));
                lFavoritos.add(favoritos);
            }

        }
        closeResource(c);

        return lFavoritos;


    }

    @Override
    public Favoritos buscarFavorito(int idFavorito) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_FAVORITOS, camposFav,
                bdDimeElTiempo.COL_ID_FAVORITOS + " = ? ",
                new String[]{Integer.toString(idFavorito)}, null,
                null, BdDimeElTiempo.COL_NOMBRE_MUNICIPIO);

        Favoritos  favoritos = new Favoritos();
        if (c != null) {
            if (c.moveToNext()) {
                favoritos.setIdFavoritos(c.getInt(0));
                favoritos.setProvincia(c.getString(1));
                favoritos.setMunicipio(c.getString(2));
                favoritos.setUrl(c.getString(3));
            }
        }
        closeResource(c);

        return favoritos;
    }

    @Override
    public Favoritos buscarFavorito(String provincia, String municipio) {
        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_FAVORITOS, camposFav,
                bdDimeElTiempo.COL_NOMBRE_PROVINCIA + " = ? AND " + bdDimeElTiempo.COL_NOMBRE_MUNICIPIO  + " = ? ",
                new String[]{provincia,municipio}, null,
                null, BdDimeElTiempo.COL_NOMBRE_MUNICIPIO);

        Favoritos  favoritos = new Favoritos();
        if (c != null) {
            if (c.moveToNext()) {
                favoritos.setIdFavoritos(c.getInt(0));
                favoritos.setProvincia(c.getString(1));
                favoritos.setMunicipio(c.getString(2));
                favoritos.setUrl(c.getString(3));
            }
        }
        closeResource(c);

        return favoritos;
    }

    @Override
    public boolean isFavorito(String provincia, String municipio) {
        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_FAVORITOS, camposFav,
                bdDimeElTiempo.COL_NOMBRE_PROVINCIA + " = ? AND " + bdDimeElTiempo.COL_NOMBRE_MUNICIPIO  + " = ? ",
                new String[]{provincia,municipio}, null,
                null, BdDimeElTiempo.COL_NOMBRE_MUNICIPIO);

        if (c != null) {
            if (c.moveToNext()) {
                return true;
            }
        }
        closeResource(c);

        return false;
    }

    @Override
    public long insertarFavorito(String provincia, String municipio, String url) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(bdDimeElTiempo.COL_NOMBRE_PROVINCIA, provincia);
        values.put(bdDimeElTiempo.COL_NOMBRE_MUNICIPIO, municipio);
        values.put(bdDimeElTiempo.COL_URL, url);

        long id = 0 ;
        if (!isFavorito(provincia, municipio)) {
            id = db.insert(BdDimeElTiempo.TABLA_FAVORITOS, null, values);
        }

        return id;

    }

    @Override
    public void eliminaFavorito(int idFavorito) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        db.delete(BdDimeElTiempo.TABLA_FAVORITOS, BdDimeElTiempo.COL_ID_FAVORITOS + " = ?",
                new String[] { String.valueOf(idFavorito) });

    }
}
