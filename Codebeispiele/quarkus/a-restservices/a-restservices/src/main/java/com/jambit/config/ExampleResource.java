package com.jambit.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class ExampleResource {
    @ConfigProperty(name = "jambit.welcomemessage")
    String welcomeMessage;

    @Inject
    AtJavaPropertyHolder atJavaPropertyHolder;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return welcomeMessage;
    }


    @GET
    @Path("/at/java")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAtJavaProps(){
        return atJavaPropertyHolder.name() + ", " + atJavaPropertyHolder.email() + ", " + atJavaPropertyHolder.address();
    }
}