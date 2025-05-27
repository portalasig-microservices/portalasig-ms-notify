package com.portalasig.ms.notify.mapper;

import com.portalasig.ms.notify.domain.event.EmailEvent;
import com.portalasig.ms.notify.dto.Email;
import com.portalasig.ms.notify.dto.EmailRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Instant;

/**
 * MapStruct mapper interface for converting between {@link EmailEvent}, {@link EmailRequest}, and {@link Email}.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailMapper {

    /**
     * Converts an {@link EmailEvent} into an {@link EmailRequest}.
     *
     * @param event the email event to convert
     * @return the corresponding email request
     */
    EmailRequest toRequest(EmailEvent event);

    /**
     * Converts an {@link EmailRequest} into an {@link Email} DTO and assigns the current timestamp.
     *
     * @param request the email request
     * @param now     the timestamp representing when the email was sent
     * @return the email DTO
     */
    @Mapping(target = "sentAt", expression = "java(now)")
    Email toDto(EmailRequest request, Instant now);
}