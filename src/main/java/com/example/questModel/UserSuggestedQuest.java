package com.example.questModel;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "suggestedQuest")
public class UserSuggestedQuest implements Serializable {

    private String userName;
    private String quest;
}
