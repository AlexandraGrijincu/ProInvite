package com.proinvite.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;

@Entity //Spune lui JPA ca aceasta clasa reprezinta o entitate si trebuie mapata la un tabel din baza de date
@Table(name="invitations") //Specifica numele tabelului din baza de date
public class Invitation {

    @Id //Marcheaza campul ca fiind cheia primara
    @GeneratedValue(strategy= GenerationType.IDENTITY) //Specifica faptul ca cheia primara este generata automat
    private Long id;

    @ManyToOne //O invitatie apartine unui singur user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne //O invitatie foloseste un singur Template
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @Column(name="event_title",length = 64, nullable=false)//Specifica detaliile unei coloane(nume,daca e unica,daca e null)
    private String eventTitle;

    @Column(name="event_date", length=16, nullable=false)
    private String eventDate;

    @Column(name="event_time", length=16, nullable=false)
    private String eventTime;

    @Column(name="event_location",length=64, nullable=false)
    private String eventLocation;

    @Column(name="event_description",length=1000, nullable=false)
    private String eventDescription;

    @Lob //(Large Object) Spune lui Hibernate sa mapeze campul la un tip de date mare în SQL(longtext)
    @Column(name = "generated_json", columnDefinition = "LONGBLOB")
    private String generatedJson;

    @Column(name="download_url",length=512, nullable=false)
    private String downloadUrl;

    @Column(name="created_at", nullable = false)
    private Timestamp createdAt;

    //Constructor fara argumente
    public Invitation(){
    }

    //Constructor cu argumente
    public Invitation(String title,String date,String time,String location,String description,String json,String url,Timestamp ca){
        this.eventTitle=title;
        this.eventDate=date;
        this.eventTime=time;
        this.eventLocation=location;
        this.eventDescription=description;
        this.generatedJson=json;
        this.downloadUrl=url;
        this.createdAt=ca;
    }

    //Getteri
    public Long getId(){
        return id;
    }
    public User getUser(){
        return user;
    }
    public Template getTemplate(){
        return template;
    }
    public String getEventTitle(){
        return eventTitle;
    }
    public String getEventDate(){
        return eventDate;
    }
    public String getEventTime(){
        return eventTime;
    }
    public String getEventLocation(){
        return eventLocation;
    }
    public String getEventDescription(){
        return eventDescription;
    }
    public String getGeneratedJson(){
        return generatedJson;
    }
    public String getDownloadUrl(){
        return downloadUrl;
    }
    public Timestamp getCreatedAt(){
        return createdAt;
    }

    //Setteri
    public void setId(Long i){
        this.id=i;
    }
    public void setUser(User u){
        this.user=u;
    }
    public void setTemplate(Template t){
        this.template=t;
    }
    public void setEventTitle(String title){
        this.eventTitle=title;
    }
    public void setEventDate(String date){
        this.eventDate=date;
    }
    public void setEventTime(String time){
        this.eventTime=time;
    }
    public void setEventLocation(String location){
        this.eventLocation=location;
    }
    public void setEventDescription(String description){
        this.eventDescription=description;
    }
    public void setGeneratedJson(String json){
        this.generatedJson=json;
    }
    public void setDownloadUrl(String url){
        this.downloadUrl=url;
    }
    public void setCreatedAt(Timestamp ca){
        this.createdAt=ca;
    }

}

