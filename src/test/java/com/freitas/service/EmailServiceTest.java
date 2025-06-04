package com.freitas.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        emailService = new EmailService();
        emailService.clearEmailHistory();
    }

    @Test
    void testSendRegistrationConfirmation() {
        String email = "test@example.com";
        String name = "João Silva";

        emailService.sendRegistrationConfirmation(email, name);

        List<EmailService.EmailLog> history = emailService.getEmailHistory(email);
        assertEquals(1, history.size());
        
        EmailService.EmailLog log = history.get(0);
        assertEquals(EmailService.EmailType.REGISTRATION_CONFIRMATION, log.getType());
        assertEquals(email, log.getTo());
        assertTrue(log.getContent().contains(name));
        assertTrue(log.getSubject().contains("Bem-vindo"));
    }

    @Test
    void testSendAppointmentConfirmation() {
        String email = "test@example.com";
        String petName = "Rex";
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);

        emailService.sendAppointmentConfirmation(email, petName, appointmentTime);

        List<EmailService.EmailLog> history = emailService.getEmailHistory(email);
        assertEquals(1, history.size());
        
        EmailService.EmailLog log = history.get(0);
        assertEquals(EmailService.EmailType.APPOINTMENT_CONFIRMATION, log.getType());
        assertEquals(email, log.getTo());
        assertTrue(log.getContent().contains(petName));
        assertTrue(log.getSubject().contains("Confirmação"));
    }

    @Test
    void testSendVaccineReminder() {
        String email = "test@example.com";
        String petName = "Rex";
        String vaccineName = "Raiva";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(7);

        emailService.sendVaccineReminder(email, petName, vaccineName, dueDate);

        List<EmailService.EmailLog> history = emailService.getEmailHistory(email);
        assertEquals(1, history.size());
        
        EmailService.EmailLog log = history.get(0);
        assertEquals(EmailService.EmailType.VACCINE_REMINDER, log.getType());
        assertEquals(email, log.getTo());
        assertTrue(log.getContent().contains(petName));
        assertTrue(log.getContent().contains(vaccineName));
        assertTrue(log.getSubject().contains("Vacina"));
    }

    @Test
    void testSendAppointmentCancellation() {
        String email = "test@example.com";
        String petName = "Rex";
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);

        emailService.sendAppointmentCancellation(email, petName, appointmentTime);

        List<EmailService.EmailLog> history = emailService.getEmailHistory(email);
        assertEquals(1, history.size());
        
        EmailService.EmailLog log = history.get(0);
        assertEquals(EmailService.EmailType.APPOINTMENT_CANCELLATION, log.getType());
        assertEquals(email, log.getTo());
        assertTrue(log.getContent().contains(petName));
        assertTrue(log.getSubject().contains("Cancelamento"));
    }

    @Test
    void testSendEmailWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendEmail(null, "Test", "Content", EmailService.EmailType.REGISTRATION_CONFIRMATION);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendEmail("", "Test", "Content", EmailService.EmailType.REGISTRATION_CONFIRMATION);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendEmail("   ", "Test", "Content", EmailService.EmailType.REGISTRATION_CONFIRMATION);
        });
    }

    @Test
    void testEmailHistory() {
        String email = "test@example.com";
        String petName = "Rex";
        LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1);

        // Enviar múltiplos emails
        emailService.sendRegistrationConfirmation(email, "João Silva");
        emailService.sendAppointmentConfirmation(email, petName, appointmentTime);
        emailService.sendVaccineReminder(email, petName, "Raiva", appointmentTime);

        // Verificar histórico
        List<EmailService.EmailLog> history = emailService.getEmailHistory(email);
        assertEquals(3, history.size());
        
        // Verificar ordem dos emails (último enviado deve ser o último no histórico)
        assertEquals(EmailService.EmailType.REGISTRATION_CONFIRMATION, history.get(0).getType());
        assertEquals(EmailService.EmailType.APPOINTMENT_CONFIRMATION, history.get(1).getType());
        assertEquals(EmailService.EmailType.VACCINE_REMINDER, history.get(2).getType());

        // Limpar histórico
        emailService.clearEmailHistory();
        assertTrue(emailService.getEmailHistory(email).isEmpty());
    }
}
