package com.proinvite.dto;

/**
 * Data Transfer Object (DTO) folosit pentru a primi datele din formularul
 * de generare a invitației trimis de client (frontend).
 */

public class InvitationRequestDTO {
    private Long userId;
    private Long templateId;
    private String eventTitle;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String eventDescription;

    public InvitationRequestDTO(){
    }

    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long ui){
        this.userId=ui;
    }
    public Long getTemplateId(){
        return templateId;
    }
    public void setTemplateId(Long ti){
        this.templateId=ti;
    }
    public String getEventTitle(){
        return eventTitle;
    }
    public void setEventTitle(String title){
        this.eventTitle=title;
    }
    public String getEventDate(){
        return eventDate;
    }
    public void setEventDate(String date){
        this.eventDate=date;
    }
    public String getEventTime(){
        return eventTime;
    }
    public void setEventTime(String time){
        this.eventTime=time;
    }
    public String getEventLocation(){
        return eventLocation;
    }
    public void setEventLocation(String location){
        this.eventLocation=location;
    }
    public String getEventDescription(){
        return eventDescription;
    }
    public void setEventDescription(String description){
        this.eventDescription=description;
    }
}
