package com.dilipkumarg.restecho.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import com.dilipkumarg.restecho.entities.RequestDetails;
import com.dilipkumarg.restecho.service.HistoryService;

@RestController
@RequestMapping("/echo")
public class EchoController {

    private final HistoryService historyService;

    @Autowired
    public EchoController(final HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping(path = "/**")
    public RequestDetails echo(HttpServletRequest request) {
        String path = (String) request.getAttribute(
                HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

        return this.historyService.saveRequest(finalPath, request);
    }
}
