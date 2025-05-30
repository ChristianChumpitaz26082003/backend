package com.sistema.gestion.servicios;

import com.sistema.gestion.modelo.MfaLog;
import com.sistema.gestion.repositorio.MfaLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MfaService {

    private final Map<String, String> otpCache = new ConcurrentHashMap<>();
    private final JavaMailSender mailSender;
    private final MfaLogRepository logRepository;

    @Autowired
    public MfaService(JavaMailSender mailSender, MfaLogRepository logRepository) {
        this.mailSender = mailSender;
        this.logRepository = logRepository;
    }

    public String generateOtp(String username) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpCache.put(username, otp);

        logRepository.save(MfaLog.builder()
                .username(username)
                .evento("OTP_GENERADO")
                .detalle("OTP generado y enviado por correo")
                .fecha(LocalDateTime.now())
                .build());

        return otp;
    }

    public boolean verifyOtp(String username, String otp) {
        String cachedOtp = otpCache.get(username);
        boolean valid = cachedOtp != null && cachedOtp.equals(otp);

        logRepository.save(MfaLog.builder()
                .username(username)
                .evento(valid ? "OTP_VALIDO" : "OTP_INVALIDO")
                .detalle("OTP ingresado: " + otp + " / Esperado: " + cachedOtp)
                .fecha(LocalDateTime.now())
                .build());

        if (valid) otpCache.remove(username);

        return valid;
    }

    public void sendOtpEmail(String email, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Código de verificación MFA");
            message.setText("Tu código de verificación es: " + otp);
            mailSender.send(message);
        } catch (Exception e) {
            logRepository.save(MfaLog.builder()
                    .username(email)
                    .evento("ENVIO_FALLIDO")
                    .detalle("Fallo al enviar OTP: " + e.getMessage())
                    .fecha(LocalDateTime.now())
                    .build());
        }
    }
}
