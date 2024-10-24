package com.example.databaseService.repository;

import com.example.questModel.UserSuggestedQuest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuggestedQuestRepository extends MongoRepository<UserSuggestedQuest, String> {
}
