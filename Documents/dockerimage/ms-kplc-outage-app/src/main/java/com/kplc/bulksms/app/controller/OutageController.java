package com.kplc.bulksms.app.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;

@RestController
public class OutageController extends BaseController{


    @GetMapping("/test")
    public ResponseEntity<Object> Test(){
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/outage/load")
    public ResponseEntity<Object> registerOutage() throws FileNotFoundException {

        String uploadDir = env.getProperty("file.upload.dir");

//        List<File> outageFiles = FileUtil.outageFiles(uploadDir);
//
//        String fileExtension = FileUtil.getFileExtension(file);

            File outageFile = new File("D:\\CamelProjects\\images\\11022024-1.png");
            HttpHeaders headers = new HttpHeaders();
            //for(File outageFile : outageFiles){
            LinkedMultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
            body.add("images", new FileSystemResource("D:\\CamelProjects\\images\\11022024-1.png"));
            //headers.setContentType(MediaType.IMAGE_PNG);
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String serverUrl = "http://138.197.111.149:3030/v1/rora/uploadOutages";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(serverUrl,requestEntity, String.class);

        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
