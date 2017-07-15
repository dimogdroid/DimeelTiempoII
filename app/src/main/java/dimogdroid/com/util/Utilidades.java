package dimogdroid.com.util;

/**
 * Created by DDAVILA on 20/06/2017.
 */

public class Utilidades {

    public String QuitarEtiquetas(String enviada) {
        String limpia = "";

        try {

            if (enviada == null) {
                return null;
            }

            limpia = enviada;

            // String limpia=sucia;
            if (limpia.contains("<p>")) { // grados del satelite
                limpia = limpia.replaceAll("<p>", "");
            }

            if (limpia.contains("ºC")) { // grados del satelite
                limpia = limpia.replaceAll("ºC", "grados");
            }

            if (limpia.contains("km/h")) { // grados del satelite
                limpia = limpia.replaceAll("km/h", "kilometros hora");
            }

            if (limpia.contains("</p>")) { // grados del satelite
                limpia = limpia.replaceAll("</p>", "");
            }
            if (limpia.contains("<br>")) { // grados del satelite
                limpia = limpia.replaceAll("<br>", "");
            }
            if (limpia.contains("<br/>")) { // grados del satelite
                limpia = limpia.replaceAll("<br/>", "");
            }
            if (limpia.contains("<\\t>")) { // grados del satelite
                limpia = limpia.replaceAll("<\\t>", "");
            }
            if (limpia.contains("null'")) { // grados del satelite
                limpia = limpia.replaceAll("null", "");
            }
            // if (limpia.indexOf(",") >= 0) { // grados del satelite
            // limpia = limpia.replaceAll(",", "");
            // }
            if (limpia.contains("</div>")) { // grados del satelite
                limpia = limpia.replaceAll("</div>", "");
            }

            if (limpia.contains("<\\n>")) { // grados del satelite
                limpia = limpia.replaceAll("<\\n>", "");
            }
            if (limpia.contains("<\\t>")) { // grados del satelite
                limpia = limpia.replaceAll("<\\t>", "");
            }

            if (limpia.contains("<strong>")) { // grados del satelite
                limpia = limpia.replaceAll("<strong>", "");
            }
            if (limpia.contains("</strong>")) { // grados del satelite
                limpia = limpia.replaceAll("</strong>", "");
            }
            if (limpia.contains("<dl")) { // grados del satelite
                limpia = limpia.replaceAll("<dl", "");
            }
            if (limpia.contains("<dd>")) { // grados del satelite
                limpia = limpia.replaceAll("<dd>", "");
            }
            if (limpia.contains("-")) { // grados del satelite
                limpia = limpia.replaceAll("-", " ");
            }

            if (limpia.contains("class=\"marginTop0")) { // grados
                // del
                // satelite
                limpia = limpia.replaceAll("class=\"marginTop0", "");
            }

            if (limpia.contains("marginBottom0\">")) { // grados
                // del
                // satelite
                limpia = limpia.replaceAll("marginBottom0\">", "");
            }

            if (limpia.contains("class=\"margintop0")) {
                limpia = limpia.replaceAll("class=\"margintop0", "");
            }

            if (limpia.contains("marginbottom0\">")) {
                limpia = limpia.replaceAll("marginbottom0\">", "");
            }

            if (limpia.contains("</dd>")) { // grados del satelite
                limpia = limpia.replaceAll("</dd>", "");
            }
            if (limpia.contains("</dl>")) { // grados del satelite
                limpia = limpia.replaceAll("</dl>", ". ");
            }
            if (limpia.contains("</td>")) { // grados del satelite
                limpia = limpia.replaceAll("</td>", ".");
            }
            if (limpia.contains("<font")) { // grados del satelite
                limpia = limpia.replaceAll("<font", "");
            }
            if (limpia.contains("color=\"#ab3000\">")) { // grados del
                // satelite
                limpia = limpia.replaceAll("color=\"#ab3000\">", "");
            }
            if (limpia.contains("</font>")) { // grados del satelite
                limpia = limpia.replaceAll("</font>", "");
            }

            if (limpia.contains("/")) { // grados del satelite
                limpia = limpia.replaceAll("/", ",");
            }

        } catch (Exception ex) {
            CustomLog.error("limpiarString", ex.getMessage());
        }

        return limpia;
    }
}
