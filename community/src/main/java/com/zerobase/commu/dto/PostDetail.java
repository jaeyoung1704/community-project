package com.zerobase.commu.dto;

import java.util.List;

import com.zerobase.commu.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//게시글 상세보기에서 사용
public class PostDetail {
    private Long postNo;
    private BoardDto board;
    private String title;
    private String content;
    private boolean isNotice; // 공지인지 여부
    private MemberDto writer;
    private Long hitsAll; // 전체 조회수
    private Long hitsDay; // 일일 조회수
    private Long hitsWeek; // 주간 조회수
    private List<CommentDto> comments; // 댓글

    public Post toEntity() {
	return Post.builder()
	    .postNo(postNo)
	    .board(board.toEntity())
	    .title(title)
	    .content(content)
	    .isNotice(isNotice)
	    .writer(writer.toEntity())
	    .hitsAll(hitsAll)
	    .hitsDay(hitsDay)
	    .hitsWeek(hitsWeek)
	    .build();
    }
}
