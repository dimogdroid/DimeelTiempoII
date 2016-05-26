package dimogdroid.com.bd;

import java.util.List;

import dimogdroid.com.entidades.Favoritos;
import dimogdroid.com.entidades.Municipio;
import dimogdroid.com.entidades.Provincia;

/**
 * Created by ddavila on 09/05/2016.
 */
public interface  ProveedorBd {

    List<Provincia> lstProvincias();

    List<Provincia> lstContinentes();

    List<Municipio> lstMunicipios (int idProvincia);

    List<Municipio> lstCiudades (int idContinente);

    List<Favoritos> lstFavoritos();

    Favoritos buscarFavorito(int idFavorito);

    Favoritos buscarFavorito(String provincia, String municipio);

    boolean isFavorito(String provincia, String municipio);

    long insertarFavorito(String Provincia, String Municipio, String url);

    void eliminaFavorito(int idFavorito);


}
