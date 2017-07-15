package dimogdroid.com.entidades;

/**
 * Created by DDAVILA on 20/06/2017.
 */

public class Ciudad {

    private int idPais;
    private int idCiudad;
    private String ciudad;

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String toString(){
        return ciudad;
    }
}
