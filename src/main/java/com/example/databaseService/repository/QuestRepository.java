package com.example.databaseService.repository;

import com.example.questModel.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

    @Query(value = "SELECT * FROM quests.public.quests order by RANDOM() LIMIT 3", nativeQuery = true)
    List<Quest> findRandomDailyQuest();

    @Query(value = "SELECT * from quests.public.quests order by RANDOM() LIMIT 1", nativeQuery = true)
    Quest findRandomQuest();
}
