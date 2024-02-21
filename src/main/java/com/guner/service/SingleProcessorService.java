package com.guner.service;

import com.guner.model.ChargingRecord;
import com.guner.model.MessageBody;
import com.guner.queue.RabbitMqSingleSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class SingleProcessorService {

    private final RabbitMqSingleSender rabbitMqSingleSender;
    public boolean process(MessageBody messageBody) {

        if (messageBody.getTransactionDate() == null ) {
            messageBody.setTransactionDate(LocalDateTime.now());
        }
        ChargingRecord chargingRecord = ChargingRecord.builder()
                .sourceGsm(messageBody.getSourceGsm())
                .targetGsm(messageBody.getTargetGsm())
                .transactionDate(java.util.Date
                        .from(messageBody.getTransactionDate().atZone(ZoneId.systemDefault())
                                .toInstant()))
                .build();

        rabbitMqSingleSender.messageSingleSend(chargingRecord);

        return true;
    }
}

