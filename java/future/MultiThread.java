package com.bc.multithread.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.bc.multithread.dto.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MongoTemplate mongoTemplate;
	
	@GetMapping("/data")
	@ResponseBody
	public String getData() {
		long a = System.currentTimeMillis();
		
		/* 멀티 스레드----------------------------------------------------------------------*/
		List<Callable<Report>> tasks = new ArrayList<>();
		for(int i = 0; i < 400; i++) {
			tasks.add(() -> mongoTemplate.findOne(new Query(), Report.class, "provider_report_h"));
		}
			
		tasks.add(this::fail);
		
		for(int i = 0; i < 400; i++) {
			tasks.add(() -> mongoTemplate.findOne(new Query(), Report.class, "provider_report_h"));
		}
		
		List<Report> reports = getReportsCompleteableFuture(tasks);
//		List<Report> reports = getReportsFuture(tasks);
		
		/*------------------------------------------------------------------------------*/
		
		/* 싱글 스레드----------------------------------------------------------------------*/
//		List<Report> reports = new ArrayList<>(); 		
//		for(int i = 0; i < 800; i++) {
//			Report report = mongoTemplate.findOne(new Query(), Report.class, "provider_report_h");
//			reports.add(report);
//		}
		/*-------------------------------------------------------------------------*/
		
		long b = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		sb.append("Time : ").append(b - a).append("ms").append("<br>");
		sb.append("size : ").append(reports.size()).append("<br>");
		sb.append(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
		return sb.toString();
	}
	
	private List<Report> getReportsFuture(List<Callable<Report>> tasks) {
		ExecutorService executorService = Executors.newWorkStealingPool();
		List<Report> reports = new ArrayList<>();
		try {
			List<Future<Report>> futures = executorService.invokeAll(tasks);
			for(Future<?> future : futures) {
				try {
					Report report = (Report) future.get();
					reports.add(report);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		executorService.shutdown();
		return reports;
	}
	
	private static List<Report> getReportsCompleteableFuture(List<Callable<Report>> tasks) {
		ExecutorService executorService = Executors.newWorkStealingPool();
		List<CompletableFuture<Report>> futures = new ArrayList<>();
		for(Callable<Report> task : tasks) {
			CompletableFuture<Report> completableFuture = CompletableFuture.supplyAsync(() -> {
				try {
					return task.call();
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}, executorService);
			
			futures.add(completableFuture);
		}
		executorService.shutdown();
		
		return futures.stream()
			.map(CompletableFuture::join)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}
	
	private Report fail() {
		throw new RuntimeException("에러~");
	}
	
	private Integer oneSecond() {
		try {
			log.info("@@@@@ oneSecond");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 1;
	}
	private Integer threeSecond() {
		try {
			log.info("@@@@@ threeSecond");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 3;
	}
}

