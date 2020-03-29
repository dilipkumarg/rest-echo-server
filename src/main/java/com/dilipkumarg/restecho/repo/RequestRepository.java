package com.dilipkumarg.restecho.repo;

import java.util.Collection;

import com.dilipkumarg.restecho.entities.RequestDetails;

public interface RequestRepository {

    RequestDetails create(RequestDetails requestDetails);

    Collection<RequestDetails> fetchAll();
}
