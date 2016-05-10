package ovh.dessert.tpe.repertoiredestagesm2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ovh.dessert.tpe.repertoiredestagesm2.StagesDAO;
import ovh.dessert.tpe.repertoiredestagesm2.entities.Entreprise;

/**
 * Created by totorolepacha on 10/05/16.
 */
public class EntrepriseAdapter extends BaseAdapter {

    private List<Entreprise> entreprises;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    public EntrepriseAdapter(Context context, List<Entreprise> entreprises) {
        this.mContext = context;
        this.entreprises = entreprises;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public EntrepriseAdapter(Context context) {
        try {
            this.entreprises = StagesDAO.getInstance(context).getAllEntreprises();
        }catch(Exception e) {
            this.entreprises = new ArrayList<>();
        }finally {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
        }
    }

    @Override
    public int getCount() {
        return this.entreprises.size();
    }

    @Override
    public Object getItem(int position) {
        return this.entreprises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
