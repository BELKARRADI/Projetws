package com.example.projetws;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.projetws.beans.Etudiant;

import java.util.Collection;
import java.util.List;

public class EtudiantAdapter extends BaseAdapter {

    Context ctx;
    private List<Etudiant> etudiants;
    private LayoutInflater inflater;



    public EtudiantAdapter(Activity activity, List<Etudiant> etudiants) {
        this.etudiants = etudiants;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void clear() {
        etudiants.clear();
        notifyDataSetChanged();
    }

    public void addAll(Collection<Etudiant> newStudents) {
        etudiants.addAll(newStudents);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public Etudiant getItem(int i) {
        return etudiants.get(i);
    }

    @Override
    public long getItemId(int i) {
        return etudiants.get(i).getId();
    }

    @Override

    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(R.layout.item, null);
        TextView title = view.findViewById(R.id.nom);
        TextView prenom = view.findViewById(R.id.prenom);
        TextView ville = view.findViewById(R.id.ville);
        TextView sexe = view.findViewById(R.id.sexe);

        TextView idP = view.findViewById(R.id.idP);
        idP.setText(etudiants.get(i).getId()+" ");
        title.setText(etudiants.get(i).getNom());
        prenom.setText(etudiants.get(i).getPrenom());
        ville.setText(etudiants.get(i).getVille());
        sexe.setText(etudiants.get(i).getSexe());

        return view;
    }
}

