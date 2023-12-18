package com.zerobase.commu.dto;

import com.zerobase.commu.entity.Board;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long boardNo;
    private String boardName;

    public Board toEntity() {
	return Board.builder().boardNo(boardNo).boardName(boardName).build();
    }
}
