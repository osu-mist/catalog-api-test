package edu.oregonstate.mist.catalogapitest.db

import edu.oregonstate.mist.catalogapitest.core.Instructor
import edu.oregonstate.mist.catalogapitest.mapper.InstructorMapper
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper


/**
 * Instructor database access object class.
 */
@RegisterMapper(InstructorMapper)
public interface InstructorDAO extends Closeable {

    /**
     * GETs all instructor objects.
     *
     * @return List of all instructors found, otherwise empty
     */
    @SqlQuery("""
            SELECT *
            FROM INSTRUCTORS
    """)
    List<Instructor> getAllInstructors()

    /**
     * POSTs an instructor object.
     *
     * @param firstInitial
     * @param lastName
     * @param numberOfCourses
     */
    @SqlUpdate("""INSERT INTO INSTRUCTORS (CID, FIRST_INITIAL, LAST_NAME, NUMBER_OF_COURSES)
                  values (INSTRUCTORS_SEQ.NEXTVAL, :firstInitial, :lastName, :numberOfCourses)""")
    void postInstructor(@Bind("firstInitial") String firstInitial,
                        @Bind("lastName") String lastName,
                        @Bind("numberOfCourses") Integer numberOfCourses)

    /**
     * GETs an instructor by the cid number
     *
     * @param cid
     * @return Instructor object
     */
    @SqlQuery("""
        SELECT *
        FROM INSTRUCTORS
        WHERE CID = :cid
             """)
    Instructor getByCid(@Bind("cid") Integer cid)

    /**
     * GETs specific instructor object instance number using Oracle sequences.
     *
     * @return Updated sequence number
     */
    @SqlQuery("""
            SELECT INSTRUCTORS_SEQ.CURRVAL FROM DUAL
            """)
    Integer getLatestCidNumber()

    /**
     * PUTs an instructor object.
     *
     * @param cid
     * @param firstInitial
     * @param lastName
     * @param numberOfCourses
     */
    @SqlUpdate("""
        UPDATE INSTRUCTORS
        SET FIRST_INITIAL = :firstInitial, LAST_NAME = :lastName, NUMBER_OF_COURSES = :numberOfCourses
        WHERE CID = :cid
              """)
    void putByCid(@Bind("cid") Integer cid,
                  @Bind("firstInitial") String firstInitial,
                  @Bind("lastName") String lastName,
                  @Bind("numberOfCourses") Integer numberOfCourses)

    /**
     * POSTs an instructor object.
     *
     * @param cid
     * @param firstInitial
     * @param lastName
     * @param numberOfCourses
     */
    @SqlUpdate("""
        INSERT INTO INSTRUCTORS (CID, FIRST_INITIAL, LAST_NAME, NUMBER_OF_COURSES)
        VALUES (:cid, :firstInitial, :lastName, :numberOfCourses)
              """)
    void postByCid(@Bind("cid") Integer cid,
                   @Bind("firstInitial") String firstInitial,
                   @Bind("lastName") String lastName,
                   @Bind("numberOfCourses") Integer numberOfCourses)

    /**
     * DELETEs an instructor object.
     *
     * @param cid
     */
    @SqlUpdate("""
        DELETE FROM INSTRUCTORS
        WHERE CID = :cid
              """)
    void deleteByCid(@Bind("cid") Integer cid)

    /**
     * GETs an instructor object through query with lastName
     *
     * @param lastName
     * @return List of all instructors found, otherwise empty
     */
    @SqlQuery("""
        SELECT *
        FROM INSTRUCTORS
        WHERE LAST_NAME = :lastName
            """)
    List<Instructor> getByLastName(@Bind("lastName") String lastName)

    void close()
}
