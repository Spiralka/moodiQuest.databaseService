package com.example.databaseService.service;

import com.example.databaseService.repository.SuggestedQuestRepository;
import com.example.questModel.UserSuggestedQuest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuggestedQuestListener {

    private final SuggestedQuestRepository suggestedQuestRepository;

    @Autowired
    public SuggestedQuestListener(SuggestedQuestRepository suggestedQuestRepository) {
        this.suggestedQuestRepository = suggestedQuestRepository;
    }

    @RabbitListener(queues = "saveQuestNoSqlQueue")
    public void saveUsersQuest(UserSuggestedQuest userSuggestedQuest) {
        suggestedQuestRepository.save(userSuggestedQuest);
    }

    @RabbitListener(queues = "getAllQuestNoSqlQueue")
    public Iterable<UserSuggestedQuest> getAllUsersQuest() {
        return suggestedQuestRepository.findAll();
    }

}
