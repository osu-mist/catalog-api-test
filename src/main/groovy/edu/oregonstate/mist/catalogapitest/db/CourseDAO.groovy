package edu.oregonstate.mist.catalogapitest.db

import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.mapper.CourseMapper
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

/**
 * Course database access object class.
 */
@RegisterMapper(CourseMapper)
public interface CourseDAO extends Closeable {

    // /course ---------------------------------------------------------------------------------------------------------

    /**
     * GETs all course objects.
     *
     * @return List of all courses found, otherwise empty
     */
    @SqlQuery("""
            SELECT *
            FROM COURSES
            """)
    List<Course> getAllCourses()

    /**
     * POSTs a course object.
     *
     * @param crn
     * @param courseName
     * @param instructor
     * @param day
     * @param time
     * @param location
     */
    @SqlUpdate("""INSERT INTO COURSES (CID, CRN, COURSENAME, INSTRUCTOR, DAY, TIME, LOCATION)
                  values (COURSES_SEQ.NEXTVAL, :crn, :courseName, :instructor, :day, :time, :location)""")
    void postCourse(@Bind("crn") Integer crn , @Bind("courseName") String courseName , @Bind("instructor") String instructor,
                   @Bind("day") String day, @Bind("time") String time, @Bind("location") String location)

    // /course/{crn} ---------------------------------------------------------------------------------------------------

    /**
     * GETs a course by the crn number.
     *
     * @param crn
     * @return Course object
     */
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE CRN = :crn
            """)
    Course getByCrn(@Bind("crn") Integer crn)

    /**
     * GETs specific course object instance number using Oracle sequences.
     *
     * @return Updated sequence number
     */
    @SqlQuery("""
            SELECT COURSES_SEQ.CURRVAL FROM DUAL
            """)
    Integer getLatestCidNumber()

    /**
     * PUTs a course object.
     *
     * @param crn
     * @param courseName
     * @param instructor
     * @param day
     * @param time
     * @param location
     */
    @SqlUpdate( """
              UPDATE COURSES
              SET COURSENAME = :courseName, INSTRUCTOR = :instructor, DAY = :day, TIME = :time, LOCATION = :location
              WHERE CRN = :crn
              """)
    void putByCrn(@Bind("crn") Integer crn, @Bind("courseName") String courseName, @Bind("instructor") String instructor,
                  @Bind("day") String day,  @Bind("time") String time, @Bind("location") String location)

    /**
     * POSTs a course object.
     *
     * @param crn
     * @param courseName
     * @param instructor
     * @param day
     * @param time
     * @param location
     */
    @SqlUpdate("""
        INSERT INTO COURSES (CRN, COURSENAME, INSTRUCTOR, DAY, TIME, LOCATION)
        values (:crn, :courseName, :instructor, :day, :time, :location)
              """)
    void postByCrn(@Bind("crn") Integer crn , @Bind("courseName") String courseName , @Bind("instructor") String instructor,
                   @Bind("day") String day, @Bind("time") String time, @Bind("location") String location)

    /**
     * DELETEs a course object.
     *
     * @param crn
     */
    @SqlUpdate("""
              DELETE FROM COURSES
              WHERE CRN = :crn
              """)
    void deleteByCrn(@Bind("crn") Integer crn)

    // course/name/{courseName} ----------------------------------------------------------------------------------------

    /**
     * GETs a course object through query with name.
     *
     * @param courseName
     * @return list of courses found, otherwise empty.
     */
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE COURSENAME = :courseName
            """)
    List<Course> getByName(@Bind("courseName") String courseName)

    // course/location/{location} --------------------------------------------------------------------------------------

    /**
     * GETs a course object through query with its location.
     *
     * @param location
     * @return list of courses found, otherwise empty.
     */
    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE LOCATION = :location
            """)
    List<Course> getByLocation(@Bind("location") String location)

    void close()
}
