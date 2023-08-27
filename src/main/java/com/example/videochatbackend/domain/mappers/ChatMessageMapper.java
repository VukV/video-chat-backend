package com.example.videochatbackend.domain.mappers;

import com.example.videochatbackend.domain.dtos.chatmessages.ChatMessageCreateDto;
import com.example.videochatbackend.domain.entities.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChatMessageMapper {

    ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

    ChatMessage chatMessageCreateDtoToChatMessage(ChatMessageCreateDto chatMessageCreateDto);

}
