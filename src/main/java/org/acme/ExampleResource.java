package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/sum/{number1}/{number2}")
    public String sum(@PathParam("number1") Integer number1,@PathParam("number2") Integer number2) {
        StringBuilder sb = new StringBuilder();
        sb.append(number1).append(" + ").append(number2).append(" = ").append(number1+number2);
        return sb.toString();
    }
}