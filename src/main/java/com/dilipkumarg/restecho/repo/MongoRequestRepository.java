package com.dilipkumarg.restecho.repo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.dilipkumarg.restecho.entities.RequestDetails;

@Profile("mongo")
@Service("mongoRequestRepository")
public class MongoRequestRepository implements RequestRepository {

    private final MongoTemplate mongoTemplate;
    @Value("${app.history.entries.size}")
    private Integer entriesSize;

    @Autowired
    public MongoRequestRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public RequestDetails create(final RequestDetails requestDetails) {
        return mongoTemplate.save(requestDetails);
    }

    @Override
    public Collection<RequestDetails> fetchAll() {
        PageRequest pageable = PageRequest.of(0, entriesSize, Sort.by(Sort.Direction.DESC, "instant"));
        Query query = new Query().with(pageable);
        return mongoTemplate.find(query, RequestDetails.class);
    }
}
