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
    public final static String TABLA_CIUDADES = "ciudades";
    public final static String TABLA_FAVORITOS = "favoritos";

    public static final String COL_ID ="id";
    public static final String COL_ID_PROVINCIA ="idProvincia";
    public static final String COL_ID_MUNICIPIO ="idMunicipio";
    public static final String COL_ID_FAVORITOS ="idFavoritos";
    public static final String COL_ID_CONTINENTES ="idContinentes";
    public static final String COL_ID_CIUDADES ="idCiudades";
    public static final String COL_NOMBRE_PROVINCIA = "provincia";
    public static final String COL_NOMBRE_MUNICIPIO = "municipio";
    public static final String COL_NOMBRE_CONTINENTE = "continente";
    public static final String COL_NOMBRE_CIUDAD = "ciudad";
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

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_CONTINENTES + " ("
                + COL_ID_CONTINENTES        + " INTEGER PRIMARY KEY, "
                + COL_NOMBRE_CONTINENTE    + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_MUNICIPIOS + " ("
                + COL_ID_MUNICIPIO        + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_PROVINCIA        + " INTEGER, "
                + COL_NOMBRE_MUNICIPIO    + " TEXT, "
                + COL_URL                 + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_CIUDADES + " ("
                + COL_ID_CIUDADES         + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_CONTINENTES      + " INTEGER, "
                + COL_NOMBRE_CIUDAD       + " TEXT, "
                + COL_URL                 + " TEXT );";

        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + TABLA_FAVORITOS + " ("
                + COL_ID_FAVORITOS        + " INTEGER PRIMARY KEY, "
                + COL_NOMBRE_PROVINCIA     + " TEXT, "
                + COL_NOMBRE_MUNICIPIO    + " TEXT, "
                + COL_URL                 + " TEXT );";

        db.execSQL(sql);

        insertarProvincias(db);

        insertarContinentes(db);

        insertarMunicipios(db);


    }

    private void insertarMunicipios(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (1,'Vitoria-Gasteiz','http://www.tiempo.es/alava-vitoria_gasteiz-es0av0046.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (1,'Llodio','http://www.tiempo.es/alava-llodio-es0av0032.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (1,'Amurrio','http://www.tiempo.es/alava-amurrio-es0av0002.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (1,'Salvatierra','http://www.tiempo.es/alava-salvatierra-es0av0040.html')";
        db.execSQL(sql);


        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
              COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (41,'Sevilla','http://www.tiempo.es/sevilla-sevilla-es0se0093.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (41,'Dos Hermanas','http://www.tiempo.es/sevilla-dos_hermanas-es0se0040.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (41,'Castilleja de la Cuesta','http://www.tiempo.es/sevilla-castilleja_de_la_cuesta-es0se0030.html')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_MUNICIPIOS + " (" + COL_ID_PROVINCIA + ", " +
                COL_NOMBRE_MUNICIPIO + ", " + COL_URL + ") VALUES (41,'Alcalá de Guadaira','http://www.tiempo.es/sevilla-alcala_de_guadaira-es0se0004.html')";
        db.execSQL(sql);


    }

    private void insertarContinentes(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTES + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (1,'Europa')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTES + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (2,'América del Sur')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTES + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (3,'América del Norte')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTES + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (4,'África')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTES + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (5,'Asia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_CONTINENTES + " (" + COL_ID_CONTINENTES + ", " + COL_NOMBRE_CONTINENTE + ") VALUES (6,'Oceanía')";
        db.execSQL(sql);
    }

    private void insertarProvincias(SQLiteDatabase db) {

        String sql = null;

        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (1,'Álava')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (2,'Albacete')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (3,'Alicante')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (4,'Almería')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (5,'Ávila')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (6,'Badajoz')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (7,'Baleares')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (8,'Barcelona')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (9,'Burgos')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (10,'Cáceres')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (11,'Cádiz')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (12,'Castellón')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (13,'Ciudad Real')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (14,'Córdoba')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (15,'Coruña')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (16,'Cuenca')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (17,'Gerona')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (18,'Granada')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (19,'Guadalajara')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (20,'Guipúzcoa')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (21,'Huelva')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (22,'Huesca')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (23,'Jaén')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (24,'León')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (25,'Lérida')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (26,'La Rioja')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (27,'Lugo')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (28,'Madrid')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (29,'Málaga')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (30,'Murcia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (31,'Navarra')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (32,'Orense')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (33,'Asturias')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (34,'Palencia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (35,'Las Palmas')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (36,'Pontevedra')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (37,'Salamanca')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (38,'Santa Cruz de Tenerife')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (39,'Cantabria')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (40,'Segovia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (41,'Sevilla')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (42,'Soria')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (43,'Tarragona')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (44,'Teruel')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (45,'Toledo')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (46,'Valencia')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (47,'Valladolid')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (48,'Vizcaya')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (49,'Zamora')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (50,'Zaragoza')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (51,'Ceuta')";
        db.execSQL(sql);
        sql = "INSERT INTO  " + TABLA_PROVINCIAS + " (" + COL_ID_PROVINCIA + ", " + COL_NOMBRE_PROVINCIA + ") VALUES (52,'Melilla')";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

