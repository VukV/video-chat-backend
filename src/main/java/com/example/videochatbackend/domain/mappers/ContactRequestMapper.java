package com.example.videochatbackend.domain.mappers;

import com.example.videochatbackend.domain.dtos.contactrequest.ContactRequestDto;
import com.example.videochatbackend.domain.entities.ContactRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContactRequestMapper {

    ContactRequestMapper INSTANCE = Mappers.getMapper(ContactRequestMapper.class);

    @Mapping(source = "contactRequest.requestId", target = "requestId")
    @Mapping(source = "contactRequest.requestSender.username", target = "senderUsername")
    @Mapping(source = "contactRequest.requestSender.firstName", target = "senderFirstName")
    ContactRequestDto contactRequestToContactRequestDto(ContactRequest contactRequest);


}
