package com.zerobase.commu.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.commu.entity.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, String> {

    // 아이디로 검색
    Optional<Member> findById(String id);
}
