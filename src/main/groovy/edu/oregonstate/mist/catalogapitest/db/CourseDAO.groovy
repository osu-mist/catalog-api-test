package edu.oregonstate.mist.catalogapitest.db

import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.mapper.CourseMapper
import io.dropwizard.jersey.params.IntParam
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(CourseMapper)
public interface CourseDAO extends Closeable {

    // Get all courses -------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
            SELECT *
            FROM COURSES
            """)
    List<Course> getAllCourses()

    // POST
    @SqlUpdate("""INSERT INTO COURSES (CID, CRN, COURSENAME, INSTRUCTOR, DAY, TIME, LOCATION)
                  values (COURSES_SEQ.NEXTVAL, :crn, :courseName, :instructor, :day, :time, :location)""")
    void postCourse(@Bind("crn") Integer crn , @Bind("courseName") String courseName , @Bind("instructor") String instructor,
                   @Bind("day") String day, @Bind("time") String time, @Bind("location") String location)

    // CRN specific requests -------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE CRN = :crn
            """)
    Course getByCrn(@Bind("crn") Integer crn)
<<<<<<< HEAD

    // Get specific instance number
    @SqlQuery("""
            SELECT COURSES_SEQ.CURRVAL FROM DUAL
            """)
    Integer getLatestCidNumber()

    // PUT
    @SqlUpdate( """
              UPDATE COURSES
              SET COURSENAME = :courseName, INSTRUCTOR = :instructor, DAY = :day, TIME = :time, LOCATION = :location
              WHERE CRN = :crn
              """)
    void putByCrn(@Bind("crn") Integer crn, @Bind("courseName") String courseName, @Bind("instructor") String instructor, @Bind("day") String day,
                  @Bind("time") String time, @Bind("location") String location)
    
    // POST
    @SqlUpdate("""INSERT INTO COURSES (CRN, COURSENAME, INSTRUCTOR, DAY, TIME, LOCATION)
                  values (:crn, :courseName, :instructor, :day, :time, :location)""")
    void postByCrn(@Bind("crn") Integer crn , @Bind("courseName") String courseName , @Bind("instructor") String instructor,
                   @Bind("day") String day, @Bind("time") String time, @Bind("location") String location)

    // DELETE
    @SqlUpdate( """
              DELETE FROM COURSES
              WHERE CRN = :crn
              """)
    void deleteByCrn(@Bind("crn") Integer crn)

    // Name specific requests -------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE COURSENAME = :courseName
            """)
    List<Course> getByName(@Bind("courseName") String courseName)

    // Location specific requests -------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE LOCATION = :location
            """)
    List<Course> getByLocation(@Bind("location") String location)

    void close()
}
