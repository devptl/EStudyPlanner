package com.esp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.esp.Component.ScheduleComponents;
import com.esp.Component.UserComponent;
import com.esp.model.Schedule;

/**
 * Service class to set the timer on hours basisn to send the mail if the free
 * slot is available for particular student
 * 
 * @author mindfire
 *
 */
@Service
@EnableScheduling
public class SetTimer {

	@Autowired
	private ScheduleComponents scheduleComponents;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private SMTPMailSender sMTPMailSender;

	/**
	 * To set the timer when the free slot for particular student is present the
	 * timer will send the mail to their respective mail id when the hour of the
	 * slot matches the current hour
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0 0 * ? * *")
	public void runScheduler() throws InterruptedException {
		ArrayList<Schedule> allSchedule = scheduleComponents.allSchedule();

		for (int i = 0; i < allSchedule.size(); i++) {
			Schedule schedule = allSchedule.get(i);

			// get the slot hours time
			int slot1inh = Integer.parseInt(schedule.getSlotOneIn().substring(0, 2));
			int slot2inh = Integer.parseInt(schedule.getSlotTwoIn().substring(0, 2));
			int slot3inh = Integer.parseInt(schedule.getSlotThreeIn().substring(0, 2));

			// getting the mail id
			String mailId = userComponent.findOne(schedule.getStudentsUserName()).getEmail();

			try {

				System.out.println(LocalDateTime.now().getHour());

				// checking if the slot time matches to current hour timing
				if (slot1inh == LocalDateTime.now().getHour()) {

					sMTPMailSender.send(mailId, "Slot time alert", "Your slot 1 timing will begin at "
							+ schedule.getSlotOneIn() + " and end at " + schedule.getSlotOneOut());
					System.out.println("slot 1 mail send for : " + schedule.getStudentsUserName());

				} else if (slot2inh == LocalDateTime.now().getHour()) {

					sMTPMailSender.send(mailId, "Slot time alert", "Your slot 2 timing will begin at "
							+ schedule.getSlotTwoIn() + " and end at " + schedule.getSlotTwoOut());
					System.out.println("slot 2 began for : " + schedule.getStudentsUserName());

				} else if (slot3inh == LocalDateTime.now().getHour()) {

					sMTPMailSender.send(mailId, "Slot time alert", "Your slot 3 timing will begin at "
							+ schedule.getSlotThreeIn() + " and end at " + schedule.getSlotThreeOut());
					System.out.println("slot 3 began for : " + schedule.getStudentsUserName());

				} else {

					System.out.println("not a slot time for " + schedule.getStudentsUserName());
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
