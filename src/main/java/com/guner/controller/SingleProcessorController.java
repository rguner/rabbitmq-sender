package com.guner.controller;

import com.guner.model.MessageBody;
import com.guner.service.SingleProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SingleProcessorController {

    private final SingleProcessorService singleProcessorService;

    @PostMapping("/sendMessage")
    public boolean sendMessage(@RequestBody MessageBody messageBody) {
        return singleProcessorService.process(messageBody);
    }
}