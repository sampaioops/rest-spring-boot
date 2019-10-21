package com.sampaio.cursoestudo.servico.email;

import com.sampaio.cursoestudo.modelo.Cliente;
import com.sampaio.cursoestudo.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstratoEmailServico implements EmailServico {

    @Value("${default.sender}")
    private String emailPadrao;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void envioaOrdemConfirmacaoEmail(Pedido pedido) {
        SimpleMailMessage msg = preparaSimplesMailMessage(pedido);
        sendEmail(msg);
    }

    protected SimpleMailMessage preparaSimplesMailMessage(Pedido pedido) {
        SimpleMailMessage msg = new SimpleMailMessage();

        //Destinatario
        msg.setTo(pedido.getCliente().getEmail());
        //Remetente
        msg.setFrom(emailPadrao);
        msg.setSubject("Pedido confirmado! Código: " + pedido.getId());
        msg.setSentDate(new Date(System.currentTimeMillis()));
        msg.setText(pedido.toString());

        return msg;
    }

    //Nao funciona!
    @Override
    public void envioaOrdemConfirmacaoEmailHtml(Pedido pedido) {
        try {
            MimeMessage mm = preparaMimeMessage(pedido);
            sendEmailHtml(mm);
        } catch (MessagingException e) {
            envioaOrdemConfirmacaoEmail(pedido);
        }
    }

    protected MimeMessage preparaMimeMessage(Pedido pedido) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(pedido.getCliente().getEmail());
        mimeMessageHelper.setFrom(emailPadrao);
        mimeMessageHelper.setSubject("Pedido confirmado! Código: " + pedido.getId());
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplatePedido(pedido));

        return mimeMessage;
    }

    protected String htmlFromTemplatePedido(Pedido pedido) {
        Context context = new Context();
        context.setVariable("pedido", pedido);
        return templateEngine.process("confirmacaoPedido", context);
    }


    @Override
    public void enviaNovaSenhaEmail(Cliente cliente, String newPass) {
        SimpleMailMessage msg = preparaNovaSenhaEmail(cliente, newPass);
        sendEmail(msg);
    }

    protected SimpleMailMessage preparaNovaSenhaEmail(Cliente cliente, String newPass) {
        SimpleMailMessage msg = new SimpleMailMessage();

        //Destinatario
        msg.setTo(cliente.getEmail());
        //Remetente
        msg.setFrom(emailPadrao);
        msg.setSubject("Solitação de nova senha");
        msg.setSentDate(new Date(System.currentTimeMillis()));
        msg.setText("Nova senha: " + newPass);

        return msg;

    }
}
