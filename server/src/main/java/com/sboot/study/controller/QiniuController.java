package com.sboot.study.controller;

import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import com.sboot.study.service.QiniuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Author : faraway
 * @Date : create in 2018/11/15 15:26
 * @Description :
 */

@RestController
public class QiniuController {

    private static final Logger log = LoggerFactory.getLogger(QiniuController.class);

    private static final String PREFIX = "qiniu";

    @Autowired
    private QiniuService qiniuService;

    /**
     * 上传图片
     *
     * @return
     */
    @RequestMapping(value = PREFIX + "/uploadImage", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public BaseResponse uploadImage(MultipartHttpServletRequest request) {
        BaseResponse response = new BaseResponse(StatusCode.SUCCESS);
        try {
            MultipartFile file = request.getFile("fileName");
            if (file == null) {
                return new BaseResponse(StatusCode.INVALID_PARAMS);
            }

            //上传并返回地址
            final String location = qiniuService.uploadImage(file);
            log.info("该附件最终上传位置： {} ", location);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.FAIL.getCode(), "上传图片失败！");
            e.printStackTrace();
        }
        return response;
    }

}
