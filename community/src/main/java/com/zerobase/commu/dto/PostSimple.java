package com.zerobase.commu.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//게시글 목록 보기에서 사용
public class PostSimple {
    private Long postNo;
    private BoardDto board;
    private String title;
    private boolean isNotice; // 공지인지 여부
    private MemberDto writer;
    private Long hitsAll; // 전체 조회수
    private Long hitsDay; // 일일 조회수
    private Long hitsWeek; // 주간 조회수

}
