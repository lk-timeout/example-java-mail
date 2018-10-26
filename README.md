# example-java-mail
java mail api的简单演示

最近有兴趣想研究下java mail发邮件的api。

这里的代码参考的网上很多的文章，有些错漏进行了修正，以及扩充。

有POP3协议和IMAP协议两种方式链接邮箱服务器。
pop3是不支持判断邮件是否为已读的，也就是说你不能直接从收件箱里面取到未读邮件，这需要自己进行判断，然而imap就提供了这样的功能，使用imap时可以很轻松的判断该邮件是否为已读或未读或其他。

代码修改了一些错漏，增加了乱码等，封装了一个简单的收发邮件的demo。

下面是博主的博文，有兴趣的可以逐一看。
这里所有的成果，大部分代码均来自该博主。

JavaMail入门第一篇 邮件简介及API概述 - 不能差不多 - 博客园
https://www.cnblogs.com/huangminwen/p/6069072.html

JavaMail入门第二篇 创建邮件 - 不能差不多 - 博客园
https://www.cnblogs.com/huangminwen/p/6072003.html

JavaMail入门第三篇 发送邮件 - 不能差不多 - 博客园
https://www.cnblogs.com/huangminwen/p/6087262.html

JavaMail入门第四篇 接收邮件 - 不能差不多 - 博客园
https://www.cnblogs.com/huangminwen/p/6096124.html

JavaMail入门第五篇 解析邮件 - 不能差不多 - 博客园
https://www.cnblogs.com/huangminwen/p/6107078.html


附相关知识点
[JavaMail] 详解Multipart和BodyPart - 猫三三 - CSDN博客
https://blog.csdn.net/waterdemo/article/details/71425504