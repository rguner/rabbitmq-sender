package com.guner.queue;

import com.guner.model.ChargingRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RabbitMqSingleSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${single-sender.topic-exchange.name}")
    private String topicExchange;

    @Value("${single-sender.routing.key.single-routing}")
    private String routingKeySingle;

    //
    public void messageSingleSend(ChargingRecord chargingRecord) {
        String sourceGsm = chargingRecord.getSourceGsm();
        IntStream.range(0, 1001).forEach(i -> {
            chargingRecord.setSourceGsm(sourceGsm + "_" + i);
            rabbitTemplate.convertAndSend(topicExchange, routingKeySingle, chargingRecord);
        });
    }
}
