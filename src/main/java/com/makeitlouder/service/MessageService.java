package com.makeitlouder.service;

import com.makeitlouder.domain.Message;

import java.util.List;

public interface MessageService {

    void sendMessage(Message message);
    List<Message> getMessages(int page, int limit);
    void deleteMessage(Long id);
    Message getMessage(Long id);
}
