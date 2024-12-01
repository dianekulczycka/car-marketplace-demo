package org.example.postlistingservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PendingPostCreationEvent {
    @JsonProperty("postId")
    private long postId;
}