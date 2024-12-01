package org.example.postlistingservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PostCheckRequestEvent {
    @JsonProperty("userEmail")
    private String userEmail;
}
