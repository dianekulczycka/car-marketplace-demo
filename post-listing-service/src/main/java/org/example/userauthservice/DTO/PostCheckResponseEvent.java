package org.example.userauthservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCheckResponseEvent {
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("canMakePost")
    private boolean canMakePost;
}
