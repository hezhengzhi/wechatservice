package com.example.demo;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class Test {
    public static void main(String[] args) throws IOException {

        //创建Amazon S3对象使用明确凭证
        BasicAWSCredentials credentials = new BasicAWSCredentials("your accesskey", "your secretkey");
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("S3SignerType");//凭证验证方式
        clientConfig.setProtocol(Protocol.HTTP);//访问协议
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(clientConfig)
.withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(//设置要用于请求的端点配置（服务端点和签名区域）
                                "s3.xxx.sn",//我的s3服务器
                                "cn-north-1")).withPathStyleAccessEnabled(true)//是否使用路径方式，是的话s3.xxx.sn/bucketname

                .build();

        System.out.println("Uploading a new object to S3 from a file\n");

        //枚举bucket
        List<Bucket> buckets = s3Client.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println("Bucket: " + bucket.getName());
        }
        //枚举bucket下对象
        ObjectListing objects = s3Client.listObjects("sinosoft-ocr-bucket");
        do {
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                System.out.println("Object: " + objectSummary.getKey());
            }
            objects = s3Client.listNextBatchOfObjects(objects);
        } while (objects.isTruncated());


        //文件上传
        try {
            s3Client.putObject("bucketname", "keyname", new File("your file path"));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }

        //文件下载
        try {
            S3Object o = s3Client.getObject("bucketname", "your file's keyname");
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(new File("your save file path"));
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }




    }
}
