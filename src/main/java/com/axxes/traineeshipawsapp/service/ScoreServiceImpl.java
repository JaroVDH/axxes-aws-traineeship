package com.axxes.traineeshipawsapp.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {

    private static final String TABLE_NAME = "TraineeScores";
    private final AmazonDynamoDB amazonDynamoDB;

    @Autowired
    public ScoreServiceImpl(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

    public int getScore(final String name, final String group) {
        GetItemRequest request = new GetItemRequest()
                .withTableName(TABLE_NAME)
                .withKey(toKey(name, group));

        GetItemResult result = amazonDynamoDB.getItem(request);

        return Optional.ofNullable(result)
                .map(GetItemResult::getItem)
                .map(item -> item.get("Score"))
                .map(attributeValue -> Integer.parseInt(attributeValue.getN()))
                .orElse(0);

    }

    public void adjustScore(final String name, final String group, final int increment) {
        Map<String, AttributeValue> attributeValues = new HashMap<>();
        attributeValues.put(":increment", new AttributeValue().withN(Integer.toString(increment)));

        UpdateItemRequest request = new UpdateItemRequest()
                .withTableName(TABLE_NAME)
                .withKey(toKey(name, group))
                .withUpdateExpression("ADD Score :increment")
                .withExpressionAttributeValues(attributeValues);

        amazonDynamoDB.updateItem(request);
    }

    public void addScore(final String name, final String group, final int score) {
        Map<String, AttributeValue> item = toKey(name, group);
        item.put("Score", new AttributeValue().withN(Integer.toString(score)));

        PutItemRequest request = new PutItemRequest()
                .withTableName(TABLE_NAME)
                .withItem(item);

        amazonDynamoDB.putItem(request);
    }

    private static Map<String, AttributeValue> toKey(final String name, final String group) {
        Map<String, AttributeValue> key =  new HashMap<>();

        key.put("Name", new AttributeValue(name));
        key.put("Group", new AttributeValue(group));

        return key;
    }

}
