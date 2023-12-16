package com.example.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.common.MailType;
import com.example.dto.MailInfo;
import com.example.service.MailerService;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class MailerServiceImpl implements MailerService {

	@Autowired
	JavaMailSender sender;

	List<MailInfo> list = new ArrayList<>();

	@Override
	public void send(MailInfo mail) throws MessagingException {
		// Tạo message
		MimeMessage message = sender.createMimeMessage();
		// Sử dụng Helper để thiết lập các thông tin cần thiết cho message
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		try {
			String emailContent = getEmailContent(mail.getBody(), mail.getMailType());
			helper.setText(emailContent, true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		helper.setReplyTo(mail.getFrom());
		String[] cc = mail.getCc();
		if (cc != null && cc.length > 0) {
			helper.setCc(cc);
		}
		String[] bcc = mail.getBcc();
		if (bcc != null && bcc.length > 0) {
			helper.setBcc(bcc);
		}
		String[] attachments = mail.getAttachments();
		if (attachments != null && attachments.length > 0) {
			for (String path : attachments) {
				File file = new File(path);
				helper.addAttachment(file.getName(), file);
			}
		}
		helper.addInline("image-1", new ClassPathResource("static/mail/image-1.png"));
		helper.addInline("image-2", new ClassPathResource("static/mail/image-2.png"));
		helper.addInline("image-3", new ClassPathResource("static/mail/image-3.png"));
		helper.addInline("image-4", new ClassPathResource("static/mail/image-4.png"));
		if (mail.getMailType() == MailType.FORGOT) {
			helper.addInline("forgot-password", new ClassPathResource("static/mail/forgot-password.png"));
		} else if (mail.getMailType() == MailType.SUCCESS_SERVICE) {
			helper.addInline("service", new ClassPathResource("static/mail/service.png"));
			helper.addInline("rate", new ClassPathResource("static/mail/rate.png"));
		}
		// Gửi message đến SMTP server
		sender.send(message);
	}

	@Autowired
	Configuration configuration;

	String getEmailContent(List<Object[]> body, MailType mailType) throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		String template = null;
		if (mailType == MailType.FORGOT) {
			template = "forgot-password.ftlh";
			for (Object[] objects : body) {
				model.put("body", objects[0].toString());
			}
		} else if (mailType == MailType.SUCCESS_SERVICE) {
			template = "success-service.ftlh";
			for (Object[] objects : body) {
				model.put("urlLogin", objects[0].toString());
				model.put("username", objects[1].toString());
			}
		}
		configuration.getTemplate(template).process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	@Override
	public void send(String to, String subject, List<Object[]> body, MailType mailType) throws MessagingException {
		this.send(new MailInfo(to, subject, body, mailType));
	}

	@Override
	public void queue(MailInfo mail) {
		list.add(mail);
	}

	@Override
	public void queue(String to, String subject, List<Object[]> body, MailType mailType) {
		queue(new MailInfo(to, subject, body, mailType));
	}

	@Scheduled(fixedDelay = 5000)
	public void run() {
		while (!list.isEmpty()) {
			MailInfo mail = list.remove(0);
			try {
				this.send(mail);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}