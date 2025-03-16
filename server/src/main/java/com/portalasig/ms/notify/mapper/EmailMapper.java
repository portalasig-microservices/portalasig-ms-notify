package com.portalasig.ms.notify.mapper;

import com.portalasig.ms.notify.domain.event.EmailEvent;
import com.portalasig.ms.notify.dto.Email;
import com.portalasig.ms.notify.dto.EmailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Instant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailMapper {

    EmailRequest toRequest(EmailEvent event);

    @Mapping(target = "sentAt", expression = "java(now)")
    Email toDto(EmailRequest request, Instant now);
}