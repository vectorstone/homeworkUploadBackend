package com.atguigu.system.service;

import com.atguigu.model.vo.Classmates;
import com.atguigu.model.vo.SubmitStatus;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClassmatesService extends IService<Classmates> {
    void importExcel(MultipartFile file);

    List<SubmitStatus> querySubmitStatus(List<String> homeworkList);
}
