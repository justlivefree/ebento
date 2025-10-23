package org.ozbeman.ebento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FileIdDTO {
    @JsonProperty("file_id")
    private UUID fileId;
}
