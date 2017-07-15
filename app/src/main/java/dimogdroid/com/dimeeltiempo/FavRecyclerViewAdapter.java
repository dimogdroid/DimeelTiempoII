package dimogdroid.com.dimeeltiempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dimogdroid.com.entidades.Favoritos;

/**
 * Created by DDAVILA on 26/06/2017.
 */

public class FavRecyclerViewAdapter extends RecyclerView.Adapter<FavRecyclerViewAdapter.ViewHolder> {

    private List<Favoritos> storeList;
    private Context context;

    public FavRecyclerViewAdapter(List<Favoritos> storeLst, Context ctx){
        storeList = storeLst;
        context = ctx;

    }

    @Override
    public FavRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoritos_row, parent, false);

        FavRecyclerViewAdapter.ViewHolder viewHolder = new FavRecyclerViewAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavRecyclerViewAdapter.ViewHolder holder, int position) {
        Favoritos favorito = storeList.get(position);

        holder.provincia.setText(favorito.getProvincia());
        holder.municipio.setText(favorito.getMunicipio());
        holder.url.setText(favorito.getUrl());
        holder.idfavorito.setText(Integer.toString(favorito.getIdFavoritos()));

    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // public ImageView delImg;
        public TextView provincia;
        public TextView municipio;
        public TextView url;
        public TextView idfavorito;


        public ViewHolder(View view) {
            super(view);
            //  delImg = (ImageView) view.findViewById(R.id.storeImg);
            provincia = (TextView) view.findViewById(R.id.txtprovfav);
            municipio = (TextView) view.findViewById(R.id.txtmunicipiofav);
            url = (TextView) view.findViewById(R.id.urlfav);
            idfavorito = (TextView) view.findViewById(R.id.idfavorito);

           // view.setOnClickListener(this);
        }
       /* @Override
        public void onClick(View v){

            Context context = v.getContext();

            Toast.makeText(context, "View latest coupons for store at "+ getAdapterPosition(),
                    Toast.LENGTH_LONG).show();
        }*/
    }
}
