package za.ac.bheki97.testsecspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.bheki97.testsecspring.dto.CreateEventDto;
import za.ac.bheki97.testsecspring.dto.GuestEventDao;
import za.ac.bheki97.testsecspring.dto.JoinEventDto;
import za.ac.bheki97.testsecspring.dto.MakeSpeakerDto;
import za.ac.bheki97.testsecspring.entity.event.Event;
import za.ac.bheki97.testsecspring.entity.user.speaker.Speaker;
import za.ac.bheki97.testsecspring.exception.EventException;
import za.ac.bheki97.testsecspring.service.EventService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        event.setGuests(new ArrayList<>());
        return service.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public boolean removeEvent(@PathVariable String id){
        return service.cancelEvent(id);

    }

    @DeleteMapping("/leave/{eventKey}/{accId}")
    public boolean leaveJoinedEvent(@PathVariable String eventKey,@PathVariable String accId) throws EventException {
        System.out.println("Guest Leaving the event: " +accId+
                "\nEvent Key: "+eventKey);

        return service.leaveEvent(eventKey,accId);
    }

    @PostMapping("/host")
    public List<Event> getAllHostEvents(@RequestBody String id) throws EventException {

        System.out.println("ID: "+id.replace("\"",""));
        //List<Event> events   = service.getAllEventOfHost(id.replace("\"",""));

        return service.getAllEventOfHost(id.replace("\"",""));
    }

    @PostMapping("/joined")
    public List<GuestEventDao> eventsJoinedByUser(@RequestBody String accid) throws EventException {
       return service.getAllJoinedEvents(accid.replace("\"",""));

    }

    @PostMapping("/change-title")
    public boolean changeEventTitle(@RequestBody MakeSpeakerDto dto) throws EventException {
        System.out.println(dto);

        return service.changeEventTitle(dto);
    }

//    @PostMapping("/speech/{speakerId}")
//    public String addSpeech(@RequestParam("audio") MultipartFile audio,@RequestParam("")int speakerId){
//            return service.SpeakAtEvent(audio,speakerId);
//    }

    @GetMapping("/{eventKey}/speaker")
    public List<Speaker> getSpeakers(@PathVariable String eventKey) throws EventException {
        return service.getAllEventSpeakers(eventKey);
    }
    @GetMapping("/{eventKey}/speaker/{language}")
    public List<Speaker> getSpeakerss(@PathVariable String eventKey,@PathVariable String language) throws EventException {
        return service.getAllEventSpeaker(eventKey,language);
    }


    @PutMapping("/{guestId}")
    public boolean updateSpeechOfSpeaker(@PathVariable int guestId, @RequestBody String speech) throws EventException {
       return service.addSpeakerSpeech(guestId, speech);
    }

    @PutMapping("/{guestId}/{language}")
    public String speakerAtEvent(@RequestParam("audio") MultipartFile audio,
                                 @PathVariable("language") String language,
                                 @PathVariable("guestId")int guestId) throws IOException {
        File inputFile = new File("C:\\User\\VM JELE\\audios\\"+audio.getOriginalFilename()),
                outputFile = new File("C:\\User\\VM JELE\\audios\\prita-" +new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.ENGLISH).format(new Date())+".flac" );

        if(inputFile.exists()){
            inputFile.mkdir();
        }
        byte[] data;

        System.out.println("Transcribing......"+language);
        System.out.println("Path and Name: "+audio.getOriginalFilename());

        if(!inputFile.exists()){

            inputFile.mkdirs();

        }

        try {
            audio.transferTo(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProcessBuilder builder = new ProcessBuilder("ffmpeg","-i",
                inputFile.getAbsolutePath(),"-ar","44100","-ac","2","-f","flac",outputFile.getAbsolutePath());
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int exitCode = -1;

        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (exitCode == 0) {

            try {
                data = Files.readAllBytes(Path.of(outputFile.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //outputFile.delete();
            //inputFile.delete();
            return service.addSPeech(data,language,guestId);
        } else {

            return "Conversion failed.";
        }


    }



}
