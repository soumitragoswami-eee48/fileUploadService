package com.portunus.fileuploadservice;

import com.dropbox.core.DbxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.portunus.fileuploadservice.utils.DropBoxClientFactory;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class FileuploadserviceApplication {
//
//    @Autowired
//    private static DropBoxClientFactory clientFactory;

    public static void main(String[] args) throws DbxException, IOException {
       
        SpringApplication.run(FileuploadserviceApplication.class, args);
    }

}
