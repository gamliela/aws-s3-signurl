package com.gamliela.aws.s3.signurl;

class Params {

    final String region;
    final String bucketName;
    final String objectKey;
    final long expiryTime;

    Params(String[] args) {
        try {
            region = args[0];
            bucketName = args[1];
            objectKey = args[2];
            expiryTime = Long.parseLong(args[3]);
            if (expiryTime*1000 < System.currentTimeMillis())
                throw new Exception("expiryTime must be future Unix timestamp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void printUsage() {
        System.out.println("usage: cmd region bucketName objectKey expiryTime");
        System.out.println("       expiryTime is future Unix timestamp");
    }

}
