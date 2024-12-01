package org.example.userauthservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PostCheckResponseEvent {
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("canMakePost")
    private boolean canMakePost;
}
