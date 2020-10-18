
package be.friendbook.api.service.controllers;

import be.friendbook.api.model.ErrorMessage;
import be.friendbook.model.Profile;
import be.friendbook.repository.ProfileRepository;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaspercools
 */
@Path("/profiles")
public class ProfileController {

    @Inject
    private ProfileRepository repo;

    @Inject
    private Logger logger;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfiles() {
        logger.log(Level.INFO, "ENTRY: getProfiles");
        GenericEntity entity = new GenericEntity<List<Profile>>((List<Profile>) repo.findAll()) {
        };

        logger.log(Level.INFO, "BUILD: getProfiles response");
        return Response.ok(entity).build();
    }

    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(@PathParam("username") String username) {
        logger.log(Level.INFO, "ENTRY: getProfile response");
        logger.log(Level.INFO, "BUILD: getProfile response");
        return Response.ok(repo.findById(username)).build();
    }

    @POST
    @Path("{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(@PathParam("username") String username, Profile entity) {
        ResponseBuilder response = Response.noContent();

        Profile p = repo.findById(username);
        if (p == null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(username, "the given profile was not found")).build();
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Profile>> constraintValues = validator.validate(entity);

        if (constraintValues.size() > 0) {

            List<ErrorMessage> lstMsgs = new ArrayList<>();
            for (ConstraintViolation<Profile> constraintValue : constraintValues) {
                lstMsgs.add(new ErrorMessage(constraintValue.getPropertyPath().toString(), constraintValue.getMessage()));
            }

            GenericEntity<List<ErrorMessage>> entityRes = new GenericEntity<List<ErrorMessage>>((List<ErrorMessage>) lstMsgs) {
            };
            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(entityRes);

        } else {
            p.setUsername(entity.getUsername());
            p.setName(entity.getName());
            p.setSurname(entity.getSurname());
            repo.update(p);
            response = Response.ok(p);
        }
        
        logger.log(Level.INFO, "BUILD: updateProfile response");
        return response.build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProfile(Profile entity) throws URISyntaxException {
        ResponseBuilder response = Response.noContent();

        Profile p = repo.findById(entity.getUsername());
        if (p != null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(entity.getUsername(), "Another profile with the same username already exists")).build();
        }

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Profile>> constraintValues = validator.validate(entity);

        if (constraintValues.size() > 0) {
            logger.log(Level.WARNING, String.format("VALIDATION: user validation constraints %s",entity));
            List<ErrorMessage> lstMsgs = new ArrayList<>();
            for (ConstraintViolation<Profile> constraintValue : constraintValues) {
                lstMsgs.add(new ErrorMessage(constraintValue.getPropertyPath().toString(), constraintValue.getMessage()));
            }

            GenericEntity<List<ErrorMessage>> entityRes = new GenericEntity<List<ErrorMessage>>((List<ErrorMessage>) lstMsgs) {
            };
            response = Response.status(Response.Status.NOT_ACCEPTABLE).entity(entityRes);

        } else {
            repo.insert(entity);
            response = Response.created(new URI("profiles/" + entity.getUsername()));
        }

        logger.log(Level.INFO, "BUILD: createProfile response");
        return response.build();
    }

    @DELETE
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProfile(@PathParam("username") String username) {
        ResponseBuilder response = Response.noContent();

        Profile p = repo.findById(username);
        if (p == null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(username, "the given profile was not found")).build();
        }

        repo.delete(p);
        response = Response.ok();
        logger.log(Level.INFO, "BUILD: deleteeProfile response");
        return response.build();
    }

    @PUT
    @Path("{username}/friends")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getlFriends(@PathParam("username") String username, Profile friend) throws URISyntaxException {
        ResponseBuilder response = Response.noContent();
        Profile p = repo.findById(username);
        if (p == null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(username, "the given profile was not found")).build();
        }

        repo.addFriend(p, friend);

        response = Response.created(new URI("profiles/" + username + "/friends/" + friend.getUsername()));
        logger.log(Level.INFO, "BUILD: getlFriends response");
        return response.build();
    }

    @GET
    @Path("{username}/friends")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addFriend(@PathParam("username") String username) throws URISyntaxException {
        ResponseBuilder response = Response.noContent();
        Profile p = repo.findById(username);
        if (p == null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(username, "the given profile was not found")).build();
        }

        GenericEntity<Set<Profile>> entityRes = new GenericEntity<Set<Profile>>((Set<Profile>) repo.getFriends(p)) {
        };

        response = Response.ok(entityRes);
        
        logger.log(Level.INFO, "BUILD: addFriend response");
        return response.build();
    }

    @DELETE
    @Path("{username}/friends/{frienduname}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFriend(@PathParam("username") String username, @PathParam("frienduname") String frienduname) throws URISyntaxException {
        ResponseBuilder response = Response.noContent();
        Profile p = repo.findById(username);
        if (p == null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(username, "the given profile was not found")).build();
        }

        Profile friend = repo.findById(frienduname);

        if (friend == null) {
            return response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(frienduname, "the given friend was not found")).build();
        }

        repo.removeFriend(p, friend);

        response = Response.created(new URI("profiles/" + username + "/friends/" + frienduname));

        logger.log(Level.INFO, "BUILD: deleteFriend response");
        return response.build();
    }
}
