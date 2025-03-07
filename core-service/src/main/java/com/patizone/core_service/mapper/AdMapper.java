package com.patizone.core_service.mapper;

import com.patizone.core_service.entity.Ad;
import com.patizone.core_service.request.RequestCreateAd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AdMapper {

  Ad toEntity(RequestCreateAd request);

}
