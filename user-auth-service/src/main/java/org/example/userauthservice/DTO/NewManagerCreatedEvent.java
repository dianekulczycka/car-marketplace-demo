package org.example.userauthservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewManagerCreatedEvent {
    @JsonProperty("email")
    private String email;
}

