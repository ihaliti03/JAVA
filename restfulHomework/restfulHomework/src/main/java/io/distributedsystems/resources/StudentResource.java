package io.distributedsystems.resources;

import io.distributedsystems.models.Student;
import io.distributedsystems.services.StudentService;
import io.distributedsystems.servicesImpl.StudentServiceImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("students")
public class StudentResource extends AbstractResource {
    private final StudentService studentService = new StudentServiceImpl();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("page") @DefaultValue("1") int page) throws Exception {
        List<Student> students = studentService.get(page);
        return (!students.isEmpty()
                ? Response.ok(students).build()
                : Response.ok("No Students Found").build());
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) throws Exception {
        Student student = studentService.getById(id);
        return (student != null)
                ? Response.ok(student).build()
                : Response.ok("Student with " + id + "not found").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String payload) throws Exception {
        Student student = gson.fromJson(payload, Student.class);
        String response = studentService.create(student);
        return Response.ok(response).build();
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") String id, String payload) throws SQLException {
        Student student = gson.fromJson(payload, Student.class);
        String response = studentService.update(id, student);
        return Response.ok(response).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id) throws SQLException {
        String response = studentService.delete(id);
        return Response.ok(response).build();
    }
}





