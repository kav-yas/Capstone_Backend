package com.example.habittracker;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableScheduling // ✅ Enables Cron Jobs
public class HabitTrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(HabitTrackerApplication.class, args);
	}
}





