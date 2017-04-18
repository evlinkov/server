package ru.request;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("")
public class Requests {

    private RequestDao requestDao;

    @GET
    @Path("/test")
    public Response test() {
        return Response.ok("all is ok").build();
    }

    @POST
    @Path(value = "/getCategories")
    public Response getCategories(InputStream inputStream) {
        try {
            String data = requestDao.getCategories(inputStream);
            return Response.ok(data).build();
        }
        catch (Exception error) {
            return Response.status(500).build();
        }
    }

    public Requests() {
        requestDao = new RequestRealisation();
    }

}
