package com.zerobase.commu.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.zerobase.commu.dto.CommentDto;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;
    @ManyToOne
    @JoinColumn(name = "post_no")
    private Post post;
    private String content;
    @ManyToOne
    @JoinColumn(name = "writer")
    private Member writer;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime regDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime upDt;

    public CommentDto toDto() {
	return CommentDto.builder()
	    .commentNo(commentNo)
	    .post(post.toDetail())
	    .writer(writer.toDto())
	    .build();
    }
}
