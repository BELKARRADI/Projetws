package com.example.projetws.Services;

import com.example.projetws.Dao.IDao;
import com.example.projetws.beans.Etudiant;

import java.util.ArrayList;
import java.util.List;

public class EtudiantService implements IDao<Etudiant> {

    private List<Etudiant> etudiants;

    public EtudiantService() {
        this.etudiants = new ArrayList<>();

    }




    @Override
    public boolean create(Etudiant o) {
        return etudiants.add(o);
    }

    @Override
    public boolean update(Etudiant o) {
        for(Etudiant p : etudiants){
            if(p.getId() == o.getId()) {
                p.setNom(o.getNom());
                p.setPrenom(o.getPrenom());
                p.setVille(o.getVille());
                p.setSexe(o.getSexe());

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Etudiant o) {
        return etudiants.remove(o);
    }

    @Override
    public Etudiant findById(int id) {


        return null;
    }

    @Override
    public List<Etudiant> findAll() {
        return etudiants;
    }
}
