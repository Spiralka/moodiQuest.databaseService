package com.example.databaseService.service;

import com.example.questModel.Quest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestListener {

    @RabbitListener(queues = "questRequestQueue")
    public List<Quest> handleDailyQuestsRequest(String message) {
        System.out.println("Received request for daily quests: " + message);
        // Здесь вы можете вернуть список квестов из базы данных
        return List.of(new Quest(1L, "Daily Quest 1"), new Quest(2L, "Daily Quest 2"), new Quest(3L, "Daily Quest 3"));
    }

    @RabbitListener(queues = "questByIdQueue")
    public Quest handleQuestByIdRequest(Long id) {
        System.out.println("Received request for quest by id: " + id);
        // Здесь вы можете вернуть квест по ID из базы данных
        return new Quest(id, "Quest with ID " + id);
    }

    @RabbitListener(queues = "questRandomQueue")
    public Quest handleRandomQuestRequest(String message) {
        System.out.println("Received request for random quest: " + message);
        // Здесь вы можете вернуть случайный квест из базы данных
        return new Quest(999L, "Random Quest");
    }
}
