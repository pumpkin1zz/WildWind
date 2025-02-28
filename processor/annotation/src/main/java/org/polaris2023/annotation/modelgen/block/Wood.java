package org.polaris2023.annotation.modelgen.block;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : baka4n
 * {@code @Date : 2025/02/28 19:08:19}
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Wood {
    boolean item() default true;
}
