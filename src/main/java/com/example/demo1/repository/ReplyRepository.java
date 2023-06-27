package com.example.demo1.repository;

import com.example.demo1.entity.Posting;
import com.example.demo1.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findListByPosting(Posting posting);
}