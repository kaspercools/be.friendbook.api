package be.friendbook.api;

import be.friendbook.filters.CORSResponseFilter;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

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