package org.doochul.application;

import org.doochul.infra.dto.Letter;

public interface MessageSendManager {
    void sendTo(final Letter letter);
}
