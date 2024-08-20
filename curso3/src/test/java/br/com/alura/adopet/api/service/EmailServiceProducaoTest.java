package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceProducaoTest {

    @InjectMocks
    private EmailServiceProducao service;

    @Mock
    private JavaMailSender emailSender;

    private SimpleMailMessage email = new SimpleMailMessage();

    @Test
    @DisplayName("Método send do emailSender deveria ser chamado")
    void cenario01() {
        // ARRANGE
        email.setFrom("adopet@email.com.br");
        email.setTo("teste@email.com");
        email.setSubject("assunto");
        email.setText("mensagem");

        // ACT
        service.enviarEmail("teste@email.com", "assunto", "mensagem");

        // ASSERT
        BDDMockito.then(emailSender).should().send(email);
    }


}