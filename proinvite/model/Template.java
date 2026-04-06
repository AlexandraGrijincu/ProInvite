package com.proinvite.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity //Spune lui JPA ca aceasta clasa reprezinta o entitate si trebuie mapata la un tabel din baza de date
@Table(name="templates") //Specifica numele tabelului din baza de date
public class Template {

    @Id //Marcheaza campul ca fiind cheia primara
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Specifica faptul ca cheia primara este generata automat
    private Long id;

    @Column(name="name",unique=true,length=64, nullable=false)//Specifica detaliile unei coloane(nume,daca e unica,daca e null)
    private String name;

    @Column(name="type",nullable=false,length=32)
    private String type;

    @Column(name="preview_url",unique=true,length=1000, nullable=false)
    private String previewUrl;

    @Column(name="json_path",unique=true,length=1000, nullable=false)
    private String jsonPath;

    //Constructor fara argumente
    public Template(){
    }

    //Constructor cu argumente
    public Template(String n,String t,String pu,String jp){
        this.name=n;
        this.type=t;
        this.previewUrl=pu;
        this.jsonPath=jp;
    }

    //Getteri
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getPreviewUrl(){
        return previewUrl;
    }
    public String getJsonPath(){
        return jsonPath;
    }

    //Setteri
    public void setId(Long i){
        this.id=i;
    }
    public void setName(String n){
        this.name=n;
    }
    public void setType(String t){
        this.type=t;
    }
    public void setPreviewUrl(String pu){
        this.previewUrl=pu;
    }
    public void setJsonPath(String jp){
        this.jsonPath=jp;
    }
}
