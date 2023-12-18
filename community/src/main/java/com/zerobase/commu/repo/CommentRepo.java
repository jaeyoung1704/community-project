package com.zerobase.commu.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.commu.entity.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    // 게시글 번호로 댓글 조회
    List<Comment> findAllByPost_PostNo(long postNo);

    // 글쓴이로 댓글 조회
    List<Comment> findAllByWriter_Id(String id);

}
