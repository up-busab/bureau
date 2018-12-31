package org.up.busab.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Enumeration;

@Path("pages")
public class MyResource
{

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt()
    {
        return "Got it!";
    }

    @POST
    public void here(@Context HttpServletRequest request, String body)
    {
        System.out.println("x==========================xxxx====================================x");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements())
        {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + " : " + request.getHeader(headerName));
        }
        System.out.println("x==========================xxxx====================================x");
        System.out.println(body);
        System.out.println("x==========================xxxx====================================x");
    }

}
