package com.atguigu.system.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.BusinessException;
import com.atguigu.common.util.CookieUtils;
import com.atguigu.common.util.ResponseEnum;
import com.atguigu.model.system.SysImages;
import com.atguigu.model.system.SysUser;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.UserImagesMapper;
import com.atguigu.system.service.OssService;
import com.atguigu.system.config.OssProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 7/6/2023 7:46 PM
 */
@Service
//不需要这一步,我们已经在属性的配置类那个地方使用了@Configuration,会创建对象注入IOC容器的,或者也可以使用下面的注解
//指定当前组件类初始化之前必须创建OssProperties对象注入到容器中
// @EnableConfigurationProperties(OssProperties.class)
@Slf4j
public class OssServiceImpl implements OssService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserImagesMapper userImagesMapper;

    @Override
    public String upload(MultipartFile file, String module, HttpServletRequest request, HttpServletResponse response) {
        //从请求头中获取token,从而可以知道目前上传图片的用户是哪一个 230907
        // String token = request.getHeader("token"); 这种方式获取不到,不知道为什么
        String token = CookieUtils.getCookieValue(request, "vue_admin_template_token");
        SysUser sysUser = (SysUser)redisTemplate.boundValueOps(token).get();

        //如果获取不到sysUser有可能是用户没有登录或者登录的信息已经过期了,可以抛出异常
        if (sysUser == null){
            //进来这里面说明登录的信息已经过期了或者用户压根就没有登录
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }

        //1.先定义文件的名字objectName
        //最终拼出来的效果类似于 avatar/2023/07/07/23423423423_xcsdf.jpg
        String objectName = module + "-" + sysUser.getName() +  new DateTime().toString("/yyyy/MM/dd/") +
                System.currentTimeMillis() + "_" + UUID.randomUUID().toString()
                //包含开始,不包含结束
                .substring(0,6) + file.getOriginalFilename().substring(file.getOriginalFilename()
                //取文件的后缀名
                .lastIndexOf("."));
        //2.定义文件的存储的路径用来回显
        String path = OssProperties.SCHEMA + OssProperties.BUCKETNAME + "." + OssProperties.ENDPOINT + "/" + objectName;
        OSS ossClient = null;
        //3.创建输入流
        try {
            ossClient = getOssClient();
            InputStream inputStream = file.getInputStream();
            //4.创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(OssProperties.BUCKETNAME, objectName, inputStream);
            //5.创建PutObjcet请求
            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);

            //将文件的路径保存到图片表里面
            SysImages sysImages = new SysImages();
            sysImages.setImageName(module);
            sysImages.setUsername(sysUser.getName());
            sysImages.setUrl(path);
            sysImages.setUserId(sysUser.getId());
            sysImages.setCreateTime(new Date());
            sysImages.setUpdateTime(sysImages.getCreateTime());
            userImagesMapper.insert(sysImages);

            //6.返回文件的路径给前端做回显
            return path;
        } catch (IOException e) {
            //打印异常的堆栈信息
            log.error("文件上传失败,失败信息是:{}",ExceptionUtils.getStackTrace(e));
            //抛出我们的自定义异常
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR);
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    @Override
    public void deleteByPath(String path) {
        //从path中获取objectName,这个是要删除的文件在桶内的完整的路径,下面删除的方法里面需要这个参数
        String objectName = path.replace(OssProperties.SCHEMA + OssProperties.BUCKETNAME + "." + OssProperties.ENDPOINT + "/", "");

        //获取ossClient对象
        OSS ossClient = null;

        try {
            ossClient = getOssClient();
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(OssProperties.BUCKETNAME, objectName);

        } catch (OSSException e) {
            //打印异常的堆栈信息
            log.error("文件上传失败,失败信息是:{}",ExceptionUtils.getStackTrace(e));
            //抛出我们的自定义异常
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }

    //获取ossClient的方法单独的抽出来
    private OSS getOssClient(){
        //从系统的环境变量里面获取access ID和access sevret用来创建凭证
        try {
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            //创建ossClient的实例
            OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT,credentialsProvider);
            return ossClient;
        } catch (ClientException e) {
            //打印异常的堆栈信息
            log.error(ExceptionUtils.getStackTrace(e));
            //抛出我们的自定义的异常
            throw new BusinessException(ResponseEnum.WEIXIN_FETCH_ACCESSTOKEN_ERROR);
        }
    }
}
