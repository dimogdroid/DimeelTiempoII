package dimogdroid.com.dimeeltiempo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by ddavila on 17/05/2016.
 */
public class EntryReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

       Toast.makeText(context, "Time is up!!!!.",
              Toast.LENGTH_LONG).show();

        try {
            Bundle bundle = intent.getExtras();
            String provincia = bundle.getString("provincia");
            String municipio = bundle.getString("municipio");
            String urlactiva = bundle.getString("urlactiva");

            // Your activity name
            Intent newIntent = new Intent(context, MainActivity.class);
            newIntent.putExtra("provincia", provincia);
            newIntent.putExtra("municipio", municipio);
            newIntent.putExtra("urlactiva", urlactiva);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
