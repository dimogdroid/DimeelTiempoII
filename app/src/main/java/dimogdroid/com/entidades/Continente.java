package dimogdroid.com.entidades;

/**
 * Created by DDAVILA on 20/06/2017.
 */

public class Continente {

    private int idContinente;
    private String continente;

    public int getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(int idContinente) {
        this.idContinente = idContinente;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    public String toString(){
        return continente;
    }
}
