package za.ac.bheki97.testsecspring.service;

import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.ac.bheki97.testsecspring.dto.CreateEventDto;
import za.ac.bheki97.testsecspring.dto.GuestEventDao;
import za.ac.bheki97.testsecspring.dto.JoinEventDto;
import za.ac.bheki97.testsecspring.dto.MakeSpeakerDto;
import za.ac.bheki97.testsecspring.entity.event.Event;
import za.ac.bheki97.testsecspring.entity.user.guest.Guest;
import za.ac.bheki97.testsecspring.entity.user.speaker.Speaker;
import za.ac.bheki97.testsecspring.exception.EventException;
import za.ac.bheki97.testsecspring.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private GuestRepo guestRepo;

    @Autowired
    private SpeakerRepo speakerRepo;

    @Autowired
    private HostRepo hostRepo;

    @Autowired
    private UserRepository userRepo;




    public boolean createEvent(Event event) throws EventException {

        //check if the event is null
        if(event==null){
            throw new EventException("Event cannot be null");
        }

        //check if the Host is null
        if(event.getHost()==null){
            throw new EventException("Host cannot be null");
        }



        //check if there is an event with same host AccountId and Same Occasion
        if(eventRepo.existsByHost_Account_IdNumberAndOccasion(
                event.getHost().getAccount().getIdNumber(),event.getOccasion().trim())){
            throw new EventException("Host already has "+event.getOccasion()+" occasion");
        }



        //event with the same Key
        if (eventRepo.existsById(event.getEventKey())){
            throw new EventException("Event Key already exists");
        }

        //check if there is event with same host AccountId and occurs same day
        if(eventRepo.existsByHost_Account_IdNumberAndDate(
                event.getHost().getAccount().getIdNumber(),event.getDate()
        )){
            throw new EventException("Already Hosting an Event on this Date");
        }


        eventRepo.save(event);

        return true;
    }

    public boolean joinEvent(JoinEventDto dto) throws EventException {

        if(dto==null)
            throw new EventException("");


        if(!userRepo.existsById(dto.getAccId())){
            throw new EventException("Account "+dto.getAccId()+" does not exist");
        }


        if(!eventRepo.existsById(dto.getEventKey())){
            System.out.println("QR code: "+dto.getEventKey());
            throw new EventException("This is not a Valid QR code");
        }


        //this stops the host from joining the event
        if(eventRepo.findById(dto.getEventKey())
                .get().getHost()
                .getAccount().getIdNumber().equalsIgnoreCase(dto.getAccId())){
            throw new EventException("You cannot join the event you're hosting");
        }




        //checking if the Guest has Joined the Event
        if(eventRepo.findById(dto.getEventKey()).orElseThrow()
                .getGuests().stream()
                .anyMatch(guest -> guest.getAccount()
                        .getIdNumber()
                        .equals(dto.getAccId())))
            throw new EventException("Guest Already part of the Event");

        if(eventRepo.existsByHost_Account_IdNumberAndEventKey(dto.getAccId(), dto.getEventKey())){
            throw new EventException("You Can't join your own Event");
        }

        //create new guest object using an account
        Guest guest = new Guest();
        guest.setJoindate(LocalDateTime.now());
        guest.setAccount(userRepo.findById(dto.getAccId()).orElseThrow());

        //update the guests of the event with the newly created guest
        Event event = eventRepo.findById(dto.getEventKey()).orElseThrow();
        guest.setEvent(event);

        guestRepo.save(guest);


        return true;

    }

    public boolean updateEvent(Event event){
        eventRepo.save(event);
        return true;
    }

    public boolean cancelEvent(String eventKey){
        eventRepo.deleteById(eventKey);
        return true;
    }

    @Transactional
    public boolean leaveEvent(String eventKey,String accId) throws EventException {

        if(eventKey==null || eventKey.isEmpty() || !eventRepo.existsById(eventKey))
            throw new EventException("Event Key Does not exists for the event you're trying to leave");

        if(accId==null || accId.isEmpty())
            throw new EventException("Invalid account");

        if(!guestRepo.existsByEvent_EventKeyAndAccount_IdNumber(eventKey,accId))
            throw new EventException("You are not a guest at any event");

        guestRepo.deleteGuestByEvent_EventKeyAndAccount_IdNumber(eventKey,accId);


        return true;
    }


    public boolean changeEventTitle(MakeSpeakerDto dto) throws EventException {

        if(dto ==null ||
                dto.getGuestId()<0 ||
                dto.getHostId()<0||
                dto.getEventKey()==null ||
                dto.getEventKey().isEmpty())return false;

        //System.out.println("guest not null");

        if(eventRepo.findById(dto.getEventKey()).get().getHost().getHostId()!=dto.getHostId())
            throw new EventException("Only Host can Make a Guest Speaker!");


        if(eventRepo.findById(dto.getEventKey()).orElseThrow()
                .getGuests().stream()
                .noneMatch(guest -> guest.getGuestId()==dto.getGuestId()))
            throw new EventException("Only Guest of this event can be Speakers");

        if((eventRepo.findById(dto.getEventKey()).orElseThrow()
                .getGuests().stream()
                .anyMatch(guest -> guest.getGuestId()==dto.getGuestId() && guest instanceof Speaker)))
            throw new EventException("Guest has already a Speaker");


        Guest guest = guestRepo.findById(dto.getGuestId()).get();
        Speaker speaker = Speaker.buildSpeaker(guest);
        guestRepo.deleteById(guest.getGuestId());
        speakerRepo.save(speaker);
        return true;
    }

    public Event[] getEvents(){
        List<Event> events = eventRepo.findAll();
        Event[] arr = new Event[events.size()];
        return events.toArray(arr);
    }


    public List<Event>  getAllEventOfHost(String hostAccId) throws EventException {

        if(hostAccId==null )return null;

        if(!hostRepo.existsByAccount_IdNumber(hostAccId))
            throw new EventException("You are not a Host");

        List<Event> events = eventRepo.findAllByHost_Account_IdNumber(hostAccId)
                .stream().map(v ->{

                    //setGuest Event to Null
                    v.setGuests(v.getGuests().stream().map(g -> {
                        g.setEvent(null);
                        return g;
                    }).toList());

                    return v;

                }).toList();


        return events;
    }

    public List<GuestEventDao> getAllJoinedEvents(String guestAccId) throws EventException {
        if(guestAccId==null )return null;

        if(!guestRepo.existsByAccount_IdNumber(guestAccId))
            throw new EventException("You have not yet Joined Any Event");

       return guestRepo.findAllByAccount_IdNumber(guestAccId)
                .stream()
                .map(guest -> {
                    Event event = guest.getEvent();
                    List<Speaker>speakers = event.getGuests()
                                                .stream()
                                                .filter(guest1 -> guest1 instanceof Speaker)
                                                .map( g ->{
                                                    g.setEvent(null);

                                                    return (Speaker)g;
                                                }).toList();

                        GuestEventDao dao = new GuestEventDao();
                        dao.setEventKey(event.getEventKey());
                        dao.setHost(event.getHost());
                        dao.setDate(event.getDate());
                        dao.setSpeakers(speakers);
                        dao.setOccasion(event.getOccasion());
                        dao.setDescription(event.getDescription());

                    return dao;
                })
                .toList();


    }

    public List<Guest> getAllEventGuests(String eventKey) throws EventException {

        if(eventKey==null || eventKey.isEmpty())
            throw new EventException("");

        return guestRepo.findAllByEvent_EventKey(eventKey);
    }







}
