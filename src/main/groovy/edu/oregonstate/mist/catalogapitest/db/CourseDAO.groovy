package edu.oregonstate.mist.catalogapitest.db

import edu.oregonstate.mist.catalogapitest.core.Course
import edu.oregonstate.mist.catalogapitest.mapper.CourseMapper
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper

@RegisterMapper(CourseMapper)
public interface CourseDAO extends Closeable {

    @SqlQuery("""
            SELECT *
            FROM COURSES
            """)
    List<Course> getAllCourses()

    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE CRN = :crn
            """)
    List<Course> findByCrn(@Bind("crn") Integer crn)

    @SqlQuery("""
            SELECT *
            FROM COURSES
            WHERE COURSENAME = :courseName
            """)
    List<Course> findByCourseName(@Bind("courseName") String courseName)

    void close()
}
