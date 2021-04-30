package com.example.valsatechcorp.urlShortnerdemo.controller;

import com.example.valsatechcorp.urlShortnerdemo.service.URLShortenerService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class UrlShortenerController {

    public static final String PATH_FOR_URL_SHORTENING = "shorten-url";
    public static final String PATH_FOR_REDIRECT_URL = "retrieve-redirect-url/{id}";

    private final URLShortenerService UrlShortenerService;

    public UrlShortenerController(URLShortenerService urlShortenerService) {
        this.UrlShortenerService = urlShortenerService;
    }

    @PostMapping(PATH_FOR_URL_SHORTENING)
    public String convertToShortUrl(@RequestBody final ShortenRequest shortenRequest, HttpServletRequest request) throws Exception {
            String url = request.getRequestURL().toString();
            String shortenedUrl = UrlShortenerService.shortenURL(url, shortenRequest.getUrl());
            return shortenedUrl;
    }

    @GetMapping(PATH_FOR_REDIRECT_URL)
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException, Exception {
        String redirectUrlString = UrlShortenerService.getLongURLFromID(id);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + redirectUrlString);
        return redirectView;
    }
}

class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}




