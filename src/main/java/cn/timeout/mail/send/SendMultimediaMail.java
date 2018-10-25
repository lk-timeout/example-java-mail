package cn.timeout.mail.send;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMultimediaMail {

	public static void main(String[] args) throws Exception {
		// 参考 https://blog.csdn.net/wty19/article/details/50607411
		System.setProperty("mail.mime.splitlongparameters", "false");
		sendMaid();
	}

	public static void sendMaid() throws Exception {
		String from = "lk_timeout@qq.com";
		String to = "lk.timeout@qq.com";
		String subject = "test";
		String body = "<a href=http://www.cnblogs.com>" + "欢迎大家访问博客园</a></br>" + "<img src=\"https://dev-1-1252654645.cos.ap-guangzhou.myqcloud.com/data/71803.jpg\">";
		String smtpHost = "smtp.qq.com";
		String authokey = "";

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", smtpHost); // 发件人的邮箱的 SMTP服务器地址
		props.setProperty("mail.smtp.auth", "true"); // 请求认证，参数名称与具体实现有关

		List<String> filePathList = new ArrayList<String>();
		filePathList.add("C:\\Users\\LK.TimeOut.000\\Desktop\\12306Bypass_1.12.88\\12306Bypass\\Music\\周二珂 - 告白气球.mp3");
		filePathList.add("C:\\Users\\LK.TimeOut.000\\Desktop\\QQ视频_0600664E3DF2AA69F5A710029238952E.mp4");

		// 创建Session实例对象
		Session session = Session.getDefaultInstance(props);
		// 创建MimeMessage实例对象
		MimeMessage message = createMessage(session, to, from, subject, body, filePathList);

		// 获取Transport对象
		Transport transport = session.getTransport("smtp");
		// 第2个参数需要填写的是QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？
		transport.connect(from, authokey);
		// 发送，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	/**
	 * @param session javax.mail
	 * @param to 接收方邮箱
	 * @param from 发送方邮箱
	 * @param subject 邮件主题
	 * @param body 邮件正文内容
	 * @param filePathList 需要上传的附件路径
	 * @return MimeMessage
	 * @throws Exception
	 * @Date 2018年10月25日 下午7:15:06
	 */
	public static MimeMessage createMessage(Session session, String to, String from, String subject, String body, List<String> filePathList) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// 设置发件人
		message.setFrom(new InternetAddress(from));
		// 设置收件人
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		// 设置发送日期
		message.setSentDate(new Date());
		// 设置邮件主题
		message.setSubject(subject);
		// 设置为debug模式, 可以查看详细的发送 log
		session.setDebug(true);

		// 创建用于组合邮件正文和附件的MimeMultipart对象
		MimeMultipart allMultipart = new MimeMultipart("mixed");

		// 创建代表邮件正文和附件的各个MimeBodyPart对象
		MimeBodyPart contentPart = createContent(body);
		allMultipart.addBodyPart(contentPart);
		for (String filePath : filePathList) {
			MimeBodyPart attachPart = createAttachment(filePath);
			allMultipart.addBodyPart(attachPart);
		}

		// 设置整个邮件内容为最终组合出的MimeMultipart对象
		message.setContent(allMultipart);
		message.saveChanges();
		return message;
	}

	/**
	 * @param body 邮件正文内容
	 * @return 返回MimeBodyPart
	 * @throws MessagingException
	 * @throws Exception
	 * @Date 2018年10月25日 下午6:44:40
	 */
	public static MimeBodyPart createContent(String body) throws MessagingException {
		/*
		 * 创建用于保存HTML正文的MimeBodyPart对象， 并将它保存到MimeMultipart中
		 */
		MimeBodyPart htmlBodyPart = new MimeBodyPart();
		htmlBodyPart.setContent(body, "text/html;charset=gb2312");
		return htmlBodyPart;
	}

	/**
	 * @param filename 文件名称路径
	 * @return MimeBodyPart
	 * @throws Exception
	 * @Date 2018年10月25日 下午6:46:33
	 */
	public static MimeBodyPart createAttachment(String filename) throws Exception {
		// 创建保存附件的MimeBodyPart对象，并加入附件内容和相应信息
		MimeBodyPart attachPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filename);
		attachPart.setDataHandler(new DataHandler(fds));
		// 解决文件为中文时候乱码
		attachPart.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", null));
		return attachPart;
	}

}
