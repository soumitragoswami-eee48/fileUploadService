/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portunus.fileuploadservice.utils;

import com.dropbox.core.v2.DbxClientV2;
import com.portunus.fileuploadservice.models.MyMetaData;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author soumitragoswami
 */

public interface BaseCloudStoreCRUDFactory {

    public abstract Object getClient();

    public abstract String uploadFile(MultipartFile fileToBeUploaded, String path);

    public abstract MyMetaData getExistingFilesMetaData();

    public abstract String removeFile(String filename);

}
