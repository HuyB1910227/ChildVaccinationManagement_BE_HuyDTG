package vn.huydtg.immunizationservice.service.mapper;

import org.mapstruct.Mapper;
import vn.huydtg.immunizationservice.domain.NotificationToken;
import vn.huydtg.immunizationservice.service.dto.NotificationTokenDTO;

@Mapper(componentModel = "spring")
public interface NotificationTokenMapper extends EntityMapper<NotificationTokenDTO, NotificationToken> {
}
