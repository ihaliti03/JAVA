package io.distributedsystems.resources;

import io.distributedsystems.models.Course;
import io.distributedsystems.models.Student;
import io.distributedsystems.services.EnrollmentService;
import io.distributedsystems.servicesImpl.EnrollmentServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;


public class EnrollmentResource extends AbstractResource {
    private final EnrollmentService enrollmentService = new EnrollmentServiceImpl();

    @Path("students/{id}/courses{cid}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
            @PathParam("id") String studentId,
            @PathParam("cid") String courseId) throws SQLException {
        String response = enrollmentService.create(studentId, courseId);
        return Response.ok(response).build();
    }

    @Path("students/{id}/courses{cid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String studentId, @PathParam("cid") String courseId) throws SQLException {
        String response = enrollmentService.delete(studentId, courseId);
        return Response.ok(response).build();
    }

    @Path("/students/{id}/courses")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCourses(@PathParam("id") String studentId) throws SQLException {
        List<Course> courses = enrollmentService.getStudentsEnrolledCourses(studentId);
        return (!courses.isEmpty())
                ? Response.ok(courses).build()
                : Response.ok("Student isn't enrolled in any course").build();
    }

    @Path("/courses/{id}/students")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents(@PathParam("id") String courseId) throws SQLException {
        List<Student> students = enrollmentService.getCoursesEnrolledStudents(courseId);
        return (!students.isEmpty())
                ? Response.ok(students).build()
                : Response.ok("No student has enrolled the course").build();
    }

}
