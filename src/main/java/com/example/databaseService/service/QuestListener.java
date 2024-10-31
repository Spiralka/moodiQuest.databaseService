package com.example.databaseService.service;

import com.example.databaseService.repository.QuestRepository;
import com.example.questModel.Quest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestListener {

    private final QuestRepository questRepository;
    private static final Logger logger = LoggerFactory.getLogger(QuestListener.class);


    @Autowired
    public QuestListener(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @RabbitListener(queues = "questRequestQueue")
    public List<Quest> handleDailyQuestsRequest(String message) {
        logger.info("Received request for daily quests: {}", message);
        List<Quest> quests = questRepository.findRandomDailyQuest();
        logger.info("Returning {} daily quests", quests.size());
        return quests;
    }

    @RabbitListener(queues = "questByIdQueue")
    public Quest handleQuestByIdRequest(Long id) {
        logger.info("Received request for quest by id: {}", id);
        Optional<Quest> quest = questRepository.findById(id);
        if (quest.isPresent()) {
            logger.info("Quest found: {}", quest.get());
        } else {
            logger.warn("Quest with id {} not found", id);
        }
        return quest.orElse(null);
    }

    @RabbitListener(queues = "questRandomQueue")
    public Quest handleRandomQuestRequest(String message) {
        logger.info("Received request for random quest: {}", message);
        Quest quest = questRepository.findRandomQuest();
        logger.info("Returning random quest: {}", quest);
        return quest;
    }

    @RabbitListener(queues = "questAddQueue")
    public Quest addQuest(Quest quest) {
        logger.info("Received request to add quest: {}", quest);
        Quest savedQuest = questRepository.save(quest);
        logger.info("Quest added successfully: {}", savedQuest);
        return savedQuest;
    }

    @RabbitListener(queues = "questUpdateQueue")
    public String updateQuest(Quest quest) {
        logger.info("Received request to update quest: {}", quest);
        Optional<Quest> existingQuest = questRepository.findById(quest.getId());

        if (existingQuest.isPresent()) {
            Quest questToUpdate = existingQuest.get();
            questToUpdate.setShortName(quest.getShortName());
            questToUpdate.setDescription(quest.getDescription());
            questToUpdate.setProgressNumber(quest.getProgressNumber());

            questRepository.save(questToUpdate);
            logger.info("Quest updated successfully: {}", questToUpdate);
            return "Quest updated successfully";
        } else {
            logger.warn("Quest with id {} not found", quest.getId());
            return "Quest not found";
        }
    }


    @RabbitListener(queues = "questDeleteQueue")
    public String deleteQuest(Long id) {
        logger.info("Received request to delete quest with id: {}", id);
        Optional<Quest> existingQuest = questRepository.findById(id);

        if (existingQuest.isPresent()) {
            questRepository.deleteById(id);
            logger.info("Quest with id {} has been deleted", id);
            return "Quest deleted successfully";
        } else {
            logger.warn("Quest with id {} not found", id);
            return "Quest not found";
        }
    }

}
