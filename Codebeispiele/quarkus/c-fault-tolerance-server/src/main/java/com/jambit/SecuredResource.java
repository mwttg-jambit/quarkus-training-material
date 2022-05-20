package com.jambit;

import io.quarkus.security.Authenticated;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.Claims;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Optional;

@Path("/secured")
@Slf4j
public class SecuredResource {
    @Inject
    SecurityContext ctx;

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
        DefaultJWTCallerPrincipal userPrincipal = (DefaultJWTCallerPrincipal)ctx.getUserPrincipal();
        String birthdate = userPrincipal.claim(Claims.birthdate).orElse("").toString();

        String name = userPrincipal.getName();
        log.info("Username: " + name);
        log.info("Birthdate: " + birthdate);
        boolean isUser = ctx.isUserInRole("User");
        log.info("has role User? " + isUser);
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
