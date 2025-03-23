package com.patizone.core_service.mapper;

import com.patizone.core_service.entity.Ad;
import com.patizone.core_service.request.RequestCreateAd;
import com.patizone.core_service.request.RequestUpdateAd;
import com.patizone.core_service.response.ResponseAd;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AdMapper {

  Ad toEntity(RequestCreateAd request);

  ResponseAd toResponse(Ad ad);

  List<ResponseAd> toResponse(List<Ad> adList);


  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateAdFromRequest(RequestUpdateAd requestUpdateAd, @MappingTarget Ad ad);

}
