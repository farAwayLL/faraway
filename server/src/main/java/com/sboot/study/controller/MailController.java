package com.sboot.study.controller;

import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Maps;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import com.sboot.study.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author : faraway
 * @Date : create in 2018/11/13 16:08
 * @Description : 发送邮件
 */
@Api(description = "发送邮件")
@RestController
public class MailController {

    private static final Logger log = LoggerFactory.getLogger(MailController.class);

    private static final String PREFIX = "mail";

    @Autowired
    private MailService mailService;

    @Autowired
    private Environment environment;

    /**
     * 发送简单邮件
     *
     * @param valueMap
     * @return
     */
    @PostMapping(PREFIX + "/sendEmail")
    @ApiOperation("发送简单邮件")
    public BaseResponse sendEmail(@RequestBody ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            log.debug("接受前端邮件信息：{}", valueMap);
            mailService.sendEmail(valueMap);
        } catch (Exception e) {
            log.error("发送邮件失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "发送邮件失败！");
        }
        return response;
    }

    /**
     * 发送带附件邮件
     *
     * @param valueMap
     * @return
     */
    @PostMapping(PREFIX + "/sendAttachmentEmail")
    @ApiOperation("发送带附件邮件")
    public BaseResponse sendAttachmentEmail(@RequestBody ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            log.debug("接受前端邮件信息：{}", valueMap);
            mailService.sendAttachmentEmail(valueMap);
        } catch (Exception e) {
            log.error("发送带附件邮件失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "发送带附件邮件失败！");
        }
        return response;
    }

    @PostMapping(PREFIX + "/sendHtmlEmail")
    @ApiOperation("发送带模板邮件")
    public BaseResponse sendHtmlEmail(@RequestBody ModelMap valueMap) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            log.debug("接受前端邮件信息：{}", valueMap);
            String html = mailService.renderTemplate(environment.getProperty("mail.template.file.location"), valueMap);
            valueMap.put("html",html);
            mailService.sendHtmlEmail(valueMap);
        } catch (Exception e) {
            log.error("发送带模板邮件失败！", e.fillInStackTrace());
            response = new BaseResponse(StatusCode.FAIL.getCode(), "发送带模板邮件失败！");
        }
        return response;
    }
}
