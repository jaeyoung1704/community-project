package com.zerobase.commu.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.commu.entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    // 게시판번호로 게시글 조회 (공지 최상위노출, 그다음은 최신순)
    public List<Post> findAllByBoard_BoardNoOrderByIsNoticeDescPostNoDesc(long boardNo);

    // 일간 조회수순 조회
    public List<Post> findAllByBoard_BoardNoOrderByIsNoticeDescHitsDayDesc(long boardNo);

    // 주간 조회수순 조회
    public List<Post> findAllByBoard_BoardNoOrderByIsNoticeDescHitsWeekDesc(long boardNo);

    // 작성자로 검색
    public List<Post> findAllByWriter_Id(String writer);

}
