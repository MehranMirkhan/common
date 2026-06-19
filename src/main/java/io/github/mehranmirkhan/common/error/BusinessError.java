package io.github.mehranmirkhan.common.error;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;

/**
 * The parent class for all error responses.
 * 
 * @param code A unique error code
 * @param message A human-readable error message
 * @param timestamp The timestamp when the error occurred
 * @param uuid A unique identifier for the error instance
 * @param requestUrl The URL of the request that caused the error
 */
@Builder
public record BusinessError(String code, String message, Instant timestamp, UUID uuid,
    String requestUrl) {
}
