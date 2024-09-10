package com._520it.crm.web.controller;

import com._520it.crm.domain.Employee;
import com._520it.crm.domain.NetworkDisk;
import com._520it.crm.page.AjaxResult;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.NetworkDiskQueryObject;
import com._520it.crm.service.INetworkDiskService;
import com._520it.crm.util.FileUtil;
import com._520it.crm.util.UserContext;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 *网盘
 */
@Controller
public class NetworkDiskController {

    @Autowired
    private INetworkDiskService networkDiskService;

    @RequestMapping("/netdisk_list")
    @ResponseBody
    public PageResult NetworkDiskList(NetworkDiskQueryObject qo){
        System.out.println("qo========111======================"+qo);
        Long parentId=null;
        if(qo.getCurrentId()!=null){
            NetworkDisk NetworkDisk = networkDiskService.get(qo.getCurrentId());
            parentId = NetworkDisk.getParentId();
            qo.setPid(parentId);
        }
        System.out.println("qo==============2222================"+qo);
        PageResult ret=networkDiskService.query(qo);
        ret.getData().put("currentId",parentId);
       return ret;
    }

    //网盘主页
    @RequestMapping("/netdisk")
    public String index(){
        return "netdisk";
    }


    //上传处理
    @RequestMapping("/netdisk_upload")
    @ResponseBody
    public AjaxResult upload(HttpSession session, MultipartFile mf, Long pid){

        try {
            NetworkDisk NetworkDisk = new NetworkDisk();
            String ofname=mf.getOriginalFilename();
            NetworkDisk.setName(ofname);
            NetworkDisk.setDir(false);
            NetworkDisk.setParentId(pid);
            NetworkDisk.setUploadtime(new Date());
            String type= FileUtil.getExt(ofname);
            NetworkDisk.setType(type);
           NetworkDisk.setPub(false);
            System.out.println(mf.getContentType());
            Employee user= (Employee) session.getAttribute(UserContext.USER_IN_SESSION);
            NetworkDisk.setUser(user);
            String path = FileUtil.upload(mf);
            NetworkDisk.setPath(path);
            System.out.println("NetworkDisk = " + NetworkDisk);
            networkDiskService.save(NetworkDisk);
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("上传失败");
        }
        return new AjaxResult(true,"上传成功");
    }

    //新建文件夹
    @RequestMapping("/netdisk_mkdir")
    @ResponseBody
    public AjaxResult mkdir(HttpSession session, Long pid, String name){

        try {

            String parentPath = null;

            if(pid!=null){
               parentPath = networkDiskService.get(pid).getPath();
            }
            String path= FileUtil.mkdir(parentPath);
            NetworkDisk NetworkDisk = new NetworkDisk();
            Employee user= (Employee) session.getAttribute(UserContext.USER_IN_SESSION);

            NetworkDisk.setPath(path);
            NetworkDisk.setUser(user);
            NetworkDisk.setName(name);
            NetworkDisk.setUploadtime(new Date());
            NetworkDisk.setDir(true);
            NetworkDisk.setParentId(pid);
            networkDiskService.save(NetworkDisk);
            return new AjaxResult(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(true,"操作失败");
        }

    }


    //重命名
    @RequestMapping("/netdisk_rename")
    @ResponseBody
    public AjaxResult rename(NetworkDisk NetworkDisk){
        try {
            System.out.println("NetworkDisk = " + NetworkDisk);
            networkDiskService.update(NetworkDisk);
            return new AjaxResult(true,"重命名成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult("操作失败");
        }
    }

    @RequestMapping("/netdisk_download")
    public ModelAndView download(HttpServletResponse resp,Long id){
        System.out.println("FileController.download");
        NetworkDisk networkDisk = networkDiskService.get(id);
        if(!networkDisk.isDir()){
            OutputStream out=null;
            InputStream in=null;
            try {
                System.out.println(networkDisk.getName());
                //resp.setCharacterEncoding("utf-8");
                resp.addHeader("Content-Disposition","attachment;filename="+new String(networkDisk.getName().getBytes("utf-8"),"iso-8859-1"));
                //resp.setHeader("Content-Disposition","attachment;filename="+NetworkDisk.getName());
                out=resp.getOutputStream();
                //String path = "/"+networkDisk.getPath().split("/")[1];
                String path = networkDisk.getPath();
                in= FileUtil.getDownloadStream(path);
                //in=new FileInputStream("/home/vnruxc/temp/abc.gif");
                IOUtils.copy(in,out);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(in!=null){
                        in.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


}
