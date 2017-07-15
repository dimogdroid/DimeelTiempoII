package dimogdroid.com.entidades;

/**
 * Created by ddavila on 09/05/2016.
 */
public class Favoritos {

    private int idFavoritos;
    private String provincia;
    private String municipio;
    private String url;

    public Favoritos(int idFavoritos, String provincia, String municipio, String url) {
        this.idFavoritos = idFavoritos;
        this.provincia = provincia;
        this.municipio = municipio;
        this.url = url;
    }

    public Favoritos() {
    }

    public int getIdFavoritos() {
        return idFavoritos;
    }

    public void setIdFavoritos(int idFavoritos) {
        this.idFavoritos = idFavoritos;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return municipio + " (" + provincia + ")";
    }
}
