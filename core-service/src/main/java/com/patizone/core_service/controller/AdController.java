package com.patizone.core_service.controller;

import com.patizone.core_service.request.RequestCreateAd;
import com.patizone.core_service.service.ad.intf.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

  private final AdService adService;

  @PostMapping
  public void save(@RequestBody RequestCreateAd requestCreateAd) {
    adService.save(requestCreateAd);
  }

}
