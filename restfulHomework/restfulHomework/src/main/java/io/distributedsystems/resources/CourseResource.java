package io.distributedsystems.resources;

import io.distributedsystems.models.Course;
import io.distributedsystems.services.CourseService;
import io.distributedsystems.servicesImpl.CourseServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;


@Path("courses")
public class CourseResource extends AbstractResource {
    private final CourseService courseService = new CourseServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() throws SQLException {
        List<Course> courses = courseService.get();
        return !courses.isEmpty()
                ? Response.ok(courses).build()
                : Response.ok("No Courses Found").build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) throws SQLException {
        Course course = courseService.getById(id);
        return (course != null)
                ? Response.ok(course).build()
                : Response.ok("Course with " + id + "not found").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String payload) throws SQLException {
        Course course = gson.fromJson(payload, Course.class);
        String response = courseService.create(course);
        return Response.ok(response).build();
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, String payload) throws SQLException {
        Course course = gson.fromJson(payload, Course.class);
        String response = courseService.update(id, course);
        return Response.ok(response).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) throws SQLException {
        String response = courseService.delete(id);
        return Response.ok(response).build();
    }
}
