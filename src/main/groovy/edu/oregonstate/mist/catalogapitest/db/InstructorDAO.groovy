package edu.oregonstate.mist.catalogapitest.db

import edu.oregonstate.mist.catalogapitest.core.Instructor
import edu.oregonstate.mist.catalogapitest.mapper.InstructorMapper
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(InstructorMapper)
public interface InstructorDAO extends Closeable {

    // /instructor -----------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
            SELECT *
            FROM INSTRUCTORS
    """)
    List<Instructor> getAllInstructors()

    // POST
    @SqlUpdate("""INSERT INTO INSTRUCTORS (CID, FIRST_INITIAL, LAST_NAME, NUMBER_OF_COURSES)
                  values (INSTRUCTORS_SEQ.NEXTVAL, :crn, :firstInitial, :lastName, :numberOfCourses)""")
    void postInstructor(@Bind("crn") Integer crn , @Bind("firstInitial") String firstInitial,
                        @Bind("lastName") String lastName , @Bind("numberOfCourses") Integer numberOfCourses)

    // instructor/cid/ -------------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
        SELECT *
        FROM INSTRUCTORS
        WHERE CID = :cid
             """)
    Instructor getByCid(@Bind("cid") Integer cid)

    // TODO Configure it on SQLDeveloper to make sure it works!
    // Get specific instance number
    @SqlQuery("""
            SELECT INSTRUCTORS_SEQ.CURRVAL FROM DUAL
            """)
    Integer getLatestCidNumber()

    // PUT
    @SqlUpdate("""
        UPDATE INSTRUCTORS
        SET FIRST_INITIAL = :firstInitial, LAST_NAME = :lastName, NUMBER_OF_COURSES = :numberOfCourses
        WHERE CID = :cid
              """)
    void putByCid(@Bind("cid") Integer cid, @Bind("firstInitial") String firstInitial,
                  @Bind("lastName") String lastName, @Bind("numberOfCourses") Integer numberOfCourses)

    // POST
    @SqlUpdate("""
        INSERT INTO INSTRUCTORS (CID, FIRST_INITIAL, LAST_NAME, NUMBER_OF_COURSES)
        VALUES (:cid, :firstInitial, :lastName, :numberOfCourses)
              """)
    void postByCid(@Bind("cid") Integer cid, @Bind("firstInitial") String firstInitial,
                   @Bind("lastName") String lastName, @Bind("numberOfCourses") Integer numberOfCourses)

    // DELETE
    @SqlUpdate("""
        DELETE FROM INSTRUCTORS
        WHERE CID = :cid
              """)
    void deleteByCid(@Bind("cid") Integer cid)

    // instructor/lastName/ --------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
        SELECT *
        FROM INSTRUCTORS
        WHERE LAST_NAME = :lastName
            """)
    List<Instructor> getByLastName(@Bind("lastName") String lastName)

    void close()
}
