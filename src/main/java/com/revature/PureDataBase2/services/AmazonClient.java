package com.revature.PureDataBase2.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import jakarta.annotation.PostConstruct;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmazonClient {
    Logger logger = LoggerFactory.getLogger(AmazonClient.class);
    private AmazonS3 s3client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @PostConstruct
    private void initializeAmazon() {
        try {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        Regions regions = Regions.US_WEST_1;
       this.s3client = AmazonS3ClientBuilder.standard().withCredentials(
        new AWSStaticCredentialsProvider(credentials)).withRegion(regions).build();
        } catch (IllegalArgumentException e) {
            logger.error("Couldn't make amazon client: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Couldn't make amazon client: " + e.getMessage(), e);
        }
    }

    public void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }
}
