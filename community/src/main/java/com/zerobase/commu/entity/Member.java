package com.zerobase.commu.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.zerobase.commu.dto.MemberDto;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Member {
    @Id
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String email;
    private boolean isAdmin = false;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime regDt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime upDt;

    public MemberDto toDto() {
	return MemberDto.builder()
	    .id(id)
	    .pw(pw)
	    .name(name)
	    .phone(phone)
	    .email(email)
	    .isAdmin(isAdmin)
	    .build();
    }
}
