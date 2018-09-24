package com.axxes.traineeshipawsapp.service;

public interface ScoreService {

    int getScore(final String name, final String group);

    void adjustScore(final String name, final String group, final int increment);

    void addScore(final String name, final String group, final int score);

}
