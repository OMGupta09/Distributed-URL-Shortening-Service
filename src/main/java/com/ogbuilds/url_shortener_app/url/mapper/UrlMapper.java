package com.ogbuilds.url_shortener_app.url.mapper;

import com.ogbuilds.url_shortener_app.url.dto.CreateShortUrlRequest;
import com.ogbuilds.url_shortener_app.url.entity.Url;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    public Url toEntity(CreateShortUrlRequest request)
    {
        Url url=new Url();

        url.setOriginalUrl(request.getOriginalUrl());

        return url;
    }
}
