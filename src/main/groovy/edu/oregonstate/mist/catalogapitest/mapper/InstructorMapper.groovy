package edu.oregonstate.mist.catalogapitest.mapper

import edu.oregonstate.mist.catalogapitest.core.Instructor
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper
import java.sql.ResultSet
import java.sql.SQLException


public class InstructorMapper implements ResultSetMapper<Instructor> {
    public Instructor map(int i, ResultSet rs, StatementContext sc) throws SQLException {
        Instructor instructor = new Instructor()

        instructor.with {
            cid               = rs.getInt     'CID'
            firstInitial      = rs.getString  'FIRST_INITIAL'
            lastName          = rs.getString  'LAST_NAME'
            numberOfCourses   = rs.getInt     'NUMBER_OF_COURSES'
        }
    }
}
