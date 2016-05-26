package dimogdroid.com.entidades;

/**
 * Created by ddavila on 23/05/2016.
 */
public class Alarma {

    private int id;
    private  String provincia;
    private  String municipio;
    private  String urlactiva;
    private  String hora;
    private  String minutos;
    private String dias;  //Cadena con valores L,M,X,J,V,S,D

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinutos() {
        return minutos;
    }

    public void setMinutos(String minutos) {
        this.minutos = minutos;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUrlactiva() {
        return urlactiva;
    }

    public void setUrlactiva(String urlactiva) {
        this.urlactiva = urlactiva;
    }
}
