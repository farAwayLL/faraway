package com.sboot.study.controller;

import com.google.common.base.Strings;
import com.sboot.study.entity.Appendix;
import com.sboot.study.response.BaseResponse;
import com.sboot.study.response.StatusCode;
import com.sboot.study.service.QiniuService;
import io.swagger.annotations.Api;
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

@Api(description = "七牛云上传")
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
            //获取上传的文件
            MultipartFile file = request.getFile("fileName");
            //获取文件所属的模块(例如上传图片是属于订单or商品？)
            String moduleType = request.getParameter("moduleType");
            if (file == null || Strings.isNullOrEmpty(moduleType)) {
                return new BaseResponse(StatusCode.INVALID_PARAMS);
            }

            //开始上传，并返回地址
            final String location = qiniuService.uploadImage(file,moduleType);
            //将上传信息保存到数据库
            log.info("该附件最终上传位置： {} ", location);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.FAIL.getCode(), "上传图片失败！");
            e.printStackTrace();
        }
        return response;
    }

}
