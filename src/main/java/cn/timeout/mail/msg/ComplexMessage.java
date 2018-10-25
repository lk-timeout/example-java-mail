package cn.timeout.mail.msg;

import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author timeout
 * @Date 2018年10月25日 下午5:06:36
 * @ClassName ComplexMessage
 * @Description 创建多媒体的邮件内容
 *
 */
public class ComplexMessage {

	public static void main(String[] args) throws Exception {
		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = createMessage(session);
		message.writeTo(new FileOutputStream("d:\\ComplexMessage.eml"));
	}

	public static void sendMaid() {

	}

	public static MimeMessage createMessage(Session session) throws Exception {
		String from = "123456@qq.com";// 发件人地址
		String to = "123456@163.com"; // 收件人地址
		String subject = "HTML邮件"; // 邮件主题
		String body = "<a href=http://www.cnblogs.com>" + "欢迎大家访问博客园</a></br>" + "<img src=\"https://dev-1-1252654645.cos.ap-guangzhou.myqcloud.com/data/71803.jpg\">";

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);

		// 创建代表邮件正文和附件的各个MimeBodyPart对象
		MimeBodyPart contentPart = createContent(body, "C:\\Users\\LK.TimeOut.000\\Desktop\\TIM20180929171803.jpg");
		// 下面的附件可以是视频或者是音频
		MimeBodyPart attachPart1 = createAttachment("C:\\Users\\LK.TimeOut.000\\Desktop\\12306Bypass_1.12.88\\12306Bypass\\Music\\周二珂 - 告白气球.mp3");
		MimeBodyPart attachPart2 = createAttachment("C:\\Users\\LK.TimeOut.000\\Desktop\\QQ视频_0600664E3DF2AA69F5A710029238952E.mp4");

		// 创建用于组合邮件正文和附件的MimeMultipart对象
		MimeMultipart allMultipart = new MimeMultipart("mixed");
		allMultipart.addBodyPart(contentPart);
		allMultipart.addBodyPart(attachPart1);
		allMultipart.addBodyPart(attachPart2);

		// 设置整个邮件内容为最终组合出的MimeMultipart对象
		message.setContent(allMultipart);
		message.saveChanges();
		return message;
	}

	public static MimeBodyPart createContent(String body, String filename) throws Exception {
		/*
		 * 创建代表组合MIME消息的MimeMultipart对象， 和将该MimeMultipart对象保存到的MimeBodyPart对象
		 */
		MimeBodyPart contentPart = new MimeBodyPart();
		MimeMultipart contentMultipart = new MimeMultipart("related");

		/*
		 * 创建用于保存HTML正文的MimeBodyPart对象， 并将它保存到MimeMultipart中
		 */
		MimeBodyPart htmlBodyPart = new MimeBodyPart();
		htmlBodyPart.setContent(body, "text/html;charset=gb2312");
		contentMultipart.addBodyPart(htmlBodyPart);

		/*
		 * 创建用于保存图片的MimeBodyPart对象， 并将它保存到MimeMultipart中
		 */
		MimeBodyPart gifBodyPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filename);
		gifBodyPart.setDataHandler(new DataHandler(fds));
		gifBodyPart.setFileName(fds.getName());
		contentMultipart.addBodyPart(gifBodyPart);

		// 将MimeMultipart对象保存到MimeBodyPart对象中
		contentPart.setContent(contentMultipart);
		return contentPart;
	}

	public static MimeBodyPart createAttachment(String filename) throws Exception {
		// 创建保存附件的MimeBodyPart对象，并加入附件内容和相应信息
		MimeBodyPart attachPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filename);
		attachPart.setDataHandler(new DataHandler(fds));
		attachPart.setFileName(fds.getName());
		return attachPart;
	}

}
