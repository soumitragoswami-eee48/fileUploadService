/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portunus.fileuploadservice.controllers;

import com.portunus.fileuploadservice.models.MyMetaData;
import com.portunus.fileuploadservice.utils.BaseCloudStoreCRUDFactory;
import com.portunus.fileuploadservice.utils.DropBoxClientFactory;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author soumitragoswami
 */
@RestController
public class DropBoxControllers {

    @Value("${storageProvider}")
    String storageProvider;
    @Value("${dropbox.accesstoken}") 
    String accessToken;
    @Autowired
    BaseCloudStoreCRUDFactory client;
    
    
     @PostConstruct
    public void init() {
         System.out.println("%&%*&*&*&"+storageProvider);
        if(storageProvider.equals("dropbox")){
            client = new DropBoxClientFactory(accessToken);
        }
    }

    @RequestMapping(value = "/open/filemetadata", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getExistingfiles() {
        MyMetaData met = client.getExistingFilesMetaData();
        if (!"".equals(met.getErrorMessage())) {
            return new ResponseEntity(met.getErrorMessage(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(met.getListOfFilesAndFolders(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/open/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFiles(
            @RequestParam(name = "path", required = false, defaultValue = "/") String folderPath,
            @RequestParam("file") MultipartFile uploadfile) {

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("একটি ফাইল নির্বাচন করুন!", HttpStatus.OK);
        }

        String response = client.uploadFile(uploadfile, folderPath);
        if (response.contains("Error:")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity("সাফল্যের সাথে আপলোড করা হয়েছে-  " + response
                    + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
        }

    }
}
