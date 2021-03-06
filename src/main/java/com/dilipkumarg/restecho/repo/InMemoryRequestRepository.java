package com.dilipkumarg.restecho.repo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import javax.annotation.PostConstruct;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.dilipkumarg.restecho.entities.RequestDetails;

@Profile("dev")
@Service("inMemoryRequestRepository")
public class InMemoryRequestRepository implements RequestRepository {

    @Value("${app.history.entries.size}")
    private Integer entriesSize;

    @Value("${app.history.entries.expiry}")
    private Integer entriesExpire;

    private Queue<RequestDetails> queue;

    @PostConstruct
    private void init() {
        queue = new CircularFifoQueue<>(entriesSize);
    }

    @Override
    public RequestDetails create(final RequestDetails requestDetails) {
        this.queue.add(requestDetails);
        return requestDetails;
    }

    @Override
    public Collection<RequestDetails> fetchAll() {
        final ArrayList<RequestDetails> requestDetails = new ArrayList<>(this.queue);
        Collections.reverse(requestDetails);
        return requestDetails;
    }
}
