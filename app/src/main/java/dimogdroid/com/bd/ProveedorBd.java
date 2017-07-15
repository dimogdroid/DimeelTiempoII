package dimogdroid.com.bd;

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
public interface  ProveedorBd {

    List<Provincia> lstProvincias();

    String buscarProvincia(int id);

    List<Municipio> lstMunicipios(int idProvincia);

    List<Continente> lstContinentes();

    List<Pais> lstPaises(int idContinente);

    List<Ciudad> lstCiudades(int idPais);


    List<Favoritos> lstFavoritos();

    Favoritos buscarFavorito(int idFavorito);

    Favoritos buscarFavorito(String provincia, String municipio);

    boolean isFavorito(Favoritos favorito);

    Favoritos insertarFavorito(Favoritos favorito);

    void eliminaFavorito(int idFavorito);


}
