package za.ac.bheki97.testsecspring.controller;

import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.bheki97.testsecspring.dto.JoinEventDto;
import za.ac.bheki97.testsecspring.entity.event.Event;
import za.ac.bheki97.testsecspring.exception.EventException;
import za.ac.bheki97.testsecspring.service.EventService;

import java.util.ArrayList;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("/new")
    public boolean createEvent(@RequestBody Event event) throws EventException {
        event.setGuests(new ArrayList<>());

        return service.createEvent(event);
    }

    @PostMapping("/join")
    public boolean joinEvent(@RequestBody JoinEventDto dto) throws EventException {

        return service.joinEvent(dto);
    }

    @PutMapping("/update")
    public boolean updateEvent(@RequestBody Event event){

        return service.updateEvent(event);
    }

    @DeleteMapping("/remove/{id}")
    public boolean removeEvent(@PathVariable String id){
        return service.cancelEvent(id);

    }

    @DeleteMapping("/leave/{eventKey}/{guestId}")
    public boolean leaveEvent(@PathVariable String eventKey,@PathVariable int guestId) throws EventException {
        return service.leaveEvent(eventKey,guestId);
    }



}
