package com.gamliela.aws.s3.signurl;

import com.amazonaws.HttpMethod;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;

public class Cli {

    private static void setup() {
        System.setProperty(SDKGlobalConfiguration.ENABLE_S3_SIGV4_SYSTEM_PROPERTY, "true");
    }

    private static URL signUrl(Params params) {
        AmazonS3 s3client = new AmazonS3Client();
        s3client.setRegion(Region.getRegion(Regions.fromName(params.region)));

        java.util.Date expiration = new java.util.Date();
        expiration.setTime(params.expiryTime*1000);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(params.bucketName, params.objectKey);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);

        return s3client.generatePresignedUrl(generatePresignedUrlRequest);
    }

    public static void main(String[] args) {
        setup();
        Params params;
        try {
            params = new Params(args);
        } catch (Exception e) {
            System.out.println("invalid parameters");
            Params.printUsage();
            return;
        }
        System.out.print(signUrl(params));
    }

}
