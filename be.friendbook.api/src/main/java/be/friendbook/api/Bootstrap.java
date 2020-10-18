package be.friendbook.api;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 *
 * @author kaspercools
 */
@ApplicationPath("api")
public class Bootstrap extends ResourceConfig{

   
    public Bootstrap() {
        register(be.friendbook.api.service.controllers.ProfileController.class);
        register(be.friendbook.filters.CORSResponseFilter.class);
    }   
}