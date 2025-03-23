package com.patizone.core_service.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestGetUsers {
    @JsonProperty("sortByOnline")
    private boolean sortByOnline;
}
