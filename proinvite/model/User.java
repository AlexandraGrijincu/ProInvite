package com.proinvite.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity //Spune lui JPA ca aceasta clasa reprezinta o entitate si trebuie mapata la un tabel din baza de date
@Table(name="users") //Specifica numele tabelului din baza de date
public class User {

    @Id //Marcheaza campul ca fiind cheia primara
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Specifica faptul ca cheia primara este generata automat
    private Long id;

    @Column(name="username",unique=true,length=64, nullable=false)//Specifica detaliile unei coloane(nume,daca e unica,daca e null)
    private String username;

    @Column(name="email",unique=true,length=64, nullable=false)
    private String email;

    @Column(name="password",nullable=false,length=512)
    private String password;

    @Column(name="data")
    private Timestamp lastUpdate;

    //Constructor fara argumente
    public User(){
    }

    //Constructor cu argumente
    public User(String u,String e,String p,Timestamp lu){
        this.username=u;
        this.email=e;
        this.password=p;
        this.lastUpdate=lu;
    }

    //Getteri
    public Long getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    //Setteri
    public void setId(Long i){
        this.id=i;
    }
    public void setUsername(String u){
        this.username=u;
    }
    public void setEmail(String e){
        this.email=e;
    }
    public void setPassword(String p){
        this.password=p;
    }
    public void setLastUpdate(Timestamp lu){
        this.lastUpdate=lu;
    }
}
