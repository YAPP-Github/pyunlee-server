package com.yapp.cvs.infrastructure.s3.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL


@Component
class S3Service(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,

    private val amazonS3Client: AmazonS3Client,
) {
    fun copyAndUploadIfExist(fileUrl: String, fileName: String): String? {
        return try {
            copyAndUpload(fileUrl, fileName)
        }catch (e: Exception){
            log.info("해당 파일이 없습니다 url: $fileUrl error: ${e.message}")
            null
        }
    }
    fun copyAndUpload(fileUrl: String, fileName: String): String {
        val extension = fileUrl.split("/").last()
            .let { it.substring(it.indexOf('.')) }

        val url = URL(fileUrl)
        val inputStream: InputStream = BufferedInputStream(url.openStream())

        amazonS3Client.putObject(PutObjectRequest(bucket, fileName + extension, inputStream, null)
            .withCannedAcl(CannedAccessControlList.PublicRead))
        return amazonS3Client.getUrl(bucket, fileName).toString()
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}