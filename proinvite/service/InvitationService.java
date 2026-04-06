package com.proinvite.service;
import com.proinvite.dto.InvitationRequestDTO;
import com.proinvite.exception.TemplateNotFoundException;
import com.proinvite.exception.UserNotFoundException;
import com.proinvite.model.User;
import com.proinvite.model.Invitation;
import com.proinvite.model.Template;
import com.proinvite.repository.UserRepository;
import com.proinvite.repository.InvitationRepository;
import com.proinvite.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.Base64;
import com.proinvite.dto.InvitationRequestDTO;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final RestTemplate restTemplate;
    private final String githubUsername; //
    private final String githubToken;

    public InvitationService(InvitationRepository ir, UserRepository ur, TemplateRepository tr, RestTemplate rt, @Value("${github.username}") String githubUsername,@Value("${github.token}") String githubToken){
        this.invitationRepository=ir;
        this.userRepository=ur;
        this.templateRepository=tr;
        this.restTemplate=rt;
        this.githubUsername=githubUsername;
        this.githubToken=githubToken;
    }

    public Invitation generateInvitation(InvitationRequestDTO requestDTO){
        Optional<User> userOprional =userRepository.findById(requestDTO.getUserId());
        if(userOprional.isEmpty()){
            throw new UserNotFoundException("Utilizatorul nu a fost gasit!");
        }
        User user=userOprional.get();

        Optional<Template> templateOptional=templateRepository.findById(requestDTO.getTemplateId());
        if(templateOptional.isEmpty()){
            throw new TemplateNotFoundException("Template-ul nu a fost gasit!");
        }
        Template template=templateOptional.get();

        Invitation invitation=new Invitation();
        invitation.setUser(user);
        invitation.setTemplate(template);
        invitation.setEventTitle(requestDTO.getEventTitle());
        invitation.setEventDate(requestDTO.getEventDate());
        invitation.setEventTime(requestDTO.getEventTime());
        invitation.setEventLocation(requestDTO.getEventLocation());
        invitation.setEventDescription(requestDTO.getEventDescription());

        String mockJson=createInvitationContent(requestDTO, template);
        invitation.setGeneratedJson(mockJson);
        invitation.setDownloadUrl("/mock/download/" + System.currentTimeMillis());
        invitation.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return invitationRepository.save(invitation);
    }

    private String getTemplateContentFromGitHub(String templateJsonUrl){
        HttpHeaders headers=new HttpHeaders();
        String auth=githubUsername+":"+githubToken;
        byte[] encodedAuth=Base64.getEncoder().encode(auth.getBytes());
        String authHeader="Basic "+new String(encodedAuth);
        headers.set("Authorization", authHeader);

        HttpEntity<String> entity=new HttpEntity<>(headers);

        try{
            ResponseEntity<String> response=restTemplate.exchange(templateJsonUrl,HttpMethod.GET,entity, String.class);
            if(response.getStatusCode().is2xxSuccessful() && response.getBody()!=null){
                return response.getBody();
            }else{
                throw new RuntimeException("Eroare la descarcarea template-ului de pe GitHub. Status: "+response.getStatusCode());
            }
        }catch(Exception e){
            throw new RuntimeException("Eroare de conexiune la Github sau descarcare template: "+e.getMessage(),e);
        }
    }

    private String createInvitationContent(InvitationRequestDTO requestDTO, Template template){
        String rawTemplateJson = getTemplateContentFromGitHub(template.getJsonPath());
        String finalJson=rawTemplateJson
                .replace("{{eventTitle}}", requestDTO.getEventTitle())
                .replace("{{eventDate}}", requestDTO.getEventDate())
                .replace("{{eventTime}}", requestDTO.getEventTime())
                .replace("{{eventLocation}}", requestDTO.getEventLocation())
                .replace("{{eventDescription}}", requestDTO.getEventDescription());
        return finalJson;
    }

}
