package com.mbclandgroup.fitresume.model;

public class Candidate {

    private String _id;
    private String name;
    private String age;
    private String dateOfBirth;
    private String tel;
    private String address;
    private String degree;
    private String currentPosition;
    private String currentWorkplace;
    private String durationOfWork;
    private String skills;
    private String expectedSalary;
    private String currentSalary;

    public Candidate(){}

    public Candidate(String name, String age, String dateOfBirth, String tel,
                     String address, String degree, String currentPosition, String currentWorkplace,
                     String durationOfWork, String skills, String expectedSalary, String currentSalary) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.tel = tel;
        this.address = address;
        this.degree = degree;
        this.currentPosition = currentPosition;
        this.currentWorkplace = currentWorkplace;
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

    public String getCurrentWorkplace() {
        return currentWorkplace;
    }

    public void setCurrentWorkplace(String currentWorkplace) {
        this.currentWorkplace = currentWorkplace;
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
    public String toString() {
        final StringBuffer sb = new StringBuffer("Candidate{");
        sb.append("_id='").append(_id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", dateOfBirth='").append(dateOfBirth).append('\'');
        sb.append(", tel='").append(tel).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", degree='").append(degree).append('\'');
        sb.append(", currentPosition='").append(currentPosition).append('\'');
        sb.append(", currentWorkplace='").append(currentWorkplace).append('\'');
        sb.append(", durationOfWork='").append(durationOfWork).append('\'');
        sb.append(", skills='").append(skills).append('\'');
        sb.append(", expectedSalary='").append(expectedSalary).append('\'');
        sb.append(", currentSalary='").append(currentSalary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
