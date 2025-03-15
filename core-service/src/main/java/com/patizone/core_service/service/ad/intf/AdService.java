package com.patizone.core_service.service.ad.intf;

import com.patizone.core_service.request.RequestCreateAd;
import com.patizone.core_service.request.RequestUpdateAd;
import com.patizone.core_service.response.ResponseAd;
import com.patizone.core_service.response.ResponsePage;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface AdService {

  void save(RequestCreateAd request);

  void delete(Long id);

  ResponseAd getById(Long id);

  ResponsePage<ResponseAd> getAll(int page, int size, String sortBy, Sort.Direction direction);

  List<ResponseAd> getAll();

  void update(Long id, RequestUpdateAd requestUpdateAd);

  //gets all ads for a specific user
  List<ResponseAd> getAllByUser(Long userId);

  ResponsePage<ResponseAd> getAllByUser(Long userId, int page, int size, String sortBy,
      Sort.Direction direction);

  //gets all ads for authenticated user
  List<ResponseAd> getAllByAuthUser();

  ResponsePage<ResponseAd> getAllByAuthUser(int page, int size, String sortBy,
      Sort.Direction direction);

}
