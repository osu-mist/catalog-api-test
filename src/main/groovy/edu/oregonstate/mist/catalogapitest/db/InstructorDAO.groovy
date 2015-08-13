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
                  values (INSTRUCTORS_SEQ.NEXTVAL, :crn, :first_initial, :last_name, :number_of_courses)""")
    void postInstructor(@Bind("crn") Integer crn , @Bind("first_initial") String first_initial,
                        @Bind("last_name") String last_name , @Bind("number_of_courses") Integer number_of_courses)

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
        SET FIRST_INITIAL = :first_initial, LAST_NAME = :last_name, NUMBER_OF_COURSES = :number_of_courses
        WHERE CID = :cid
              """)
    void putByCid(@Bind("cid") Integer cid, @Bind("first_initial") String first_initial,
                  @Bind("last_name") String last_name, @Bind("number_of_courses") Integer number_of_courses)

    // POST
    @SqlUpdate("""
        INSERT INTO INSTRUCTORS (CID, FIRST_INITIAL, LAST_NAME, NUMBER_OF_COURSES)
        VALUES (:cid, :first_initial, :last_name, :number_of_courses)
              """)
    void postByCid(@Bind("cid") Integer cid, @Bind("first_initial") String first_initial,
                   @Bind("last_name") String last_name, @Bind("number_of_courses") Integer number_of_courses)

    // DELETE
    @SqlUpdate("""
        DELETE FROM INSTRUCTORS
        WHERE CID = :cid
              """)
    void deleteByCid(@Bind("cid") Integer cid)

    // instructor/last_name/ -------------------------------------------------------------------------------------------

    // GET
    @SqlQuery("""
        SELECT *
        FROM INSTRUCTORS
        WHERE LAST_NAME = :last_name
            """)
    List<Instructor> getByLastName(@Bind("last_name") String last_name)

}
