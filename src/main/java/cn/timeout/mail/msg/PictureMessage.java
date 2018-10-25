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

public class PictureMessage {
    public static void main(String[] args) throws Exception {
        String from = "123456@qq.com";// 发件人地址
        String to = "123456@163.com"; // 收件人地址
        String subject = "HTML邮件";
        String body = "<a href=http://www.cnblogs.com>" + "欢迎大家访问博客园</a></br>"
                + "<img src=\"c:\\dog.jpg\">";

        Session session = Session.getDefaultInstance(new Properties());
        // 创建MimeMessage对象，并设置各种邮件头字段
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));
        message.setSubject(subject);

        // 创建一个子类型为"related"的MimeMultipart对象。
        MimeMultipart multipart = new MimeMultipart("related");

        /*
         * 创建一个表示HTML正文的MimeBodyPart对象， 并将它加入到前面创建的MimeMultipart对象中
         */
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(body, "text/html;charset=gb2312");
        multipart.addBodyPart(htmlBodyPart);

        /*
         * 创建一个表示图片内容的MimeBodyPart对象， 并将它加入到前面创建的MimeMultipart对象中
         */
        MimeBodyPart gifBodyPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource("c:\\dog.jpg");
        gifBodyPart.setFileName(fds.getName());
        gifBodyPart.setDataHandler(new DataHandler(fds));
        multipart.addBodyPart(gifBodyPart);

        /*
         * 将MimeMultipart对象设置为整个邮件的内容， 要注意调用saveChanges方法进行更新
         */
        message.setContent(multipart);
        message.saveChanges();

        // 把MimeMessage对象中的内容写入到文件中
        message.writeTo(new FileOutputStream("c:\\PictureMessage.eml"));
    }
}