package com.shareqube.crud.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

/**
 * Created by judeebene on 12/26/16.
 */
public class Person {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String zipCode;
    private String dateOfBirth;
    private Object dateAdded;


    public Person(){

    }

    // to Exclude it from firebase conversion and
    @Exclude
    public HashMap<String, Object> convertToMap(){
        HashMap<String,Object> result = new HashMap<>();

        result.put("firstName", getFirstName()) ;
        result.put("lastName" , getLastName());
        result.put("phoneNumber" , getPhoneNumber());
        result.put("zipCode" ,getZipCode());
        result.put("dateOfBirth" , getDateOfBirth());
        result.put("dateAdded" ,getDateAdded()) ;

        return result;
    }

    public Person(String firstName, String lastName, String phoneNumber, String zipCode, String dateOfBirth, Object dateAdded) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.dateOfBirth = dateOfBirth;
        this.dateAdded = dateAdded;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Object getDateAdded() {
        return dateAdded;
    }
}
