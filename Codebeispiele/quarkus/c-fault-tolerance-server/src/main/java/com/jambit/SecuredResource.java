package com.jambit;

import io.quarkus.security.Authenticated;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/secured")
public class SecuredResource {
    @GET
    @Path("/public")
    @Produces(MediaType.APPLICATION_JSON)
    public String publicResource(){
        return "public accesss";
    }

    @GET
    @Path("/authenticated")
    @Produces(MediaType.APPLICATION_JSON)
    @Authenticated
    public String authenticatedAccess(){
        return "you are authenticated";
    }

    @GET
    @Path("/adminOnly")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin"})
    public String adminsOnly(){
        return "you are a proven admin";
    }

}
