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

    // CRN specific requests -------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE CRN = :crn
            """)
    Course getByCrn(@Bind("crn") Integer crn)

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