package edu.oregonstate.mist.catalogapitest.core

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class Course {

    Integer cid
    Integer crn
    String courseName
    String instructor
    String day
    String time
    String location
}
