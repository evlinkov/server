package ru.request;

import javax.ws.rs.GET;
import ru.model.Receipt;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

@Path("")
public class Requests {

    @Inject
    ReceiptService receiptService;

    @GET
    @Path("/{data}")
    public Response test() {
        return Response.ok("all is ok").build();
    }

    @POST
    @Path(value = "/getCategories")
    @Consumes("application/xml")
    public Response getCategories(Receipt receipt) {
        try {
            //String data = requestDao.getCategories(inputStream);
            //return Response.ok(data).build();
            return null;
        }
        catch (Exception error) {
            return Response.status(500).build();
        }
    }

}
