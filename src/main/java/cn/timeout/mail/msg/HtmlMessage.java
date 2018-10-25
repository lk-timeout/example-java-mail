package cn.timeout.mail.msg;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;

public class HtmlMessage {
	public static void main(String[] args) throws Exception {
		String from = "123456@qq.com";
		String to = "123456@163.com";
		String subject = "test";
		String body = "<h4>欢迎大家阅读此邮件</h4>";
		// 创建Session实例对象
		Session session = Session.getDefaultInstance(new Properties());
		// 创建MimeMessage实例对象
		MimeMessage msg = new MimeMessage(session);
		// 设置发件人
		msg.setFrom(new InternetAddress(from));
		// 设置收件人
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		// 设置发送日期
		msg.setSentDate(new Date());
		// 设置邮件主题
		msg.setSubject(subject);
		// 设置HTML格式的邮件正文
		msg.setContent(body, "text/html;charset=gb2312");
		// 保存并生成最终的邮件内容
		msg.saveChanges();
		// 把MimeMessage对象中的内容写入到文件中
		msg.writeTo(new FileOutputStream("d:\\test.eml"));
	}
}