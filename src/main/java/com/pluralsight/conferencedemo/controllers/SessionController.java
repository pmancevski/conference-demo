package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id) {
        return sessionRepository.getReferenceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable Long id){
        //check for children
        sessionRepository.deleteById(id);
//        Session session = sessionRepository.getReferenceById(id);
//
//        if (session.getSpeakers().size() == 0) {
//            sessionRepository.deleteById(id);
//        }
//        else {
//
//            speakerRepository.deleteById(session.getSpeakers().get(0).getSpeaker_id());
//
//            sessionRepository.deleteById(id);
//        }
    }

    @RequestMapping(value =  "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //todo add validation that all attributes are passed in, otherwise status code 400
        Session existingSession = sessionRepository.getReferenceById(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");

        return sessionRepository.saveAndFlush(existingSession);
    }
}
