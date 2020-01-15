package com.gerry.jnshu.controller;

import com.gerry.jnshu.core.CommonResult;
import com.gerry.jnshu.pojo.EmailInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("image/")
public class UploadController {

    @PostMapping("/upload")
    public CommonResult uploadFile(HttpServletRequest request){
//        public CommonResult uploadFile2(@RequestParam("file") MultipartFile[] files){

        //获取前端上传的文件列表
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (files==null||files.size()==0) {
//        if (files==null) {
            return CommonResult.error(-1,"文件不能为空") ;
        }

        String fileName = files.get(0).getOriginalFilename();
        String filePath = request.getContextPath()+"/images/";
        File dest = new File(filePath + fileName);
        try {
            files.get(0).transferTo(dest);
            return CommonResult.success(null,"上传成功");
        } catch (IOException e) {

        }
        return CommonResult.error(-1,"文件上传失败");

    }

}
