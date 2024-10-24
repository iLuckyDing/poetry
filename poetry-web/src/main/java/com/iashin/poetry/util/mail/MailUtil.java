package com.iashin.poetry.util.mail;

import com.alibaba.fastjson.JSON;
import com.iashin.poetry.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;

/**
 * @date: 2024/10/24
 * @project: poetry
 * @email: dingzhen7186@163.com
 * @author: dingzhen
 */
@Component
@Slf4j
public class MailUtil {

    /**
     * 1. 来源人名
     * 2. 来源内容
     */
    public static final String ORIGINAL_TEXT = "<hr style=\"border: 1px dashed #ef859d2e;margin: 20px 0\">\n" +
            "            <div>\n" +
            "                <div style=\"font-size: 18px;font-weight: bold;color: #C5343E\">\n" +
            "                    %s\n" +
            "                </div>\n" +
            "                <div style=\"margin-top: 6px;font-size: 16px;color: #000000\">\n" +
            "                    <p>\n" +
            "                        %s\n" +
            "                    </p>\n" +
            "                </div>\n" +
            "            </div>";

    /**
     * 发件人
     */
    public static final String REPLY_MAIL = "你之前的评论收到来自 %s 的回复";
    public static final String COMMENT_MAIL = "你的文章 %s 收到来自 %s 的评论";
    public static final String MESSAGE_MAIL = "你收到来自 %s 的留言";
    public static final String LOVE_MAIL = "你收到来自 %s 的祝福";
    public static final String IM_MAIL = "你收到来自 %s 的消息";
    public static final String NOTIFICATION_MAIL = "你收到来自 %s 的订阅";

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 1. 网站名称
     * 2. 邮件类型
     * 3. 发件人
     * 4. 发件内容
     * 5. originalText
     * 6. 网站名称
     */
    private String mailText;

    @PostConstruct
    public void init() {
        this.mailText = "<div style=\"font-family: serif;line-height: 22px;padding: 30px\">\n" +
                "    <div style=\"display: flex;flex-direction: column;align-items: center\">\n" +
                "        <div style=\"margin: 10px auto 20px;text-align: center\">\n" +
                "            <div style=\"line-height: 32px;font-size: 26px;font-weight: bold;color: #000000\">\n" +
                "                嘿！你在 %s 中收到一条新消息。\n" +
                "            </div>\n" +
                "            <div style=\"font-size: 16px;font-weight: bold;color: rgba(0, 0, 0, 0.19);margin-top: 21px\">\n" +
                "                %s\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div style=\"min-width: 250px;max-width: 800px;min-height: 128px;background: #F7F7F7;border-radius: 10px;padding: 32px\">\n" +
                "            <div>\n" +
                "                <div style=\"font-size: 18px;font-weight: bold;color: #C5343E\">\n" +
                "                    %s\n" +
                "                </div>\n" +
                "                <div style=\"margin-top: 6px;font-size: 16px;color: #000000\">\n" +
                "                    <p>\n" +
                "                        %s\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            %s\n" +
                "            <a style=\"width: 150px;height: 38px;background: #ef859d38;border-radius: 32px;display: flex;align-items: center;justify-content: center;text-decoration: none;margin: 40px auto 0\"\n" +
                "               href=\"https://poetize.cn\" target=\"_blank\">\n" +
                "                <span style=\"color: #DB214B\">有朋自远方来</span>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <div style=\"margin-top: 20px;font-size: 12px;color: #00000045\">\n" +
                "            此邮件由 %s 自动发出，直接回复无效（一天最多发送 " + CommonConstant.COMMENT_IM_MAIL_COUNT + " 条通知邮件和 " + CommonConstant.CODE_MAIL_COUNT + " 条验证码邮件），退订请联系站长。\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
    }

    public String getMailText() {
        return mailText;
    }

    @Async
    public void sendMailMessage(List<String> to, String subject, String text) {
        log.info("发送邮件===================");
        log.info("to：{}", JSON.toJSONString(to));
        log.info("subject：{}", subject);
        log.info("text：{}", text);
        try {
            //true代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人1或多个
            mimeMessageHelper.setTo(to.toArray(new String[0]));
            //邮件主题
            mimeMessageHelper.setSubject(subject);
            //邮件内容
            mimeMessageHelper.setText(text, true);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            mailSender.send(mimeMessageHelper.getMimeMessage());

            log.info("发送成功==================");
        } catch (MessagingException e) {
            log.info("发送失败==================");
            log.error(e.getMessage());
        }
    }
}
