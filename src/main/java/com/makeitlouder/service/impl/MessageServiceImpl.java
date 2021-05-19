package com.makeitlouder.service.impl;

import com.makeitlouder.domain.Message;
import com.makeitlouder.repositories.MessageRepository;
import com.makeitlouder.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(int page, int limit) {
        if (page > 0)  page -= 1;

        Pageable pageRequest = PageRequest.of(page, limit);
        Page<Message> messagePage = messageRepository.findAll(pageRequest);

        return messagePage.getContent();
    }

    @Override
    public Message getMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) throw new IllegalStateException("Message not found");
        return messageRepository.findById(id).get();
    }

    @Override
    public void deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isEmpty()) throw new IllegalStateException("Message not found");
        messageRepository.deleteById(id);
    }
}
