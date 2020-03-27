package com.dilipkumarg.restecho.entities;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDetails {

    private final Instant instant = Instant.now();
    private String url;
    private String method;
    private Map<String, String[]> requestParams;
    private Map<String, List<String>> headers;
    private Object body;
}
