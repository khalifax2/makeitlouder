package com.makeitlouder.ui.controllers;

import com.makeitlouder.domain.Message;
import com.makeitlouder.service.MessageService;
import com.makeitlouder.ui.model.response.OperationalStatusModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MesssageController {

    private final MessageService messageService;

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        messageService.sendMessage(message);
    }

    @GetMapping
    public List<Message> getMessages(@RequestParam(value = "page") int page,
                                     @RequestParam(value = "limit") int limit) {
        return messageService.getMessages(page, limit);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {
        return messageService.getMessage(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public OperationalStatusModel deletePet(@PathVariable Long id) {
        messageService.deleteMessage(id);
        OperationalStatusModel operation = OperationalStatusModel.builder()
                .operationName("DELETE").operationResult("SUCCESS").build();

        return operation;
    }

}
