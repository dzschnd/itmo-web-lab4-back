package web.rest.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import web.rest.repository.ShotRepository;
import web.rest.http.requests.ShotCreateRequest;
import web.rest.http.resources.ShotMapper;
import web.rest.http.resources.ShotResource;
import web.rest.model.Shot;
import web.rest.model.User;
import web.rest.security.SecurityManager;

import java.util.List;

@Path("/my-shots")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserShotsController {
    @Inject
    private ShotRepository shotRepository;
    @Inject
    private SecurityManager securityManager;
    @Inject
    private ShotMapper shotMapper;

    @GET
    public List<ShotResource> retrieveAll() {
        User user = securityManager.getAuthenticatedUser();

        List<Shot> shots = shotRepository.findByUser(user);
        return shotMapper.toResourceList(shots);
    }

    @POST
    public ShotResource create(@Valid ShotCreateRequest requestData) {
        User user = securityManager.getAuthenticatedUser();

        Shot shot = Shot.builder()
                .x(requestData.getX())
                .y(requestData.getY())
                .r(requestData.getR())
                .user(user).build();
        shot = shotRepository.save(shot);

        return shotMapper.toResource(shot);
    }
}
