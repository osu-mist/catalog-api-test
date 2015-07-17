package edu.oregonstate.mist.catalogapitest.mapper

import edu.oregonstate.mist.catalogapitest.core.Course
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.sql.SQLException

public class CourseMapper implements ResultSetMapper<Course> {
    public Course map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        Course course = new Course()

        course.with {

            cid           = rs.getInt     'CID'
            crn           = rs.getInt     'CRN'
            courseName    = rs.getString  'COURSENAME'
            instructor    = rs.getString  'INSTRUCTOR'
            day           = rs.getString  'DAY'
            time          = rs.getString  'TIME'
            location      = rs.getString  'LOCATION'
        }

        return course
    }
}
