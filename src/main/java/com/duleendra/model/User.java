package com.duleendra.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.completion.Completion;

import java.io.Serializable;

/**
 * User document for saving to Elasticsearch
 *
 * @author Duleendra Shashimal
 */

@Document(indexName = "tremor_video", type = "user")
public class User implements Serializable {

    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private String id;

    @NotEmpty
    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    private String firstName;

    @NotEmpty
    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    private String lastName;

    @NotEmpty
    @Email
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private String email;

    @NotEmpty
    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    private String bioData;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private String userType;

    @Field(type = FieldType.String, index = FieldIndex.analyzed, store = true)
    private String adminJobResponsibility;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private String dateOfBirth;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed, store = true)
    private Integer adminAccessCode;

    @CompletionField(payloads = false, maxInputLength = 100)
    private Completion searchSuggest;

    public User() {
    }

    public User(String firstName, String lastName, String email, String birthDate, String userType, String bioData, int accessCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = birthDate;
        this.userType = userType;
        this.bioData = bioData;
        this.adminAccessCode = accessCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getBioData() {
        return bioData;
    }

    public void setBioData(String bioData) {
        this.bioData = bioData;
    }

    public Integer getAdminAccessCode() {
        return adminAccessCode;
    }

    public void setAdminAccessCode(Integer adminAccessCode) {
        this.adminAccessCode = adminAccessCode;
    }

    public String getAdminJobResponsibility() {
        return adminJobResponsibility;
    }

    public void setAdminJobResponsibility(String adminJobResponsibility) {
        this.adminJobResponsibility = adminJobResponsibility;
    }

    public Completion getSearchSuggest() {
        return searchSuggest;
    }

    public void setSearchSuggest(Completion searchSuggest) {
        this.searchSuggest = searchSuggest;
    }

    @Override
    public String toString() {
        return "User ["
                + this.getFirstName() +
                this.getLastName() +
                this.getEmail() +
                this.getUserType() +
                this.getAdminAccessCode() +
                this.getBioData() +
                this.getDateOfBirth() +
                this.getAdminJobResponsibility() +
                "]";
    }
}
