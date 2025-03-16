package br.com.meetime.hubspotintegration.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BucketConstants {

    public static final int BUCKET_CAPACITY = 5;
    public static final Duration BUCKET_DURATION = Duration.ofSeconds(60);
    public static final int TOKENS = 1;
}
