package org.doochul.service;

public interface MessageSendManager {
    void sendTo(String targetToken, Letter letter);
}
