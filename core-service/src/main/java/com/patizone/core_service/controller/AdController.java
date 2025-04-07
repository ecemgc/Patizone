package com.patizone.core_service.controller;

import com.patizone.core_service.entity.AdType;
import com.patizone.core_service.request.RequestCreateAd;
import com.patizone.core_service.request.RequestPage;
import com.patizone.core_service.request.RequestUpdateAd;
import com.patizone.core_service.response.ResponseAd;
import com.patizone.core_service.response.ResponsePage;
import com.patizone.core_service.service.ad.intf.AdService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

  private final AdService adService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void save(@RequestBody RequestCreateAd requestCreateAd) {
    adService.save(requestCreateAd);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    adService.delete(id);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ResponseAd getById(@PathVariable Long id) {
    return adService.getById(id);
  }

  @GetMapping("/paginated")
  @ResponseStatus(HttpStatus.OK)
  public ResponsePage<ResponseAd> getAll(RequestPage requestPage) {
    return adService.getAll(requestPage.getPage(), requestPage.getPageSize(),
        requestPage.getSortBy(), requestPage.getDirection());
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ResponseAd> getAll() {
    return adService.getAll();
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable Long id, @RequestBody RequestUpdateAd requestUpdateAd) {
    adService.update(id, requestUpdateAd);
  }

  //gets all ads with specific user

  @GetMapping("/user/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public List<ResponseAd> getAllByUser(@PathVariable Long userId) {
    return adService.getAllByUser(userId);
  }

  @GetMapping("/user/{userId}/paginated")
  @ResponseStatus(HttpStatus.OK)
  public ResponsePage<ResponseAd> getAllByUser(@PathVariable Long userId, RequestPage requestPage) {
    return adService.getAllByUser(userId, requestPage.getPage(), requestPage.getPageSize(),
        requestPage.getSortBy(), requestPage.getDirection());
  }

  //gets all ads of authenticated user
  @GetMapping("/authUser")
  public List<ResponseAd> getAllByAuthUser() {
    return adService.getAllByAuthUser();
  }

  @GetMapping("/authUser/paginated")
  public ResponsePage<ResponseAd> getAllByAuthUser(RequestPage requestPage) {
    return adService.getAllByAuthUser(requestPage.getPage(), requestPage.getPageSize(),
        requestPage.getSortBy(), requestPage.getDirection());
  }
  @GetMapping("/types")
  public List<AdType> getAdTypes() {
    return adService.getAdTypes();
  }
}
