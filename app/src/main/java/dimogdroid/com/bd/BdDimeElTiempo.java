package dimogdroid.com.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ddavila on 09/05/2016.
 */
public class BdDimeElTiempo  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dimeeltiempo.db";
    private static final int DATABASE_VERSION = 1;

    public final static String TABLA_PROVINCIAS = "provincias";
    public final static String TABLA_MUNICIPIOS = "municipios";
    public final static String TABLA_CONTINENTES = "continentes";
    public final static String TABLA_PAISES = "paises";
    public final static String TABLA_CIUDADES = "ciudades";
    public final static String TABLA_FAVORITOS = "favoritos";

    public static final String COL_ID ="id";
    public static final String COL_ID_PROVINCIA ="idProvincia";
    public static final String COL_ID_MUNICIPIO ="idMunicipio";
    public static final String COL_ID_FAVORITOS ="idFavoritos";
    public static final String COL_ID_CONTINENTE ="idContinentes";
    public static final String COL_ID_PAIS ="idPaises";
    public static final String COL_ID_CIUDAD ="idCiudades";
    public static final String COL_NOMBRE_PROVINCIA = "provincia";
    public static final String COL_NOMBRE_MUNICIPIO = "municipio";
    public static final String COL_NOMBRE_CONTINENTE = "continente";
    public static final String COL_NOMBRE_PAIS = "pais";
    public static final String COL_NOMBRE_CIUDAD = "ciudad";
    public static final String COL_NOMBRE_PARTEUNO = "parteUno";
    public static final String COL_NOMBRE_PARTEDOS = "parteDos";
    public static final String COL_URL = "url";

    public BdDimeElTiempo(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLA_PROVINCIAS + " ("
                + COL_ID_PROVINCIA        + " INTEGER PRIMARY KEY, "
                + COL_NOMBRE_PROVINCIA    + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_MUNICIPIOS + " ("
                + COL_ID_MUNICIPIO        + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_PROVINCIA        + " INTEGER, "
                + COL_NOMBRE_MUNICIPIO    + " TEXT, "
                + COL_URL                 + " TEXT );";

        db.execSQL(sql);


        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_CONTINENTES + " ("
                + COL_ID_CONTINENTE        + " INTEGER PRIMARY KEY, "
                + COL_NOMBRE_CONTINENTE    + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_PAISES + " ("
                + COL_ID_CONTINENTE   + " INTEGER, "
                + COL_ID_PAIS        + " INTEGER PRIMARY KEY, "
                + COL_NOMBRE_PAIS    + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_CIUDADES + " ("
                + COL_ID_PAIS           + " INTEGER, "
                + COL_ID_CIUDAD         + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
                + COL_NOMBRE_CIUDAD       + " TEXT, "
                + COL_URL                 + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_FAVORITOS + " ("
                + COL_ID_FAVORITOS        + " INTEGER PRIMARY KEY, "
                + COL_NOMBRE_PARTEUNO     + " TEXT, "
                + COL_NOMBRE_PARTEDOS    + " TEXT, "
                + COL_URL                 + " TEXT );";

        db.execSQL(sql);

        insertarProvincias(db);

        insertarMunicipios(db);

        insertarContinentes(db);

        insertarPaises(db);

        insertarCiudades(db);




    }

    private void insertarPaises(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_PAISES + " (" + COL_ID_CONTINENTE + ", " + COL_ID_PAIS + ", " + COL_NOMBRE_PAIS + ") VALUES (1, 1,'REINO UNIDO')";
        db.execSQL(sql);

        sql = "INSERT INTO  " + TABLA_PAISES + " (" + COL_ID_CONTINENTE + ", " + COL_ID_PAIS + ", " + COL_NOMBRE_PAIS + ") VALUES (1, 2,'PORTUGAL')";
        db.execSQL(sql);

    }

    private void insertarCiudades(SQLiteDatabase db) {
        String sql = null;

        sql = "INSERT INTO  " + TABLA_CIUDADES + " (" + COL_ID_PAIS + ", " +
                COL_ID_CIUDAD + ", " + COL_NOMBRE_CIUDAD + ", " + COL_URL + ") VALUES (1, 1, 'Londres','http://www.tiempo.es/reino_unido-londres-gb0ki0101.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CIUDADES + " (" + COL_ID_PAIS + ", " +
                COL_ID_CIUDAD + ", " + COL_NOMBRE_CIUDAD + ", " + COL_URL + ") VALUES (2, 2, 'Lisboa','http://www.tiempo.es/portugal-lisboa-pt0li0005.html')";
        db.execSQL(sql);

    }

    private void insertarMunicipios(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (1,'A Coruña','http://www.tiempo.es/a_coruna-a_coruna-es0lc0031.html')";
        db.execSQL(sql);

        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (2,'Vitoria-Gasteiz','http://www.tiempo.es/alava-vitoria_gasteiz-es0av0046.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (2,'Llodio','http://www.tiempo.es/alava-llodio-es0av0032.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (2,'Amurrio','http://www.tiempo.es/alava-amurrio-es0av0002.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (2,'Salvatierra','http://www.tiempo.es/alava-salvatierra-es0av0040.html')";
        db.execSQL(sql);


        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
              COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (42,'Sevilla','http://www.tiempo.es/sevilla-sevilla-es0se0093.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (42,'Dos Hermanas','http://www.tiempo.es/sevilla-dos_hermanas-es0se0040.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (42,'Castilleja de la Cuesta','http://www.tiempo.es/sevilla-castilleja_de_la_cuesta-es0se0030.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (42,'Alcalá de Guadaira','http://www.tiempo.es/sevilla-alcala_de_guadaira-es0se0004.html')";
        db.execSQL(sql);


    }

    private void insertarContinentes(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTE + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (1,'Europa')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTE + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (2,'América del Sur')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTE + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (3,'América del Norte')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTE + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (4,'África')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTE + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (5,'Asia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTE + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (6,'Oceanía')";
        db.execSQL(sql);
    }

    private void insertarProvincias(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (1,'A Coruña')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (2,'Álava')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (3,'Albacete')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (4,'Alicante')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (5,'Almería')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (6,'Asturias')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (7,'Ávila')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (8,'Badajoz')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (9,'Barcelona')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (10,'Burgos')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (11,'Cáceres')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (12,'Cádiz')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (13,'Cantabria')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (14,'Castellón')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (15,'Ceuta')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (16,'Ciudad Real')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (17,'Córdoba')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (18,'Cuenca')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (19,'Girona')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (20,'Granada')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (21,'Guadalajara')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (22,'Guipúzcoa')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (23,'Huelva')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (24,'Huesca')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (25,'Islas Baleares')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (26,'Islas Canarias')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (27,'Jaén')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (28,'La Rioja')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (29,'León')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (30,'Lérida')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (31,'Lugo')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (32,'Madrid')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (33,'Málaga')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (34,'Melilla')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (35,'Murcia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (36,'Navarra')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (37,'Ourense')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (38,'Palencia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (39,'Pontevedra')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (40,'Salamanca')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (41,'Segovia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (42,'Sevilla')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (43,'Soria')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (44,'Tarragona')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (45,'Teruel')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (46,'Toledo')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (47,'Valencia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (48,'Valladolid')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (49,'Vizcaya')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (50,'Zamora')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (51,'Zaragoza')";
        db.execSQL(sql);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

