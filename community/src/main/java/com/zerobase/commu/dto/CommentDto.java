package com.zerobase.commu.dto;

import com.zerobase.commu.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long commentNo;
    private PostDetail post;
    private String content;
    private MemberDto writer;

    public Comment toEntity() {
	return Comment.builder()
	    .commentNo(commentNo)
	    .post(post.toEntity())
	    .content(content)
	    .writer(writer.toEntity())
	    .build();
    }
}
