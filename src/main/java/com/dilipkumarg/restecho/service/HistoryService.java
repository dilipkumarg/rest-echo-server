package com.dilipkumarg.restecho.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import com.dilipkumarg.restecho.entities.RequestDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HistoryService {

    private final RequestRepository repository;
    private final ObjectMapper objectMapper;

    @Autowired
    public HistoryService(
            final RequestRepository repository, final ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public RequestDetails saveRequest(final String path, HttpServletRequest request) {
        final Map<String, List<String>> headers = Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(name -> name, it -> Collections.list(request.getHeaders(it))));

        RequestDetails requestDetails = RequestDetails.builder()
                .url(path)
                .method(request.getMethod())
                .headers(headers)
                .requestParams(request.getParameterMap())
                .body(readBody(request))
                .build();

        return this.repository.create(requestDetails);
    }

    public Collection<RequestDetails> fetchRequests() {
        return repository.fetchAll();
    }

    private Object readBody(
            final HttpServletRequest request) {
        final String bodyAsString = readBodyAsString(request);
        Object body = bodyAsString;
        if (!StringUtils.isEmpty(bodyAsString) && MediaType.APPLICATION_JSON_VALUE
                .equalsIgnoreCase(request.getContentType())) {
            try {
                body = objectMapper.readValue(bodyAsString, new TypeReference<LinkedHashMap<String, Object>>() {
                });
            } catch (JsonProcessingException e) {
                log.error("Error while converting to Map from String", e);
            }
        }
        return body;
    }

    private String readBodyAsString(HttpServletRequest request) {
        try {
            if (request.getInputStream() != null) {
                return StreamUtils.copyToString(request.getInputStream(), Charset.defaultCharset());
            }
        } catch (IOException e) {
            // ignore
            log.error("Error while reading request body", e);
        }
        return null;
    }
}
