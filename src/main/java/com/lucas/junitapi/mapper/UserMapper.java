package com.lucas.junitapi.mapper;

import com.lucas.junitapi.entity.User;
import com.lucas.junitapi.request.UserPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
	@Mapping(target = "id", ignore = true)
	User toUser(UserPostRequestBody userPostRequestBody);
}