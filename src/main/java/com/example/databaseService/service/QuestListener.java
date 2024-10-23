package com.example.databaseService.service;

import com.example.databaseService.repository.QuestRepository;
import com.example.questModel.Quest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<Quest> quests = questRepository.findAll();
        Collections.shuffle(quests);
        return quests.stream().limit(3).collect(Collectors.toList());
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
        return new Quest(999L, "Random Quest");
    }
}
