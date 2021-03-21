package com.example.fundamentals;

import com.example.fundamentals.entity.Application;
import com.example.fundamentals.entity.Release;
import com.example.fundamentals.entity.Ticket;
import com.example.fundamentals.repository.ApplicationRepository;
import com.example.fundamentals.repository.ReleaseRepository;
import com.example.fundamentals.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.Basic;
import java.util.List;

@SpringBootApplication
public class FundamentalsApplication {

	private Logger log = LoggerFactory.getLogger(FundamentalsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FundamentalsApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			ApplicationRepository appRepo,
			ReleaseRepository relRepo,
			TicketRepository ticketRepo
	) {
		return (args) -> {
			Application app1 = appRepo.save(new Application("myapp", "owner", "my cool app"));
			Release rel1 = relRepo.save(new Release("my-1.0.0","20200101"));
			Release rel2 = relRepo.save(new Release("my-2.0.0","20210101"));

			var ticket1= new Ticket("bug1","app does not work",app1,rel1,"FIXED");
			var ticket2= new Ticket("bug2","app still does not work",app1,rel1,"OPEN");
			ticketRepo.saveAll(List.of(ticket1,ticket2));

			appRepo.findAll().forEach(app -> log.info("---app:"+app));
			relRepo.findAll().forEach(rel -> log.info("---rel:"+rel));
			ticketRepo.findAll().forEach(ticket -> log.info("---ticket:"+ticket));

		};
	}
}
