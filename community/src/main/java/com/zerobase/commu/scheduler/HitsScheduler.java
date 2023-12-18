package com.zerobase.commu.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zerobase.commu.entity.Post;
import com.zerobase.commu.repo.PostRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HitsScheduler {
    private final PostRepo postRepo;

    // 매일 자정에 일간 조회수 리셋
    @Scheduled(cron = "0 0 0 * * *") // 초 분 시 일 월 요일
    public void RestDay() {
	List<Post> postList = postRepo.findAll();
	postList.forEach(x -> x.setHitsDay(0l));
	postRepo.saveAllAndFlush(postList);
    }

    // 매주 월요일 자정에 주간 조회수 리셋
    @Scheduled(cron = "0 0 0 * * MON") // 초 분 시 일 월 요일
    public void RestWeek() {
	List<Post> postList = postRepo.findAll();
	postList.forEach(x -> x.setHitsWeek(0l));
	postRepo.saveAllAndFlush(postList);
    }
}
