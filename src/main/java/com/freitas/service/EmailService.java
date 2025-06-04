package com.freitas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final Map<String, List<EmailLog>> emailHistory = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public enum EmailType {
        REGISTRATION_CONFIRMATION,
        APPOINTMENT_CONFIRMATION,
        APPOINTMENT_REMINDER,
        APPOINTMENT_CANCELLATION,
        VACCINE_REMINDER
    }

    // Classe interna para log de emails
    public static class EmailLog {
        private final String to;
        private final String subject;
        private final String content;
        private final EmailType type;
        private final LocalDateTime sentAt;

        public EmailLog(String to, String subject, String content, EmailType type, LocalDateTime sentAt) {
            this.to = to;
            this.subject = subject;
            this.content = content;
            this.type = type;
            this.sentAt = sentAt;
        }

        public String getTo() { return to; }
        public String getSubject() { return subject; }
        public String getContent() { return content; }
        public EmailType getType() { return type; }
        public LocalDateTime getSentAt() { return sentAt; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmailLog emailLog = (EmailLog) o;
            return Objects.equals(to, emailLog.to) &&
                   Objects.equals(subject, emailLog.subject) &&
                   Objects.equals(content, emailLog.content) &&
                   type == emailLog.type &&
                   Objects.equals(sentAt, emailLog.sentAt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(to, subject, content, type, sentAt);
        }
    }

    public void sendEmail(String to, String subject, String content, EmailType type) {
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço de e-mail do destinatário não pode ser vazio");
        }

        // Simula o envio de email registrando em log
        logger.info("=== Simulando envio de e-mail ===");
        logger.info("Tipo: " + type);
        logger.info("Para: " + to);
        logger.info("Assunto: " + subject);
        logger.info("Conteúdo:\n" + content);
        logger.info("================================");

        // Registra o email no histórico
        EmailLog emailLog = new EmailLog(to, subject, content, type, LocalDateTime.now());
        emailHistory.computeIfAbsent(to, k -> new ArrayList<>()).add(emailLog);
    }

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
        
        sendEmail(to, subject, content, EmailType.APPOINTMENT_CONFIRMATION);
    }

    public void sendRegistrationConfirmation(String to, String clientName) {
        String subject = "Bem-vindo à Clínica Veterinária";
        String content = String.format(
            "Olá %s!\n\n" +
            "Bem-vindo à Clínica Veterinária. Seu cadastro foi realizado com sucesso.\n\n" +
            "Com seu cadastro você pode:\n" +
            "- Agendar consultas online\n" +
            "- Acessar o histórico médico dos seus pets\n" +
            "- Receber lembretes de vacinas\n" +
            "- Ver resultados de exames\n\n" +
            "Para acessar o sistema, utilize seu e-mail e a senha cadastrada.\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            clientName
        );
        
        sendEmail(to, subject, content, EmailType.REGISTRATION_CONFIRMATION);
    }

    public void sendAppointmentReminder(String to, String petName, LocalDateTime appointmentDateTime) {
        String subject = "Lembrete de Consulta - Clínica Veterinária";
        String content = String.format(
            "Olá!\n\n" +
            "Lembramos que você tem uma consulta agendada para o pet %s amanhã às %s.\n\n" +
            "Não se esqueça de trazer:\n" +
            "- Carteira de vacinação\n" +
            "- Resultados de exames anteriores (se houver)\n" +
            "- Medicamentos em uso (se houver)\n\n" +
            "Em caso de imprevistos, entre em contato conosco:\n" +
            "Tel: (11) 1234-5678\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            petName,
            appointmentDateTime.format(DATE_FORMATTER)
        );
        
        sendEmail(to, subject, content, EmailType.APPOINTMENT_REMINDER);
    }

    public void sendAppointmentCancellation(String to, String petName, LocalDateTime appointmentDateTime) {
        String subject = "Cancelamento de Consulta - Clínica Veterinária";
        String content = String.format(
            "Olá!\n\n" +
            "A consulta do pet %s agendada para %s foi cancelada conforme solicitado.\n\n" +
            "Para agendar uma nova consulta, acesse nosso sistema online ou entre em contato:\n" +
            "Tel: (11) 1234-5678\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            petName,
            appointmentDateTime.format(DATE_FORMATTER)
        );
        
        sendEmail(to, subject, content, EmailType.APPOINTMENT_CANCELLATION);
    }

    public void sendVaccineReminder(String to, String petName, String vaccineName, LocalDateTime dueDate) {
        String subject = "Lembrete de Vacina - Clínica Veterinária";
        String content = String.format(
            "Olá!\n\n" +
            "Está chegando a data da vacina %s do pet %s.\n" +
            "Data prevista: %s\n\n" +
            "A vacinação em dia é fundamental para a saúde do seu pet!\n" +
            "Agende a vacinação através do nosso sistema online ou telefone:\n" +
            "Tel: (11) 1234-5678\n\n" +
            "Atenciosamente,\n" +
            "Equipe da Clínica Veterinária",
            vaccineName,
            petName,
            dueDate.format(DATE_FORMATTER)
        );
        
        sendEmail(to, subject, content, EmailType.VACCINE_REMINDER);
    }

    // Métodos para fins de teste e depuração
    public List<EmailLog> getEmailHistory(String email) {
        return emailHistory.getOrDefault(email, new ArrayList<>());
    }

    public void clearEmailHistory() {
        emailHistory.clear();
    }
}
