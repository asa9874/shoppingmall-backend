package com.shopping.common;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> { //TODO 이거 resposne 하는곳에서 전부 이걸로 바꿔야함
    private String message;
    private T data;
    private int status;
    private String error;

    // 성공 
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .message("Success")
                .data(data)
                .status(200)
                .error(null)
                .build();
    }

    // 실패
    public static <T> ApiResponse<T> error(String errorMessage, int status) {
        return ApiResponse.<T>builder()
                .message("Error")
                .data(null)
                .status(status)
                .error(errorMessage)
                .build();
    }
}