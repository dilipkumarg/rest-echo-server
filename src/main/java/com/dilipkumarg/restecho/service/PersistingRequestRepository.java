package com.dilipkumarg.restecho.service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.dilipkumarg.restecho.entities.RequestDetails;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Primary
@Slf4j
public class PersistingRequestRepository implements RequestRepository {

    private final RequestRepository delegate;
    private final ObjectMapper objectMapper;
    @Value("${app.persistence.filepath}")
    private File requestsFile;

    @Autowired
    public PersistingRequestRepository(
            final @Qualifier("inMemory") RequestRepository delegate,
            final ObjectMapper objectMapper) {
        this.delegate = delegate;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    private void init() {
        if (requestsFile.exists()) {
            try {
                final List<RequestDetails> requestDetails = objectMapper
                        .readValue(requestsFile, new TypeReference<List<RequestDetails>>() {
                        });
                log.info("Found {} requests in the file", requestDetails.size());
                requestDetails.forEach(delegate::create);

            } catch (IOException e) {
                throw new RuntimeException("Error while reading request from file", e);
            }
        } else {
            log.info("Persistent file not found");
        }
    }

    @Override
    public RequestDetails create(final RequestDetails requestDetails) {
        final RequestDetails created = delegate.create(requestDetails);
        save(fetchAll());
        return created;
    }

    @Override
    public Collection<RequestDetails> fetchAll() {
        return delegate.fetchAll();
    }

    private void save(Collection<RequestDetails> requestDetails) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(requestsFile, requestDetails);
        } catch (IOException e) {
            throw new RuntimeException("Error while persisting requests", e);
        }
    }
}
