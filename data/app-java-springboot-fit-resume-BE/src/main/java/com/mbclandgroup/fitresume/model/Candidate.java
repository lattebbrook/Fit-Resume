package com.mbclandgroup.fitresume.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "candidate")
public class Candidate {

    @Id
    private String _id;
    private String fileName;
    private String name;
    private String age;
    private String dateOfBirth;
    private String tel;
    private String address;
    private String degree;
    private String currentPosition;
    private String workplaceHistory;
    private String durationOfWork;
    private String skills;
    private String expectedSalary;
    private String currentSalary;

    public Candidate(String fileName, String name, String age, String dateOfBirth, String tel,
                     String address, String degree, String currentPosition, String workplaceHistory,
                     String durationOfWork, String skills, String expectedSalary, String currentSalary) {
        this.fileName = fileName;
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.tel = tel;
        this.address = address;
        this.degree = degree;
        this.currentPosition = currentPosition;
        this.workplaceHistory = workplaceHistory;
        this.durationOfWork = durationOfWork;
        this.skills = skills;
        this.expectedSalary = expectedSalary;
        this.currentSalary = currentSalary;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getWorkplaceHistory() {
        return workplaceHistory;
    }

    public void setWorkplaceHistory(String workplaceHistory) {
        this.workplaceHistory = workplaceHistory;
    }

    public String getDurationOfWork() {
        return durationOfWork;
    }

    public void setDurationOfWork(String durationOfWork) {
        this.durationOfWork = durationOfWork;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(String currentSalary) {
        this.currentSalary = currentSalary;
    }

    @Override
    public String toString(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
