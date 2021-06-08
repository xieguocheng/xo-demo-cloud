/****************************************************
 * 创建人：  @author xieguocheng
 * 创建时间: 2021/03/08 16:46
 * 项目名称:  yh-risk-parent
 * 文件名称: FastDFSHelper
 * 文件描述: FastDFSHelper
 * 公司名称: 深圳市赢和信息技术有限公司
 *
 * All rights Reserved, Designed By 深圳市赢和信息技术有限公司
 * @Copyright:2016-2019
 *
 ********************************************************/
package com.xo.web.fdfs;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 包名称： com.xo.web
 * 类名称：FastDFSHelper
 * 类描述：FastDFSHelper
 * 创建人： xieguocheng
 * 创建时间：2021/03/08 16:46
 */
public final class FastDFSHelper {

    private static final Logger logger = LoggerFactory.getLogger(FastDFSHelper.class);
    private static TrackerClient trackerClient;

    static {
        try {
            ClientGlobal.init("fdfs_client.conf");
            trackerClient = new TrackerClient();
        } catch (IOException | MyException e) {
            logger.error("error", e);
        }
    }

    public static void main(String[] args) {
        uploadFile("C:/Users/xgc10/Desktop/功能.docx");
        downloadFile("hhh.docx", "group1", "M00/00/00/ag6mQmBF56iAI4ROAAC7Am1EkK051.docx");
        deleteFile("group1", "M00/00/00/ag6mQmBF56iAI4ROAAC7Am1EkK051.docx");
    }


    /**
     * 向FastDFS上传文件
     *
     * @param localFilename 本地文件名
     * @return 上传成功，返回组名和该文件在FastDFS中的名称；上传失败，返回null
     */
    public static void uploadFile(String localFilename) {
        TrackerServer trackerServer;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            logger.error("error", e);
            return;
        }
        StorageClient storageClient = new StorageClient(trackerServer, null);
        try {
            String[] arr = storageClient.upload_file(localFilename, null, null);
            if (arr == null || arr.length != 2) {
                logger.error("向FastDFS上传文件失败");
            } else {
                for (String s : arr) {
                    System.out.println(s);
                }
                logger.info("向FastDFS上传文件成功");
            }
        } catch (IOException | MyException e) {
            logger.error("error", e);
        } finally {
            closeTrackerServer(trackerServer);
        }
    }

    /**
     * 从FastDFS下载文件
     *
     * @param localFilename  本地文件名
     * @param groupName      文件在FastDFS中的组名
     * @param remoteFilename 文件在FastDFS中的名称
     */
    public static void downloadFile(String localFilename, String groupName, String remoteFilename) {
        TrackerServer trackerServer;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            logger.error("error", e);
            return;
        }
        StorageClient storageClient = new StorageClient(trackerServer, null);
        File file = new File(localFilename);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] content = storageClient.download_file(groupName, remoteFilename);
            if (content == null || content.length == 0) {
                logger.error("文件大小为空！");
                boolean flag = file.delete();
                logger.info("删除文件结果：{}", flag);
                return;
            }
            bos.write(content);
            logger.info("成功下载文件： " + localFilename);
        } catch (IOException | MyException e) {
            logger.error("error", e);
        } finally {
            closeTrackerServer(trackerServer);
        }
    }

    /**
     * 从FastDFS删除文件
     *
     * @param groupName      文件在FastDFS中的组名
     * @param remoteFilename 文件在FastDFS中的名称
     */
    public static void deleteFile(String groupName, String remoteFilename) {
        TrackerServer trackerServer;
        try {
            trackerServer = trackerClient.getConnection();
        } catch (IOException e) {
            logger.error("error", e);
            return;
        }
        StorageClient storageClient = new StorageClient(trackerServer, null);
        try {
            int i = storageClient.delete_file(groupName, remoteFilename);
            if (i == 0) {
                logger.info("FastDFS删除文件成功");
            } else {
                logger.info("FastDFS删除文件失败");
            }
        } catch (IOException | MyException e) {
            logger.error("error", e);
        } finally {
            closeTrackerServer(trackerServer);
        }
    }

    private static void closeTrackerServer(TrackerServer trackerServer) {
        try {
            if (trackerServer != null) {
                logger.info("关闭trackerServer连接");
                trackerServer.close();
            }
        } catch (IOException e) {
            logger.error("error", e);
        }
    }
}
