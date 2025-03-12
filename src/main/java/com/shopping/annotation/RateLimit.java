package com.shopping.annotation; 

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)         // 메서드에만 적용
@Retention(RetentionPolicy.RUNTIME)  // 런타임까지 유지
public @interface RateLimit {
    long value() default 5;         // 초당 허용 요청 수
    long timeWindow() default 1;    // 제한 시간 (초)
}
