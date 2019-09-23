/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portunus.fileuploadservice.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author soumitragoswami
 */
public class MyMetaData {
   private List<String> listOfFilesAndFolders = new ArrayList<>();
   private String errorMessage="";

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getListOfFilesAndFolders() {
        return listOfFilesAndFolders;
    }

    public void setListOfFilesAndFolders(List<String> listOfFilesAndFolders) {
        this.listOfFilesAndFolders = listOfFilesAndFolders;
    }
}
