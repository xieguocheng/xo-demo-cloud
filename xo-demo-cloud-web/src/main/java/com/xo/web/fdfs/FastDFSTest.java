
package com.xo.web.fdfs;

import java.io.File;
import java.io.InputStream;

/**
 * 包名称： com.xo.web
 * 类名称：FastDFSTest
 * 类描述：FastDFSTest
 * 创建人： xieguocheng
 * 创建时间：2021/03/08 17:07
 */


public class FastDFSTest {

    /**
     * 上传
     */
    public static void upload() throws Exception {
        // id:    group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
        // fastdfsBasePath = http://192.168.1.170/fastdfs
        // url:    http://192.168.1.170/fastdfs/group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
        String path = "C:/Users/xgc10/Desktop/功能.docx";
        File file = new File(path);
        String fileId = FastDFSClientUtils.upload(file, path);
        System.out.println("本地文件：" + path + "，上传成功！ 文件ID为：" + fileId);
    }

    /**
     * 下载
     */
    public static void download() throws Exception {
        // id:    group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
        // url:    http://192.168.1.170/fastdfs/group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg
        String fileId = "group1/M00/00/00/ag6mQmBF7uKADtgPAAC7Am1EkK028.docx";
        InputStream inputStream = FastDFSClientUtils.download("group1", fileId);
        System.out.println(inputStream.available());
        String path = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "001.jpg";
        System.out.println(path);
        //FileUtils.copyInputStreamToFile(inputStream,  new File(path));
    }

    /**
     * 删除
     */
    public static void delete() throws Exception {
        String fileId = "group1/M00/00/00/wKgBr1crVnSAbI4sAAAeV2aU4jU126.jpg";
        int result = FastDFSClientUtils.delete("group1", fileId);
        System.out.println(result == 0 ? "删除成功" : "删除失败:" + result);
    }


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        upload();
        download();
        delete();

    }

}
