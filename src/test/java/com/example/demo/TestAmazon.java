package com.example.demo;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by pc on 2018/12/18 10:35
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAmazon {
    static AmazonS3 s3;
    static String  AWS_ACCESS_KEY = "AKIAJ6ZFSN3KS22ZCA6Q"; // 【你的 access_key】
    static String AWS_SECRET_KEY = "02m1eEBT2GIQ1pnRpFXHNt1umESuTFRwee2ysOTU"; // 【你的 aws_secret_key】

    static String bucketName = "hzzbucket"; // 【你 bucket 的名字】 # 首先需要保证 s3 上已经存在该存储桶

    static {
        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        //设置S3的地区
        builder.setRegion(Regions.US_EAST_1.getName());
        s3=builder.build();
    }

    public  static String uploadToS3(File tempFile, String remoteFileName) throws IOException {
        try {
            String bucketPath = bucketName + "/upload" ;
            /*s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, tempFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));*/
            s3.putObject("hzzbucket", remoteFileName, tempFile);
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            URL url = s3.generatePresignedUrl(urlRequest);
            return url.toString();
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
        } catch (AmazonClientException ace) {
            ace.printStackTrace();
        }
        return null;
    }

    @Test
    public void test() throws IOException {
        File uploadFile = new File("C:\\Users\\pc\\Desktop\\txt\\aa.txt");
        String uploadKey = "test";
        String url=uploadToS3(uploadFile,uploadKey);
        Assert.assertNotNull(url);
    }

    public static Bucket createBucket(String bucket_name) {
        Bucket b = null;
        if (s3.doesBucketExist(bucket_name)) {
            System.out.format("Bucket %s already exists.\n", bucket_name);
            b = getBucket(bucket_name);
        } else {
            try {
                b = s3.createBucket(bucket_name);
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
            }
        }
        return b;
    }

    public static Bucket getBucket(String bucket_name) {
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public static void main(String[] args) throws IOException {
        /*Bucket name=getBucket("hzzbucket");
        System.out.println(name);*/

        File uploadFile = new File("C:\\Users\\pc\\Desktop\\文档\\微信营销系统登录接口文档.docx");
        String uploadKey = "文档/微信营销系统登录接口文档.docx";
        String url=uploadToS3(uploadFile,uploadKey);
        Assert.assertNotNull(url);
    }

}
