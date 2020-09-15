package com.ek.common.utils;

import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.mailConfig.domain.MailConfig;

import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;



public class MailUtil {

	@SuppressWarnings("static-access")
	public static void sendMail(MailConfig mailConfig, List<Mail> receiveMailList, String html, String formTitle) throws Exception {
		// 创建邮件配置
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", mailConfig.getSmtpAdress()); // 发件人的邮箱的 SMTP 服务器地址
		props.setProperty("mail.smtp.port", mailConfig.getSmtpPort());

		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.setProperty("mail.smtp.ssl.enable", "true");// 开启ssl
		
		// 根据邮件配置创建会话，注意session别导错包
		Session session = Session.getDefaultInstance(props);
		// 开启debug模式，可以看到更多详细的输入日志
		// session.setDebug(true);
		// 创建邮件
		// 根据会话创建邮件
		MimeMessage msg = new MimeMessage(session);
		// address邮件地址, personal邮件昵称, charset编码方式
		InternetAddress fromAddress = new InternetAddress(mailConfig.getSendMail(), mailConfig.getSendName(), "utf-8");
		// 设置发送邮件方
		msg.setFrom(fromAddress);
		// 设置邮件接收方
		StringBuilder stringBuilder = new StringBuilder();
		Iterator<Mail> iterator = receiveMailList.iterator();
		while (iterator.hasNext()){
			stringBuilder.append(iterator.next().getMailAccount());
			stringBuilder.append(",");
		}
		InternetAddress[] manyAddress = InternetAddress.parse(stringBuilder.substring(0,stringBuilder.length()-1).toString());

		/*
		 * 设置收件人地址 * MimeMessage.RecipientType.TO:发送 MimeMessage.RecipientType.CC：抄送
		 * MimeMessage.RecipientType.BCC：密送
		 */
		if (null == mailConfig.getSendStyle()) {
			mailConfig.setSendStyle("TO");
		}
		if ("TO".equals(mailConfig.getSendStyle())) {
			
			msg.setRecipients(RecipientType.TO, manyAddress);
		} else if ("CC".equals(mailConfig.getSendStyle())) {
			
			msg.setRecipients(RecipientType.CC, manyAddress);
		} else if ("BCC".equals(mailConfig.getSendStyle())) {
			
			msg.setRecipients(RecipientType.BCC, manyAddress);
		} else {
			
			msg.setRecipients(RecipientType.TO, manyAddress);
		}

		// 设置邮件标题
		msg.setSubject("表单提交信息通知-----" + formTitle, "utf-8");

		msg.setContent(html, "text/html;charset=UTF-8");

		// 设置显示的发件时间
		msg.setSentDate(new Date());
		// 保存设置
		msg.saveChanges();

		// 获取传输通道
		Transport transport = session.getTransport();
		transport.connect(mailConfig.getSmtpAdress(), mailConfig.getSendMail(), mailConfig.getAuthCode());
		// 连接，并发送邮件
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
		

	}

}
