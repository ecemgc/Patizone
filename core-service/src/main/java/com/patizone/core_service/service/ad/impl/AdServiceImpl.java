package com.patizone.core_service.service.ad.impl;

import com.patizone.core_service.mapper.AdMapper;
import com.patizone.core_service.repository.AdRepository;
import com.patizone.core_service.request.RequestCreateAd;
import com.patizone.core_service.service.ad.intf.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

  private final AdRepository repository;
  private final AdMapper mapper;

  @Override
  public void save(RequestCreateAd request) {
    repository.save(mapper.toEntity(request));
  }
}
