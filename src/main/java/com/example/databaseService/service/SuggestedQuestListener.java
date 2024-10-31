package com.example.databaseService.service;

import com.example.databaseService.repository.SuggestedQuestRepository;
import com.example.questModel.UserSuggestedQuest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuggestedQuestListener {

    private final SuggestedQuestRepository suggestedQuestRepository;
    private static final Logger logger = LoggerFactory.getLogger(SuggestedQuestListener.class);


    @Autowired
    public SuggestedQuestListener(SuggestedQuestRepository suggestedQuestRepository) {
        this.suggestedQuestRepository = suggestedQuestRepository;
    }

    @RabbitListener(queues = "saveQuestNoSqlQueue")
    public void saveUsersQuest(UserSuggestedQuest userSuggestedQuest) {
        logger.info("Received request to save user suggested quest: {}", userSuggestedQuest);
        suggestedQuestRepository.save(userSuggestedQuest);
        logger.info("User suggested quest saved successfully: {}", userSuggestedQuest);
    }

    @RabbitListener(queues = "getAllQuestNoSqlQueue")
    public Iterable<UserSuggestedQuest> getAllUsersQuest() {
        logger.info("Received request to get all user suggested quests");
        Iterable<UserSuggestedQuest> quests = suggestedQuestRepository.findAll();
        logger.info("Returning all user suggested quests");
        return quests;
    }

}
