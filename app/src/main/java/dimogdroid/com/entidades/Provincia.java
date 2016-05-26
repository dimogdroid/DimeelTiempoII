package dimogdroid.com.entidades;

/**
 * Created by ddavila on 09/05/2016.
 */
public class Provincia {

    private int idProvincia;
    private String provincia;

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String toString(){
        return provincia;
    }
}
