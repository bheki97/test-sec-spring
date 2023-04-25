package za.ac.bheki97.testsecspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.bheki97.testsecspring.service.JwtService;
import za.ac.bheki97.testsecspring.service.SpeechTextService;

import za.ac.bheki97.testsecspring.dto.TranslationDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/speech")
public class SpeechController {

    @Autowired
    private SpeechTextService speechService;

    @PostMapping("/transcribe")
    public String transcribeAudio(@RequestParam("audio") MultipartFile audio,
                                  @RequestParam("language") String language) throws IOException, InterruptedException {
        File inputFile = new File("C:\\User\\VM JELE\\audios\\"+audio.getOriginalFilename()),
                outputFile = new File("C:\\User\\VM JELE\\audios\\prita-" +new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.ENGLISH).format(new Date())+".flac" );

        if(inputFile.exists()){
            inputFile.mkdir();
        }


        System.out.println("Transcribing......"+language);
        System.out.println("Path and Name: "+audio.getOriginalFilename());

        if(!inputFile.exists()){

            inputFile.mkdirs();

        }



        audio.transferTo(inputFile);
        ProcessBuilder builder = new ProcessBuilder("ffmpeg","-i",
                inputFile.getAbsolutePath(),"-ar","44100","-ac","2","-f","flac",outputFile.getAbsolutePath());
        Process process = builder.start();

        int exitCode = -1;

        exitCode = process.waitFor();

        if (exitCode == 0) {

            byte[] data = Files.readAllBytes(Path.of(outputFile.getAbsolutePath()));
            outputFile.delete();
            inputFile.delete();
            return speechService.transcribeAudio(data,language.replaceAll("\"",""));
        } else {

            return "Conversion failed.";
        }

    }


    @PostMapping("/translate") public String translate(@RequestBody TranslationDto dto) throws IOException {

        String translation = speechService.translateText(dto.getText(), dto.getOriginLang(), dto.getTransLang());

        return translation;
    }



    @GetMapping("/{lang}/{text}")
    public ResponseEntity<byte[]> readText(@PathVariable("text") String text,
                                                        @PathVariable("lang") String language) throws IOException {
        System.out.println("Translating: "+text+" to "+language+" language");

        MultipartFile file =  speechService.readtext(text, language);
        InputStreamResource resource = new InputStreamResource(file.getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);

        //return speechService.readtext(text, language);
        byte[] bytes = resource.getInputStream().readAllBytes();
        System.out.println("translation Done!!!");
        return   ResponseEntity.ok()
                .header(String.valueOf(headers))
                .body(bytes);
    }

}
