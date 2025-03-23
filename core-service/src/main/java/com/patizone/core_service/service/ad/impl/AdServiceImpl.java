package com.patizone.core_service.service.ad.impl;

import com.patizone.core_service.entity.Ad;
import com.patizone.core_service.entity.AdType;
import com.patizone.core_service.entity.User;
import com.patizone.core_service.exceptions.BusinessException;
import com.patizone.core_service.mapper.AdMapper;
import com.patizone.core_service.repository.AdRepository;
import com.patizone.core_service.repository.AdTypeRepository;
import com.patizone.core_service.repository.UserRepository;
import com.patizone.core_service.request.RequestCreateAd;
import com.patizone.core_service.request.RequestUpdateAd;
import com.patizone.core_service.response.ResponseAd;
import com.patizone.core_service.response.ResponsePage;
import com.patizone.core_service.service.ad.intf.AdService;
import com.patizone.core_service.service.auth.intf.AuthenticatedUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

  private final AdRepository repository;
  private final AdMapper mapper;
  private final AuthenticatedUserService authenticatedUserService;
  private final UserRepository userRepository;
  private final AdTypeRepository adTypeRepository;

  @Override
  public void save(RequestCreateAd request) {
    repository.save(mapper.toEntity(request.setOwner(authenticatedUserService.getCurrentUser())));
  }

  @Override
  public void delete(Long id) {
    Ad existingAd = repository.findById(id)
        .orElseThrow(() -> new BusinessException("Ad with id %s not found".formatted(id)));
    User user = authenticatedUserService.getCurrentUser();
    if (!existingAd.getOwner().getId().equals(user.getId())) {
      throw new BusinessException(
          "You cannot delete ad with id %s, you are not the owner".formatted(id));
    }
    repository.deleteById(id);
  }

  @Override
  public ResponseAd getById(Long id) {
    return mapper.toResponse(
        repository.findById(id)
            .orElseThrow(() -> new BusinessException("Ad with id %s not found".formatted(id))));
  }

  //getAll with pagination
  @Override
  public ResponsePage<ResponseAd> getAll(int page, int size, String sortBy, Direction direction) {
    Pageable pageable;
    if (sortBy != null) {
      pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    } else {
      pageable = PageRequest.of(page, size);
    }
    Page<Ad> adPage = repository.findAll(pageable);
    return new ResponsePage<ResponseAd>()
        .setContent(mapper.toResponse(adPage.getContent()))
        .setPageNumber(adPage.getNumber())
        .setLast(adPage.isLast())
        .setPageSize(adPage.getSize())
        .setTotalPages(adPage.getTotalPages())
        .setTotalElements(adPage.getTotalElements());
  }

  //getAll without pagination
  @Override
  public List<ResponseAd> getAll() {
    return mapper.toResponse(repository.findAll());
  }

  @Override
  public void update(Long id, RequestUpdateAd requestUpdateAd) {
    Ad existingAd = repository.findById(id)
        .orElseThrow(() -> new BusinessException("Ad with id %s not found.".formatted(id)));

    Long currentUserId = authenticatedUserService.getCurrentUserId();

    if (!existingAd.getOwner().getId().equals(currentUserId)) {
      throw new BusinessException("You cannot update this ad, you are not the owner");
    }

    mapper.updateAdFromRequest(requestUpdateAd, existingAd);
    repository.save(existingAd);
  }

  @Override
  public List<ResponseAd> getAllByUser(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new BusinessException("User with id %s not found".formatted(userId));
    }
    List<Ad> adList = repository.findAllByOwnerId(userId);
    return mapper.toResponse(adList);
  }

  @Override
  public ResponsePage<ResponseAd> getAllByUser(Long userId, int page, int size, String sortBy,
      Direction direction) {
    if (!userRepository.existsById(userId)) {
      throw new BusinessException("User with id %s not found".formatted(userId));
    }
    Pageable pageable;
    if (sortBy != null) {
      pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
    } else {
      pageable = PageRequest.of(page, size);
    }
    Page<Ad> adPage = repository.findAllByOwnerId(userId, pageable);
    return new ResponsePage<ResponseAd>()
        .setContent(mapper.toResponse(adPage.getContent()))
        .setPageNumber(adPage.getNumber())
        .setTotalElements(adPage.getTotalElements())
        .setLast(adPage.isLast())
        .setTotalPages(adPage.getTotalPages())
        .setPageSize(adPage.getSize());
  }

  @Override
  public List<ResponseAd> getAllByAuthUser() {
    return getAllByUser(authenticatedUserService.getCurrentUserId());
  }

  @Override
  public ResponsePage<ResponseAd> getAllByAuthUser(int page, int size, String sortBy,
      Direction direction) {
    return getAllByUser(authenticatedUserService.getCurrentUserId(), page, size, sortBy, direction);
  }

  @Override
  public List<AdType> getAdTypes() {
    return adTypeRepository.findAll();
  }
}
