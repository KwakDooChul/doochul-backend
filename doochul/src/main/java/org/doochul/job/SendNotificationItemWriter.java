package org.doochul.job;

import lombok.extern.slf4j.Slf4j;
import org.doochul.service.Letter;
import org.doochul.service.MessageSendManager;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendNotificationItemWriter implements ItemWriter<Letter> {
    private final MessageSendManager messageSendManager;

    public SendNotificationItemWriter(MessageSendManager messageSendManager) {
        this.messageSendManager = messageSendManager;
    }

    @Override
    public void write(final Chunk<? extends Letter> letters) {
        for (final Letter letter : letters) {
            messageSendManager.sendTo(letter);
        }
    }
}
