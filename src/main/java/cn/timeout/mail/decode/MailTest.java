package cn.timeout.mail.decode;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

/**
 * @author timeout
 * @Date 2018年10月26日 下午3:14:46
 * @ClassName MailTest
 * @Description 测试类
 *
 */
public class MailTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String imapServer = "imap.qq.com";
			String protocol = "imap";
			String username = "lk_timeout@qq.com";
			String password = "xxxxxx"; // QQ邮箱的SMTP的授权码，什么是授权码，它又是如何设置？

			Properties p = new Properties();
			p.setProperty("mail.transport.protocol", protocol); // 使用的协议（JavaMail规范要求）
			p.setProperty("mail.smtp.host", imapServer); // 发件人的邮箱的 SMTP服务器地址

			// SSL安全连接参数
//			p.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			p.setProperty("mail.pop3.socketFactory.fallback", "true");
//			p.setProperty("mail.pop3.socketFactory.port", "995");

			Session session = Session.getDefaultInstance(p, null);
			Store store = session.getStore(protocol);
			store.connect(imapServer, username, password);
			Folder folder = store.getFolder("INBOX");
			FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			folder.open(Folder.READ_ONLY);
			Message message[] = folder.search(ft);
			System.out.println("邮件数量:　" + message.length);
			new GetMailInfoThread(message).start();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}