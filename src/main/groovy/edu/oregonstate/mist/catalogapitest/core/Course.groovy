package edu.oregonstate.mist.catalogapitest.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class Course {

    private Integer cid
    private Integer crn
    private String courseName
    private String instructor
    private String day
    private String time
    private String location

    public Course() {
        // Jackson Deserialization
    }

    public Course(Integer cid, Integer crn, String courseName, String instructor, String day, String time, String location) {
        this.cid = cid
        this.crn = crn
        this.courseName = courseName
        this.instructor = instructor
        this.day = day
        this.time = time
        this.location = location
    }

    // Getters ------------------------------------------------------------------------------------------------
    @JsonProperty
    Integer getCid() {
        return cid
    }

    @JsonProperty
    Integer getCrn() {
        return crn
    }

    @JsonProperty
    String getCourseName() {
        return courseName
    }

    @JsonProperty
    String getInstructor() {
        return instructor
    }

    @JsonProperty
    String getDay() {
        return day
    }

    @JsonProperty
    String getTime() {
        return time
    }

    @JsonProperty
    String getLocation(){
        return location
    }

    // Setters ------------------------------------------------------------------------------------------------
    @JsonProperty
    void setCid(cid) {
        this.cid = cid
    }

    @JsonProperty
    void setCrn(crn) {
        this.crn = crn
    }

    @JsonProperty
    void setCourseName(courseName) {
        this.courseName = courseName
    }

    @JsonProperty
    void setInstructor(instructor) {
        this.instructor = instructor
    }

    @JsonProperty
    void setDay(day) {
        this.day = day
    }

    @JsonProperty
    void setTime(time) {
        this.time = time
    }
}