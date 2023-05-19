package za.ac.bheki97.testsecspring.controller;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import za.ac.bheki97.testsecspring.dto.CreateEventDto;
import za.ac.bheki97.testsecspring.dto.JoinEventDto;
import za.ac.bheki97.testsecspring.entity.event.Event;
import za.ac.bheki97.testsecspring.exception.EventException;
import za.ac.bheki97.testsecspring.service.EventService;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("")
    public boolean createEvent(@RequestBody CreateEventDto event) throws EventException {
//        event.setGuests(new ArrayList<>());

        System.out.println(event);
        System.out.println(event.getLocalDateTime().toString());
        return service.createEvent(new Event(event));
    }

    @PostMapping("/join")
    public boolean joinEvent(@RequestBody JoinEventDto dto) throws EventException {

        return service.joinEvent(dto);
    }

    @PutMapping("")
    public boolean updateEvent(@RequestBody Event event){

        return service.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public boolean removeEvent(@PathVariable String id){
        return service.cancelEvent(id);

    }

    @DeleteMapping("/leave/{eventKey}/{guestId}")
    public boolean leaveEvent(@PathVariable String eventKey,@PathVariable int guestId) throws EventException {
        return service.leaveEvent(eventKey,guestId);
    }

    @PostMapping("/host")
    public CreateEventDto[] getAllHostEvents(@RequestBody String id) throws EventException {

        System.out.println("ID: "+id.replace("\"",""));
        CreateEventDto[] eventDto = service.getAllEventOfHost(id.replace("\"",""));
        Arrays.stream(eventDto).forEach(System.out::println);
        return eventDto;
    }







}
