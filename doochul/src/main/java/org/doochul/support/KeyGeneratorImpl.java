package org.doochul.support;

import org.springframework.stereotype.Component;

@Component
public class KeyGeneratorImpl implements KeyGenerator {

    @Override
    public String generateAccountKey(String userToken) {
        return userToken + ":token";
    }
}
