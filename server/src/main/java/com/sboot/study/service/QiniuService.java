package com.sboot.study.service;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.sboot.study.entity.Appendix;
import com.sboot.study.mybatisMapper.AppendixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : faraway
 * @Date : create in 2018/11/15 14:46
 * @Description :
 */
@Service
public class QiniuService {

    private static final Logger log = LoggerFactory.getLogger(QiniuService.class);

    //七牛分配的的二级域名,使用期限30天,过期将无法通过该域名访问图片
    @Value("${qiniu.domain}")
    private String qnDomainName;

    private String IMAGE_PREFIX = "image";

    //七牛云存储的用户标识
    @Value("${qiniu.ak}")
    private String accessKey;

    //七牛云储存的密钥
    @Value("${qiniu.sk}")
    private String secketKey;

    //七牛云储存空间
    @Value("${qiniu.bucket}")
    private String bucketName;

    @Autowired
    private AppendixMapper appendixMapper;

    /**
     * 上传都需要调用该方法，该方法是上传的核心方法
     *
     * @param uploadBytes
     * @param name
     * @param token
     */
    public Response uploadToQn(byte[] uploadBytes, String name, String token) throws Exception {
        // 构造一个带指定Zone对象的配置类
        //		华东	Zone.zone0()
        //		华北	Zone.zone1()
        //		华南	Zone.zone2()
        //		北美	Zone.zoneNa0()
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
    public String uploadImage(MultipartFile imageFile, String moduleType) throws Exception {
        //用户标识和密钥生成Auth信息
        Auth auth = Auth.create(accessKey, secketKey);
        //生成token信息
        String token = auth.uploadToken(bucketName);
        //获取文件的文件名,用来保存到数据库
        String fileName = imageFile.getOriginalFilename();

        //改造文件上传的文件名字，保存到七牛云
        String uploadFileName = fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        uploadFileName = sdf.format(new Date()) + uploadFileName;

        //相对路径(也可以认为就是文件名)
        String urlPath = IMAGE_PREFIX + "/" + uploadFileName;
        //开始上传-----------------参数：文件的字节码-----------相对路径--token信息
        uploadToQn(imageFile.getBytes(), urlPath, token);

        //绝对路径------------------------File.separator表示"/",保证在windows和linux系统都可行
        String location = qnDomainName + "/" + urlPath;
        //将上传的详细信息保存到数据库
        Appendix appendix = new Appendix();
        appendix.setLocation(location);
        appendix.setModuleType(moduleType);
        appendix.setName(fileName);
        appendix.setSize(imageFile.getSize());
        appendixMapper.insertSelective(appendix);
        return location;
    }

}
