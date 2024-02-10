package web.rest.http.resources;

import org.mapstruct.Mapper;
import web.rest.model.Shot;

import java.util.List;

@Mapper
public interface ShotMapper {
    ShotResource toResource(Shot shot);

    List<ShotResource> toResourceList(List<Shot> shots);
}
