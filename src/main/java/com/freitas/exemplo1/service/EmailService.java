package com.freitas.exemplo1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void sendAppointmentConfirmation(String to, String petName, LocalDateTime appointmentDateTime) {
        String subject = "Confirmação de Agendamento - Clínica Veterinária";
        String content = String.format(
            "Olá!\n\n" +
            "Seu agendamento para o pet %s foi confirmado para %s.\n\n" +
            "Lembretes importantes:\n" +
            "- Chegue com 10 minutos de antecedência\n" +
            "- Traga a carteira de vacinação do pet\n" +
            "- Em caso de imprevistos, cancele com pelo menos 24h de antecedência\n\n" +
            "Endereço da clínica:\n" +
            "Rua Exemplo, 123\n" +
            "Bairro Centro\n" +
            "Tel: (11) 1234-5678\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            petName, 
            appointmentDateTime.format(DATE_FORMATTER)
        );
        
        sendEmail(to, subject, content);
    }

    public void sendAppointmentCancellation(String to, String petName, LocalDateTime appointmentDateTime) {
        String subject = "Cancelamento de Agendamento - Clínica Veterinária";
        String content = String.format(
            "Olá!\n\n" +
            "O agendamento do pet %s para %s foi cancelado.\n\n" +
            "Caso queira remarcar, entre em contato conosco:\n" +
            "Tel: (11) 1234-5678\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            petName,
            appointmentDateTime.format(DATE_FORMATTER)
        );
        
        sendEmail(to, subject, content);
    }

    public void sendAppointmentReschedule(String to, String petName, LocalDateTime newDateTime) {
        String subject = "Reagendamento de Consulta - Clínica Veterinária";
        String content = String.format(
            "Olá!\n\n" +
            "O agendamento do pet %s foi remarcado para %s.\n\n" +
            "Lembretes importantes:\n" +
            "- Chegue com 10 minutos de antecedência\n" +
            "- Traga a carteira de vacinação do pet\n" +
            "- Em caso de imprevistos, cancele com pelo menos 24h de antecedência\n\n" +
            "Endereço da clínica:\n" +
            "Rua Exemplo, 123\n" +
            "Bairro Centro\n" +
            "Tel: (11) 1234-5678\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            petName,
            newDateTime.format(DATE_FORMATTER)
        );
        
        sendEmail(to, subject, content);
    }

    private void sendEmail(String to, String subject, String content) {
        // TODO: Implement actual email sending logic
        log.info("Sending email to: {}", to);
        log.info("Subject: {}", subject);
        log.info("Content: {}", content);
    }
}
