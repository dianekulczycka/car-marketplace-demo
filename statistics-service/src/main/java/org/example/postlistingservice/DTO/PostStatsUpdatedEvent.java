package org.example.postlistingservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostStatsUpdatedEvent {
    @JsonProperty("postId")
    private Integer postId;

    public Integer postId() {
        return this.postId;
    }
}

