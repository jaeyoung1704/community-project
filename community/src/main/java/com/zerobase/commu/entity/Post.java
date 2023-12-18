package com.zerobase.commu.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.zerobase.commu.dto.PostDetail;
import com.zerobase.commu.dto.PostSimple;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;
    private String title;
    private String content;
    private boolean isNotice = false; // 공지인지 여부

    @ManyToOne
    @JoinColumn(name = "writer")
    private Member writer;
    private Long hitsAll; // 전체 조회수
    private Long hitsDay; // 일일 조회수
    private Long hitsWeek; // 주간 조회수

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime regDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime upDt;

    public PostDetail toDetail() {
	return PostDetail.builder()
	    .postNo(postNo)
	    .board(board.toDto())
	    .title(title)
	    .content(content)
	    .isNotice(isNotice)
	    .writer(writer.toDto())
	    .hitsAll(hitsAll)
	    .hitsDay(hitsDay)
	    .hitsWeek(hitsWeek)
	    .build();
    }

    public PostSimple toSimple() {
	return PostSimple.builder()
	    .postNo(postNo)
	    .board(board.toDto())
	    .title(title)
	    .isNotice(isNotice)
	    .hitsAll(hitsAll)
	    .hitsDay(hitsDay)
	    .hitsWeek(hitsWeek)
	    .writer(writer.toDto())
	    .build();
    }
}
