package com.example.databaseService.service;

import com.example.databaseService.repository.QuestRepository;
import com.example.questModel.Quest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class QuestListener {

    private final QuestRepository questRepository;

    @Autowired
    public QuestListener(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @RabbitListener(queues = "questRequestQueue")
    public List<Quest> handleDailyQuestsRequest(String message) {
        System.out.println("Received request for daily quests: " + message);
        return questRepository.findRandomDailyQuest();
    }

    @RabbitListener(queues = "questByIdQueue")
    public Quest handleQuestByIdRequest(Long id) {
        System.out.println("Received request for quest by id: " + id);
        Optional<Quest> quests = questRepository.findById(id);
        return quests.orElse(null);
    }

    @RabbitListener(queues = "questRandomQueue")
    public Quest handleRandomQuestRequest(String message) {
        System.out.println("Received request for random quest: " + message);
        return questRepository.findRandomQuest();
    }

    @RabbitListener(queues = "questAddQueue")
    public Quest addQuest(Quest quest) {
        System.out.println("Received request for add quest: " + quest);
        return questRepository.save(quest);
    }

    @RabbitListener(queues = "questUpdateQueue")
    public String updateQuest(Quest quest) {
        Optional<Quest> existingQuest = questRepository.findById(quest.getId());

        if (existingQuest.isPresent()) {
            Quest questToUpdate = existingQuest.get();
            questToUpdate.setShortName(quest.getShortName());
            questToUpdate.setDescription(quest.getDescription());
            questToUpdate.setProgressNumber(quest.getProgressNumber());

            questRepository.save(questToUpdate);
            return "Quest updated successfully";
        } else {
            System.out.println("Quest with id " + quest.getId() + " not found.");
            return "Quest not found";
        }
    }


    @RabbitListener(queues = "questDeleteQueue")
    public String deleteQuest(Long id) {
        System.out.println("Received request for delete quest with id: " + id);
        Optional<Quest> existingQuest = questRepository.findById(id);

        if (existingQuest.isPresent()) {
            questRepository.deleteById(id);
            System.out.println("Quest with id " + id + " has been deleted.");
            return "Quest deleted successfully";
        } else {
            System.out.println("Quest with id " + id + " not found.");
            return "Quest not found";
        }
    }


}
