package dimogdroid.com.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dimogdroid.com.entidades.Ciudad;
import dimogdroid.com.entidades.Continente;
import dimogdroid.com.entidades.Favoritos;
import dimogdroid.com.entidades.Municipio;
import dimogdroid.com.entidades.Pais;
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

    String[] camposCont = new String[] {bdDimeElTiempo.COL_ID_CONTINENTE,
                                        bdDimeElTiempo.COL_NOMBRE_CONTINENTE};

    String[] camposPais = new String[] {bdDimeElTiempo.COL_ID_CONTINENTE,
            bdDimeElTiempo.COL_ID_PAIS,
            bdDimeElTiempo.COL_NOMBRE_PAIS};

    String[] camposMun = new String[] {bdDimeElTiempo.COL_ID_PROVINCIA,
                                       bdDimeElTiempo.COL_ID_MUNICIPIO,
                                       bdDimeElTiempo.COL_NOMBRE_MUNICIPIO,
                                       bdDimeElTiempo.COL_URL};

    String[] camposCiu = new String[] {bdDimeElTiempo.COL_ID_PAIS,
            bdDimeElTiempo.COL_ID_CIUDAD,
            bdDimeElTiempo.COL_NOMBRE_CIUDAD,
            bdDimeElTiempo.COL_URL};


    String[] camposFav = new String[] {bdDimeElTiempo.COL_ID_FAVORITOS,
                                       bdDimeElTiempo.COL_NOMBRE_PARTEUNO,
                                       bdDimeElTiempo.COL_NOMBRE_PARTEDOS,
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
    public List<Continente> lstContinentes() {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_CONTINENTES, camposCont, null, null, null,
                null, BdDimeElTiempo.COL_ID_CONTINENTE);

        List<Continente> lContinente = new ArrayList<>();
        if (c != null) {
            Continente continente;
            while (c.moveToNext()) {
                continente = new Continente();
                continente.setIdContinente(c.getInt(0));
                continente.setContinente(c.getString(1));
                lContinente.add(continente);
            }

        }
        closeResource(c);

        return lContinente;
    }

    @Override
    public List<Pais> lstPaises(int idContinente) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_PAISES, camposPais, bdDimeElTiempo.COL_ID_CONTINENTE  + " = ? ",
                new String[] { Integer.toString(idContinente) }, null,
                null, BdDimeElTiempo.COL_NOMBRE_PAIS);

        List<Pais> lPaises = new ArrayList<>();
        if (c != null) {
            Pais pais;
            while (c.moveToNext()) {
                pais = new Pais();
                pais.setIdContinente(c.getInt(0));
                pais.setIdPais(c.getInt(1));
                pais.setPais(c.getString(2));

                lPaises.add(pais);
            }

        }
        closeResource(c);

        return lPaises;
    }

    @Override
    public String buscarProvincia(int id) {

        Provincia provincia = new Provincia();
        Cursor c = null;
        try {
            SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

            c = db.query(BdDimeElTiempo.TABLA_PROVINCIAS, camposProv,
                    bdDimeElTiempo.COL_ID_PROVINCIA + " = ? ",
                    new String[]{Integer.toString(id)}, null,
                    null, null);


            if (c != null) {
                if (c.moveToNext()) {
                    provincia.setIdProvincia(c.getInt(0));
                    provincia.setProvincia(c.getString(1));
                }
            }
        }catch(Exception e){

        }

        closeResource(c);

        return provincia.getProvincia();

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
                municipio.setProvincia(buscarProvincia(c.getInt(0)));
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
    public List<Ciudad> lstCiudades(int idPais) {
        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_CIUDADES, camposCiu, bdDimeElTiempo.COL_ID_PAIS  + " = ? ",
                new String[] { Integer.toString(idPais) }, null,
                null, BdDimeElTiempo.COL_NOMBRE_CIUDAD);

        List<Ciudad> lCiudades = new ArrayList<>();
        if (c != null) {
            Ciudad ciudad;
            while (c.moveToNext()) {
                ciudad = new Ciudad();
                ciudad.setIdPais(c.getInt(0));
                ciudad.setIdCiudad(c.getInt(1));
                ciudad.setCiudad(c.getString(2));

                lCiudades.add(ciudad);
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
                null, BdDimeElTiempo.COL_NOMBRE_PARTEDOS);

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
                null, BdDimeElTiempo.COL_NOMBRE_PARTEDOS);

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
                bdDimeElTiempo.COL_NOMBRE_PARTEUNO + " = ? AND " + bdDimeElTiempo.COL_NOMBRE_PARTEDOS  + " = ? ",
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
    public boolean isFavorito(Favoritos favorito) {
        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        Cursor c = db.query(BdDimeElTiempo.TABLA_FAVORITOS, camposFav,
                bdDimeElTiempo.COL_NOMBRE_PARTEUNO + " = ? AND " + bdDimeElTiempo.COL_NOMBRE_PARTEDOS  + " = ? ",
                new String[]{favorito.getProvincia(),favorito.getMunicipio()}, null,
                null, BdDimeElTiempo.COL_NOMBRE_PARTEDOS);

        if (c != null) {
            if (c.moveToNext()) {
                return true;
            }
        }
        closeResource(c);

        return false;
    }

    @Override
    public Favoritos insertarFavorito(Favoritos favorito) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(bdDimeElTiempo.COL_NOMBRE_PARTEUNO, favorito.getProvincia());
        values.put(bdDimeElTiempo.COL_NOMBRE_PARTEDOS, favorito.getMunicipio());
        values.put(bdDimeElTiempo.COL_URL, favorito.getUrl());

        long id = 0 ;
        if (!isFavorito(favorito)) {
            id = db.insert(BdDimeElTiempo.TABLA_FAVORITOS, null, values);
            return buscarFavorito((int)id);
        }else{
            return null;
        }
    }

    @Override
    public void eliminaFavorito(int idFavorito) {

        SQLiteDatabase db = bdDimeElTiempo.getWritableDatabase();

        db.delete(BdDimeElTiempo.TABLA_FAVORITOS, BdDimeElTiempo.COL_ID_FAVORITOS + " = ?",
                new String[] { String.valueOf(idFavorito) });

    }
}
