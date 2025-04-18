package com.patizone.core_service.request;

import com.patizone.core_service.entity.AdType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class RequestUpdateAd {

  private String title;
  private String address;
  private String description;
  private String animalType;
  private String animalName;
  private String animalBreed;
  private Integer animalAge;
  private String imageUrl;
  private LocalDateTime endDate;
  private AdType adType;

}
