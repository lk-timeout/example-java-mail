package cn.timeout.mail.receive;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import com.sun.mail.imap.IMAPMessage;

public class ReceiveIMAPMail {

	public static void main(String[] args) throws Exception {
		// 定义连接IMAP服务器的属性信息
		String imapServer = "imap.qq.com";
		String protocol = "imap";
		String username = "lk_timeout@qq.com";
		String password = "xxxxxxxxxxx"; // QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？

		Properties props = new Properties();
		// 如果报SSL错误或者提示用授权码登录，则要加上这两行。
//		props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
//		props.setProperty("mail.pop3.port", "995");

		props.setProperty("mail.transport.protocol", protocol); // 使用的协议（JavaMail规范要求）
		props.setProperty("mail.smtp.host", imapServer); // 发件人的邮箱的 SMTP服务器地址

		// 获取连接
		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		// 获取Store对象
		Store store = session.getStore(protocol);
		store.connect(imapServer, username, password); // POP3服务器的登陆认证

		// 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
		Folder folder = store.getFolder("INBOX");// 获得用户的邮件帐户
		folder.open(Folder.READ_WRITE); // 设置对邮件帐户的访问权限

		// 获取邮件总数
		System.out.println("=====" + folder.getMessageCount());
		// 获取未读邮件数
		System.out.println("=====" + folder.getUnreadMessageCount());
		// 获取删除数据，暂未测试，一直0
		System.out.println("=====" + folder.getDeletedMessageCount());

		/**
		 * Flag 类型列举如下
		 * Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
		 * Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
		 * Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
		 * Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
		 * Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
		 * Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
		 * Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
		 */
		// 增加过滤筛选条件 找出未读邮件
		FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false); //false代表未读，true代表已读
//		Message[] messages = folder.getMessages();// 得到邮箱帐户中的所有邮件
		Message[] messages = folder.search(ft);// 得到邮箱帐户中的满足过滤条件的所有邮件
		for (Message message : messages) {
			IMAPMessage msgImapMessage = (IMAPMessage) message;
			msgImapMessage.setFlag(Flags.Flag.SEEN, true); // 标记为已读邮件
			String subject = message.getSubject();// 获得邮件主题
			Address from = message.getFrom()[0];// 获得发送者地址
			System.out.println("邮件的主题为: " + subject + "\t发件人地址为: " + from);
			System.out.println("邮件的内容为：");
//			message.writeTo(System.out);// 输出邮件内容到控制台
		}

		folder.close(false);// 关闭邮件夹对象
		store.close(); // 关闭连接对象
	}
}
