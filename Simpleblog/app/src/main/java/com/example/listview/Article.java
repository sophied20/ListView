package com.example.listview;

public class Article {
    int id;
    String titre;
    String contenu;
    String auteur;
    String date;

    public Article(int id, String titre, String contenu, String auteur, String date){
        this.id=id;
        this.titre=titre;
        this.contenu=contenu;
        this.auteur=auteur;
        this.date=date;
    }

    public String getTitre(){return titre;}
    public int getId(){return id;}
    public String getContenu(){return contenu;}
    public String getAuteur(){return auteur;}
    public String getDate(){return  date;}

    public void setTitre(String titre){
        this.titre = titre;
    }
    public void setId(int id){this.id=id;}
    public void setContenu(String contenu){
        this.contenu = contenu;
    }
    public void setAuteur(String auteur){
        this.auteur = auteur;
    }
    public void setDate(String date){
        this.date = date;
    }

}
