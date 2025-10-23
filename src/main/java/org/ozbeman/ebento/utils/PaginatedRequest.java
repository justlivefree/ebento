package org.ozbeman.ebento.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.ozbeman.ebento.entity.enums.UserRole;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedRequest {
    @PositiveOrZero
    private int page = 0;

    @Positive
    @Range(min = 10, max = 100)
    private int size = 10;

    @Size(min = 1, max = 10)
    @JsonProperty(value = "sort_by")
    private String sortBy;

    @Size(min = 1, max = 50)
    private String search;

    public Pageable generatePageable() {
        return PageRequest.of(page, size, Sort.by(Objects.requireNonNullElse(sortBy, "createdAt")));
    }
}
