package com.example.demo1.service;

import com.example.demo1.entity.Review;
import com.example.demo1.entity.Room;
import com.example.demo1.repository.ReviewRepository;
import com.example.demo1.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private ReviewRepository reviewRepository;

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    // 총점만 업데이트 됨
    public void update(Long roomId) { // RoomUpdateDTO updateParam 안써도 될 것 같기도..
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> { // 영속화
                    return new IllegalArgumentException("방 찾기 실패 : roomId를 찾을 수 없습니다.");
                });
        Room newRoom = roomRepository.findByRoomId(roomId);
        room.setTotalScore(calculateTotalScore(newRoom));
    }

    public Optional<Room> search(String address) {
        return roomRepository.findByAddress(address);
    }

    // 해당 방의 리뷰들을 모두 검색해서 점수 평균 내기
    // 리뷰 어떻게 가져오지.. 지금 포스팅이랑 댓글 다는 부분도 여기서 막혔는데..ㅋㅋ -> 일단 리스트로 뽑아냈음
    private double calculateTotalScore(Room room) {

        int sum = 0;
        int count = 0;
        double result = 0;

        List<Review> reviews = reviewRepository.findListByRoom(room);
        if (reviews == null || reviews.isEmpty()) {
            throw new UnsupportedOperationException();
        }

        for (Review review : reviews) {
            sum += review.getScore();
            count++;
        }

        result = (double) sum / (double) count;

        return result;

    }

}