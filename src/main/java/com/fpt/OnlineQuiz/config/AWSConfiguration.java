package com.fpt.OnlineQuiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import lombok.Data;

@Data
@Configuration
public class AWSConfiguration {


    @Value("${app.aws.access_key}")
    private String accessKey;

    @Value("${app.aws.secret_key}")
    private String secretKey;

    @Value("${app.aws.sns.regions}")
    private Regions regions;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(this.accessKey, this.secretKey);
    }

    @Bean
    public AmazonSNS amazonSNSClient() {
        return AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
                .withRegion(regions)
                .build();
    }

    @Bean
    public AmazonS3 amazonS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
                .withRegion(regions)
                .build();
    }
}

