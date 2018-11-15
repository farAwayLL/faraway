package com.sboot.study.service;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author : faraway
 * @Date : create in 2018/11/15 14:46
 * @Description :
 */
@Service
public class QiniuService {

    private static final Logger log = LoggerFactory.getLogger(QiniuService.class);

    //七牛分配的的二级域名
    private String qnDomainName = "http://pi866ebo6.bkt.clouddn.com";

    private String IMAGE_PREFIX = "image/";

    //七牛云存储用户标识
    @Value("${qiniu.ak}")
    private String accessKey = null;

    //七牛云储存密钥
    @Value("${qiniu.sk}")
    private String secketKey = null;

    //
    @Value("${qiniu.bucket}")
    private String bucketName = null;

    /**
     * 上传图片七牛方法
     * 不管哪个方法都需要调用该方法，该方法是上传的核心方法
     * @param uploadBytes
     * @param name
     * @param token
     */
    public Response uploadToQn(byte[] uploadBytes, String name, String token) throws Exception {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        return uploadManager.put(uploadBytes, name, token);
    }

    /**
     * 上传图片到七牛
     *
     * @throws Exception
     */
    public String uploadImage(MultipartFile imageFile) throws Exception {
        Auth auth = Auth.create(accessKey, secketKey);
        String token = auth.uploadToken(bucketName);//token其实就是验证ak,sk,bucket对不对

        //相对路径
        String urlPath = IMAGE_PREFIX + imageFile.getOriginalFilename();
        //上传----------------参数：文件的字节码---------------相对路径
        Response response = uploadToQn(imageFile.getBytes(), urlPath, token);

        //图片全路径,保存在数据库中
        return qnDomainName + "/" + urlPath;
    }

}
