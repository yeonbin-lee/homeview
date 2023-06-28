package com.example.demo1.repository;

import com.example.demo1.entity.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    //Page<Posting> findAll(Pageable pageable);
}