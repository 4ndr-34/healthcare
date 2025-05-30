package com.example.healthcare.helper.scheduler;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@RequiredArgsConstructor
public class AppointmentStatusUpdater {

    private final AppointmentRepository appointmentRepository;

    @Scheduled(cron = "0 */30 * * * *")
    @Transactional
    public void updateConfirmedAppointments() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        appointmentRepository.findAllByAppointmentDateAndAppointmentTimeBeforeAndAppointmentStatus(today, now, Appointment.AppointmentStatus.CONFIRMED)
                .forEach(appointment -> {
                    appointment.setAppointmentStatus(Appointment.AppointmentStatus.CONFIRMED);
                    appointmentRepository.save(appointment);
                });
    }

}
