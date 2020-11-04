package com.ek.project.system.form.controller;


import com.ek.common.utils.IpUtils;
import com.ek.common.utils.StringUtils;
import com.ek.common.utils.UuidUtil;
import com.ek.common.utils.file.FileUtils;
import com.ek.framework.config.EKConfig;
import com.ek.framework.config.ServerConfig;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.project.system.form.domain.FileInfo;
import com.ek.project.system.form.service.FormFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Controller
public class FormFileController {
    private static final Logger log = LoggerFactory.getLogger(FormFileController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private FormFileService formFileService;

    /**
     * 表单文件下载请求
     *
     * @param fileName 文件名称
     */
    @RequestMapping("/form/download/{fileName:.+}")
    public void fileDownload(@PathVariable("fileName")String fileName, HttpServletResponse response, HttpServletRequest request) {
        try {
            String uploadPath = EKConfig.getUploadPath();
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            String dir = "/attachment";
            if(suffix.equals("jpg") || suffix.equals("gif") ||suffix.equals("png") ) {
                dir = "/picture";
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = uploadPath+dir+File.separator+fileName;
            if(!new File(filePath).exists()) {
                throw new RuntimeException();
            };
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());

        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }



    /**
     * 表单文件上传请求
     */
    @PostMapping("/form/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file,HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            // 上传文件路径
            String filePath = EKConfig.getUploadPath();
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

            // 保存文件到本地
            String key = UuidUtil.getShortUuid();


            String dir = "/attachment";
            if(suffix.equals("jpg") || suffix.equals("gif") ||suffix.equals("png") ) {
                dir = "/picture";
            }

            //如果文件夹不存在则创建
            File fullDir = new File(filePath+dir);
            if (!fullDir.exists()) {
                System.out.println("创建文件夹:"+filePath+dir);
                fullDir.mkdirs();
            }

            String path =  key + "." + suffix;

            String fullPath = filePath+dir+ File.separator+path;
            File dest = new File(fullPath);
            file.transferTo(dest);
            System.out.println("上传文件："+dest.getAbsolutePath());


            FileInfo fileInfo= new FileInfo();
            fileInfo.setPath(fullPath);
            fileInfo.setName(fileName);
            fileInfo.setSuffix(suffix);
            fileInfo.setIp(IpUtils.getIpAddr());
            fileInfo.setSize((int) file.getSize());
            formFileService.save(fileInfo);

            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", "/form/download/"+path);
            return ajax;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }


}
