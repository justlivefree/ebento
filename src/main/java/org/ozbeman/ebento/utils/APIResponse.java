package org.ozbeman.ebento.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {
    private boolean success;

    private T data;

    private T error;

    private Integer size;

    private Integer pageNumber;

    public static <E> APIResponse<E> ofError(E error) {
        return APIResponse.<E>builder().error(error).build();
    }

    public static <E> APIResponse<E> ofSuccess(E data) {
        return APIResponse.<E>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <E> APIResponse<E> ofSuccess(E data, Integer pageNumber, Integer size) {
        return APIResponse.<E>builder()
                .success(true)
                .data(data)
                .pageNumber(pageNumber)
                .size(size)
                .build();
    }
}
