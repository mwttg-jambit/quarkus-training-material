package at.javatraining.configdemo;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/configured")
public class ConfiguredResource {
    @ConfigProperty(name="at.javatraining.greeting")
    String greeting;

    @ConfigProperty(name="at.javatraining.dbhost")
    String dbhost;

    @Inject
    MultipleProperties multipleProperties;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hello")
    public String sayHello(){
        return greeting;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dbhost")
    public String getDbHost(){
        return dbhost;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/multiple/props")
    public String getMultipleProps(){
        return multipleProperties.allProps();
    }
}
