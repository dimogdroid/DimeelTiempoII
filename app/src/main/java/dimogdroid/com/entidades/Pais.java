package dimogdroid.com.entidades;

/**
 * Created by DDAVILA on 20/06/2017.
 */

public class Pais {

    private int idContinente;
    private int idPais;
    private String pais;

    public int getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(int idContinente) {
        this.idContinente = idContinente;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String toString(){
        return pais;
    }
}
