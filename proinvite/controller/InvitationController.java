package com.proinvite.controller;

import com.proinvite.exception.TemplateNotFoundException;
import com.proinvite.dto.InvitationRequestDTO;
import com.proinvite.service.InvitationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proinvite.model.Invitation;
import com.proinvite.exception.UserNotFoundException;

//InvitationController este poarta de intrare in aplicatie; gestioneaza comunicarea dintre frontend si service; rolul ei este de a primi cereri http
@RestController
@RequestMapping("/api/invitation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService){
        this.invitationService=invitationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestBody InvitationRequestDTO requestDTO){
        try{
            Invitation savedInvitation=invitationService.generateInvitation(requestDTO);
            return new ResponseEntity<>("Invitatie generata cu succes! URL Mock: "+savedInvitation.getDownloadUrl(), HttpStatus.CREATED);
        }catch(UserNotFoundException |TemplateNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
