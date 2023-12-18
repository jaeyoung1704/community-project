package com.zerobase.commu.dto;

import com.zerobase.commu.entity.Member;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String email;
    private boolean isAdmin = false;

    public Member toEntity() {
	return Member.builder()
	    .id(id)
	    .pw(pw)
	    .name(name)
	    .phone(phone)
	    .email(email)
	    .isAdmin(isAdmin)
	    .build();
    }
}
