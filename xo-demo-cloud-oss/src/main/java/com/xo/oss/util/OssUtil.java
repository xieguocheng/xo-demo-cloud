package com.xo.oss.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: xo
 * @DATE: 2020/10/15
 * @Description: BbtOssUtil
 **/
public class OssUtil {

    private OSSClient ossClient;

    private String bucketName;

    public OssUtil(OSSClient ossClient, String bucketName) {
        this.ossClient = ossClient;
        this.bucketName = bucketName;
    }

    public boolean doesFileExist(String path) {
        return ossClient.doesObjectExist(path, path);
    }

    public List<String> listFiles( String path) {
        List<String> listFiles = new ArrayList<>();
        String marker = null;
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
            listObjectsRequest.setBucketName(bucketName);
            listObjectsRequest.setPrefix(path);
            listObjectsRequest.setMarker(marker);
            ObjectListing listObjects = ossClient.listObjects(listObjectsRequest);
            marker = listObjects.getNextMarker();
            List<OSSObjectSummary> objectSummaries = listObjects.getObjectSummaries();
            if (objectSummaries != null && !objectSummaries.isEmpty()) {
                listFiles.addAll(objectSummaries.stream().map(OSSObjectSummary::getKey).collect(Collectors.toList()));
            }
        } while (marker != null);
        return listFiles;
    }

    public PutObjectResult writInputStream(String path, InputStream inputStream) {
        PutObjectResult putObject = ossClient.putObject(bucketName, path, inputStream);
        return putObject;
    }

    public PutObjectResult writInputStream(String path, InputStream inputStream, ObjectMetadata metadata) {
        return ossClient.putObject(bucketName, path, inputStream,metadata);
    }

    public PutObjectResult writFile(String path, File file) {
        PutObjectResult putObject = ossClient.putObject(bucketName, path, file);
        return putObject;
    }

    public void deleteFile(String path) {
        ossClient.deleteObject(bucketName, path);
    }

    public InputStream downloadToInputStream(final String filePath) {
        final OSSObject ossObject = ossClient.getObject(bucketName, filePath);
        InputStream in = ossObject.getObjectContent();
        return in;
    }

}

