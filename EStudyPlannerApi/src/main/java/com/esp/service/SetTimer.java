package com.esp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.Schedule;

@Service
@EnableScheduling
public class SetTimer {

	@Autowired
	private DtoOperation dtoOperation;

	@Autowired
	private SMTPMailSender sMTPMailSender;

	@Scheduled(cron = "0 0 * ? * *")
	public void runScheduler() throws InterruptedException {
		ArrayList<Schedule> allSchedule = dtoOperation.getScheduleComponents().allSchedule();

		for (int i = 0; i < allSchedule.size(); i++) {
			Schedule s1 = allSchedule.get(i);

			// get the slot hours time
			int slot1inh = Integer.parseInt(s1.getSlotOneIn().substring(0, 2));
			int slot2inh = Integer.parseInt(s1.getSlotTwoIn().substring(0, 2));
			int slot3inh = Integer.parseInt(s1.getSlotThreeIn().substring(0, 2));

			// getting the mail id
			String mailId = dtoOperation.getStudentsComponents().findOneStudent(s1.getStudentsUserName()).getEmail();

			try {

				System.out.println(LocalDateTime.now().getHour());

				// checking if the slot time matches to current hour timing
				if (slot1inh == LocalDateTime.now().getHour()) {

					sMTPMailSender.send(mailId, "Slot time alert", "Your slot 1 timing will begin at "
							+ s1.getSlotOneIn() + " and end at " + s1.getSlotOneOut());
					System.out.println("slot 1 mail send for : " + s1.getStudentsUserName());

				} else if (slot2inh == LocalDateTime.now().getHour()) {

					sMTPMailSender.send(mailId, "Slot time alert", "Your slot 2 timing will begin at "
							+ s1.getSlotTwoIn() + " and end at " + s1.getSlotTwoIn());
					System.out.println("slot 2 began for : " + s1.getStudentsUserName());

				} else if (slot3inh == LocalDateTime.now().getHour()) {

					sMTPMailSender.send(mailId, "Slot time alert", "Your slot 3 timing will begin at "
							+ s1.getSlotThreeIn() + " and end at " + s1.getSlotThreeIn());
					System.out.println("slot 3 began for : " + s1.getStudentsUserName());

				} else {

					System.out.println("not a slot time for " + s1.getStudentsUserName());
					System.out.println(
							"As his slot in hour 1st : " + slot1inh + " 2nd : " + slot2inh + " 3rd : " + slot3inh);

				}

			} catch (Exception e) {
				System.out.println("network issue mail can not be send");
			}

		}
		Thread.sleep(3000);

	}

}