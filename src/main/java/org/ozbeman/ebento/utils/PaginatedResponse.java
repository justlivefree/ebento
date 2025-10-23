package org.ozbeman.ebento.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private int pageCount;
    private int currentPage;
    private boolean hasNext;
    private boolean hasPrev;
    private List<T> data;

    public static <E> PaginatedResponse<E> of(Page<E> page) {
        return PaginatedResponse.<E>builder()
                .pageCount(page.getTotalPages())
                .currentPage(page.getNumber())
                .hasNext(page.hasNext())
                .hasPrev(page.hasPrevious())
                .data(page.getContent())
                .build();
    }
}
