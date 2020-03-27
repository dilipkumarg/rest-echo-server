package com.dilipkumarg.restecho.service;

import java.util.Collection;

import com.dilipkumarg.restecho.entities.RequestDetails;

public interface RequestRepository {

    RequestDetails create(RequestDetails requestDetails);

    Collection<RequestDetails> fetchAll();
}
