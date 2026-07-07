package com.ogbuilds.url_shortener_app.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlClickEvent {

    private Long urlId;

    private String shortCode;

}
