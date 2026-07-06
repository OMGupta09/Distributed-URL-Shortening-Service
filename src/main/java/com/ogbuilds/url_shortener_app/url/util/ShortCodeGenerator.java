package com.ogbuilds.url_shortener_app.url.util;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;

@NoArgsConstructor
public final class ShortCodeGenerator {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int SHORT_CODE_LENGTH=7;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate()
    {
        StringBuilder shortCode= new StringBuilder(SHORT_CODE_LENGTH);

        for(int i=0; i<SHORT_CODE_LENGTH; i++)
        {
            shortCode.append(
                    CHARACTERS.charAt(
                            RANDOM.nextInt(CHARACTERS.length())
                    )
            );
        }

        return shortCode.toString();
    }


}
