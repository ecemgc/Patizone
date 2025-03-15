package com.patizone.core_service.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class RequestPage {

  private int page = 0;
  private int pageSize = 10;
  private String sortBy;
  private Sort.Direction direction = Sort.Direction.DESC;
}
