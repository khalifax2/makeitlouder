package com.makeitlouder.bucket;

public enum BucketName {
    PROFILE_IMAGE("mybucket-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
