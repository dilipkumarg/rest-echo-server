package com.dilipkumarg.restecho.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dilipkumarg.restecho.entities.RequestDetails;
import com.dilipkumarg.restecho.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(final HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public Collection<RequestDetails> listAll() {
        return this.historyService.fetchRequests();
    }
}
