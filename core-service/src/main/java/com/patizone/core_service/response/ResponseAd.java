package com.patizone.core_service.response;

import com.patizone.core_service.entity.AdType;
import com.patizone.core_service.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseAd {

  private Long id;
  private String title;
  private ResponseUser owner;
  private String address;
  private String description;
  private String animalType;
  private String animalName;
  private String animalBreed;
  private Integer animalAge;
  private String imageUrl;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private AdType adType;
}
