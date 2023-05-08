package com.ssafy.topicservice.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.ssafy.topicservice.elastic.TopicDocument;
import com.ssafy.topicservice.service.TopicService;
import com.ssafy.topicservice.vo.TopicResoponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topic-service")
@RequiredArgsConstructor
@Tag(name = "토픽 관리")
public class TopicController {

    private final TopicService topicService;
    @Operation(summary = "MSA 연결 체크")
    @GetMapping("/health-check")
    public String checkConnection(){
        return "TopicService Check Completed!";
    }


    @Operation(summary = "엘라스틱 repository에 저장", description = "데이터가 쌓이면 추후 메서드로 변경 예정")
    @PostMapping("/save/elastic")
    public void saveElastic() {
        topicService.saveElastic();
    }

    @Operation(summary = "엘라스틱 repository에서 삭제", description = "데이터가 쌓이면 추후 메서드로 변경 예정")
    @PostMapping("/delete/elastic")
    public void delete() {
        topicService.deleteElastic();
    }

    @Operation(summary = "토픽 검색", description = "초성단위로는 안됨")
    @GetMapping("/elastic-search")
    public ResponseEntity<List<TopicDocument>> searchByTitle(@RequestParam String title) {
        List<TopicDocument> documents = topicService.searchByTitle(title);
        return ResponseEntity.ok().body(documents);
    }

    @Operation(summary = "네이버 감정 분석 요청", description = "새로운 토픽일 경우 응모하시겠습니까? 이후 검증")
    @PostMapping("/call/naver")
    public ResponseEntity<String> goToNaver(@RequestBody Map<String, String> topicCandidate) {
        return ResponseEntity.ok().body(topicService.goToNaver(topicCandidate.get("topicCandidate")));
    }

    @Operation(summary = "새로운 토픽인지 아닌지 검증", description = "새로운 토픽인지 아닌지 검증")
    @PostMapping("/check/similarity")
    public ResponseEntity<String> checkSimilarity(@RequestBody Map<String, String> inputTopic) {
        return ResponseEntity.ok().body(topicService.checkSimilarity(inputTopic.get("inputTopic")));
    }

    @Operation(summary = "토픽 저장", description = "추후 데이터 쌓이면 메서드로 변환 예정")
    @PostMapping("/save/topic")
    public void saveTopic() {
        topicService.saveTopic();
    }

    @Operation(summary = "관리자가 응모리스트를 조회", description = "PENDING 상태인것만 반환")
    @GetMapping("/admin/pendingList")
    public ResponseEntity<List<TopicResoponseDto>> getPendingTopic() {
        return ResponseEntity.ok().body(topicService.getPendingTopic());
    }

    @Operation(summary = "관리자가 응모 토픽을 승인", description = "APPROVE 상태로 변환")
    @PostMapping("/admin/changeStatus/approve")
    public ResponseEntity<?> approveTopic(@RequestBody Map<String, Long> topicId) {
        topicService.approveTopic(topicId.get("topicId"));
        return ResponseEntity.ok().body("승인 완료");
    }

    @Operation(summary = "관리자가 응모 토픽을 거절", description = "REJECT 상태로 변환")
    @PostMapping("/admin/changeStatus/reject")
    public ResponseEntity<?> rejectTopic(@RequestBody Map<String, Long> topicId) {
        topicService.rejectTopic(topicId.get("topicId"));
        return ResponseEntity.ok().body("거절 완료");
    }

//    @GetMapping("/random/one/{teamId}")
//    public ResponseEntity<TopicResoponseDto> getRandomOne() {
//        return ResponseEntity.ok().body(topicService.getRandomOne());
//    }

}
