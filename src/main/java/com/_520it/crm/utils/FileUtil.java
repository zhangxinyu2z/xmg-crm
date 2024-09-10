package com._520it.crm.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 *@author vnruxc
 *
 */
public class FileUtil {


    /**
     * 文件上传处理
     * 将上传的文件保存到相应目录
     * @param multipartFile
     * @return  文件的相对与web根目录的保存路径
     */
    public static String upload(MultipartFile multipartFile){

        InputStream in=null;
        OutputStream out=null;
        String filename=null;
        try {

            in = multipartFile.getInputStream();
            String realpath= UserContext.get().getSession().getServletContext().getRealPath("/netdisk");
            System.out.println(realpath);
            String originalname=multipartFile.getOriginalFilename();
            String filetype=getExt(originalname);
             filename= UUID.randomUUID().toString()+"."+filetype;

            out=new FileOutputStream(new File(realpath,filename));
            IOUtils.copy(in,out);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out!=null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(in!=null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "/netdisk/"+filename;
    }


    public static String mkdir(String parentPath) {

        String path=null;
        try {
            String absParentPath;
            if(parentPath==null){
                parentPath="/netdisk";
                absParentPath= UserContext.get().getSession().getServletContext().getRealPath("/netdisk");
            }
            absParentPath= UserContext.get().getSession().getServletContext().getRealPath(parentPath);
            String dirname= UUID.randomUUID().toString();
            File newDir=new File(absParentPath,dirname);
            newDir.mkdir();
            path=parentPath+"/"+dirname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    public static InputStream getDownloadStream(String path) {
        System.out.println("path = " + path);
        System.out.println(UserContext.get().getSession().getServletContext().getRealPath("/netdisk"));
        String absParentPath= UserContext.get().getSession().getServletContext().getRealPath(path);
        //System.out.println("UserContext.getLocalRequest().getSession().getServletContext() = " + UserContext.getLocalRequest().getSession().getServletContext());
        System.out.println("absParentPath = " + absParentPath);
        System.out.println("=========================");
        FileInputStream fileInputStream=null;
        try {
            fileInputStream = new FileInputStream(absParentPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return fileInputStream;
    }


    public static String getExt(String fileName) {
        String ext = "";
        int i = fileName.lastIndexOf(".");
        if (i >= 0) {
            ext = fileName.substring(i + 1);
        }
        return ext;
    }
}




