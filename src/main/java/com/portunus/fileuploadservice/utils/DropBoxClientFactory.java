/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portunus.fileuploadservice.utils;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DeleteResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import com.portunus.fileuploadservice.models.MyMetaData;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author soumitragoswami
 */
@Service
public class DropBoxClientFactory implements BaseCloudStoreCRUDFactory {

    
    //String accessToken;

    private DbxClientV2 client;
//https://github.com/dropbox/dropbox-sdk-java/blob/master/examples/upload-file/src/main/java/com/dropbox/core/examples/upload_file/Main.java
//http://blog.camilolopes.com.br/serie-dropbox-gerando-token-no-dropbox-api/
    
    public DropBoxClientFactory(@Value("${dropbox.accesstoken}") String accessToken) {
        // Create Dropbox client
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        this.client = new DbxClientV2(config, accessToken);
        try {
            FullAccount account = client.users().getCurrentAccount();
            System.out.println("এই অ্যাকাউন্টটির মালিকানা রয়েছে এই ব্যক্তির $**$*$*$*$*$*$*$*$*$* " + account.getName().getDisplayName());

        } catch (DbxException dbex) {
            System.out.println("এই ব্যক্তির অ্যাকাউন্ট পাওয়া যায় নি | " + dbex);
        }

    }

    @Override
    public DbxClientV2 getClient() {
        return this.client;
    }

    @Override
    public String uploadFile(MultipartFile fileToBeUploaded, String path) {
        try {
            // Upload to Dropbox
            InputStream inputStream = new BufferedInputStream(fileToBeUploaded.getInputStream());
            FileMetadata metadata = getClient().files().uploadBuilder(path + fileToBeUploaded.getOriginalFilename())
                    .uploadAndFinish(inputStream);
        } catch (IOException | DbxException ex) {
            return "Error: Exception occured while uploading file";
        }catch (Exception e){
             Logger.getLogger(DropBoxClientFactory.class.getName()).log(Level.INFO, null, e);
        }
        return "Success: Files uploaded!";
    }

    @Override
    public MyMetaData getExistingFilesMetaData() {
        MyMetaData filesAndFolderListMeta = new MyMetaData();
        try {
            // Get files and folder metadata from Dropbox root directory
            ListFolderResult result;
            result = getClient().files().listFolder("");
            
            System.out.println("&&&&&&&&&&&"+result.getEntries().size());
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    filesAndFolderListMeta.getListOfFilesAndFolders().add(metadata.getPathLower());
                    System.out.println(metadata.getPathLower()+filesAndFolderListMeta.getListOfFilesAndFolders().size());
                }
                if (!result.getHasMore()) {
                    break;
                }
                result = getClient().files().listFolderContinue(result.getCursor());
            }
        } catch (DbxException ex) {
            Logger.getLogger(DropBoxClientFactory.class.getName()).log(Level.INFO, null, ex);
            filesAndFolderListMeta.setErrorMessage("কোনও ফাইল বা ফোল্ডার পাওয়া যায় নি");
        } catch (Exception e){
        Logger.getLogger(DropBoxClientFactory.class.getName()).log(Level.INFO, null, e);
        }
        return filesAndFolderListMeta;
    }

    @Override
    public String removeFile(String filename,String path) {
        try
        {
            DeleteResult res = client.files().deleteV2(path+filename);
            
        }
        catch (DbxException dbxe)
        {
            dbxe.printStackTrace();
            return "Error:could not delete";
        }
    return "Successful:file deleted";
    }

}
