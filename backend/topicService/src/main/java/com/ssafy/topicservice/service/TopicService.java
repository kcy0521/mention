package com.ssafy.topicservice.service;

import com.ssafy.topicservice.elastic.TopicDocument;
import com.ssafy.topicservice.vo.TopicResoponseDto;
import com.ssafy.topicservice.vo.TopicTitleRequestDto;

import java.util.List;

public interface TopicService {

    void saveElastic();

    void deleteElastic();

    List<String> getDailyTopic();

    void setDailyTopic();

    String goToNaver(String topicCandidate, Long memberId);

    List<TopicDocument> searchByTitle(String title);

    String checkSimilarity(String inputTopic);

    void saveTopic();

    List<TopicResoponseDto> getPendingTopic();

    void approveTopic(Long topicId);

    void rejectTopic(Long topicId);

//    TopicResoponseDto getRandomOne();
}