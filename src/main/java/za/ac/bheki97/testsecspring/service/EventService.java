package za.ac.bheki97.testsecspring.service;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

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
                event.getHost().getAccount().getIdNumber(),event.getOccasion())){
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


        if(!userRepo.existsById(dto.getAccId())){
            throw new EventException("Account with"+dto.getAccId()+" does not exist");
        }

        if(!eventRepo.existsById(dto.getEventKey())){
            throw new EventException("This is not a Valid QR code");
        }

        //checking if the Guest has Joined the Event
        if(eventRepo.findById(dto.getEventKey()).orElseThrow()
                .getGuests().stream()
                .anyMatch(guest -> guest.getAccount()
                        .getIdNumber()
                        .equals(dto.getAccId())))
            throw new EventException("Guest Already part of the Event");

        //create new guest object using an account
        Guest guest = new Guest();
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

    public boolean leaveEvent(String eventKey,int guestId) throws EventException {


        if(!eventRepo.existsById(eventKey))
            throw new EventException("Invalid event");

        if(!guestRepo.existsById(guestId))
            throw new EventException("You are not a guest");

        //check if there is any guest is part of the event
        if(eventRepo.findById(eventKey).orElseThrow()
                .getGuests().stream()
                .noneMatch(guest -> guest.getGuestId()==guestId))
            throw new EventException("You are not part of this event\n");

        System.out.println("You are Part of this event\n");
        //remove the guest from the event guest list
        Guest guest = guestRepo.findById(guestId).get();
        guestRepo.delete(guest);

        if(guestRepo.existsById(guestId))
            throw new EventException("Guest Failed to Leave");

        return true;
    }


    public boolean makeGuestSpeaker(MakeSpeakerDto dto) throws EventException {

        if(dto ==null ||
                dto.getGuestId()<0 ||
                dto.getHostId()<0||
                dto.getEventKey()==null ||
                dto.getEventKey().isEmpty())return false;



        if(!eventRepo.existsByHost_HostId(dto.getHostId()))
            throw new EventException("Only Host can Make a Guest Speaker!");

        if(eventRepo.findById(dto.getEventKey()).orElseThrow()
                .getGuests().stream()
                .noneMatch(guest -> guest.getGuestId()==dto.getGuestId()))
            throw new EventException("Only Guest of this event can be Speakers");

        if(eventRepo.findById(dto.getEventKey()).orElseThrow()
                .getGuests().stream()
                .noneMatch(guest -> guest instanceof Speaker))
            throw new EventException("Guest is Already a Speaker");


        Guest guest = guestRepo.findById(dto.getGuestId()).get();
        Speaker speaker = (Speaker) guest;
        guestRepo.deleteById(guest.getGuestId());
        speakerRepo.save(speaker);
        return true;
    }


    public CreateEventDto[] getAllEventOfHost(String hostAccId) throws EventException {

        if(hostAccId==null )return null;

        if(!hostRepo.existsByAccount_IdNumber(hostAccId))
            throw new EventException("You are not a Host");

        List<CreateEventDto> events = eventRepo.findAllByHost_Account_IdNumber(hostAccId)
                .stream().map( ev -> new CreateEventDto(ev)).toList();

        CreateEventDto[] eventsArr = new CreateEventDto[events.size()];

        return events.toArray(eventsArr);
    }

    public GuestEventDao[] getAllJoinedEvents(String guestAccId) throws EventException {
        if(guestAccId==null )return null;

        if(!guestRepo.existsByAccount_IdNumber(guestAccId))
            throw new EventException("You have not yet Joined Any Event");

        List<GuestEventDao> events = guestRepo.findAllByAccount_IdNumber(guestAccId)
                .stream()
                .map(guest -> {
                    Event event = guest.getEvent();
                    List<Speaker>speakers = event.getGuests()
                                                .stream()
                                                .filter(guest1 -> guest1 instanceof Speaker)
                                                .map(Speaker::buildSpeaker).toList();

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
        GuestEventDao[] eventsArr = new GuestEventDao[events.size()];
        events.toArray(eventsArr);
        return events.toArray(eventsArr);

    }







}
