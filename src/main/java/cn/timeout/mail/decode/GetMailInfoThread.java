package cn.timeout.mail.decode;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author timeout
 * @Date 2018年10月26日 下午3:14:19
 * @ClassName GetMailInfoThread
 * @Description 用于获取邮箱中邮件信息的线程
 *
 */
public class GetMailInfoThread extends Thread {
	Message message[] = null;
	DecodeMail re = null;

	public GetMailInfoThread(Message message[]) {
		this.message = message;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		if (null != message) {
			for (int i = 0; i < message.length; i++) {
				try {
					re = new DecodeMail((MimeMessage) message[i]);

					System.out.println("邮件　" + i + "　主题:　" + re.getSubject());
					System.out.println("邮件　" + i + "　是否需要回复:　" + re.getReplySign());
					System.out.println("邮件　" + i + "　是否已读:　" + re.isNew());
					System.out.println("邮件　" + i + "　是否包含附件:　" + re.isContainAttach(message[i]));
					System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
					System.out.println("邮件　" + i + "　发送人地址:　" + re.getFrom());
					System.out.println("邮件　" + i + "　收信人地址:　" + re.getMailAddress("to"));
					System.out.println("邮件　" + i + "　抄送:　" + re.getMailAddress("cc"));
					System.out.println("邮件　" + i + "　暗抄:　" + re.getMailAddress("bcc"));
					re.setDateFormat("yyyy年MM月dd日");
					System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());
					System.out.println("邮件　" + i + "　邮件ID:　" + re.getMessageId());
					re.getMailContent(message[i]);
					System.out.println("邮件　" + i + "　正文内容:　\r\n" + re.getBodyText());
					// 保存附件
					re.saveAttachMent(message[i]);
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
