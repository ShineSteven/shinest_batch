package shine.st.commons.amazon

import java.io.File

import com.amazonaws.services.s3.AmazonS3Client


/**
  * Created by stevenfanchiang on 2016/3/24.
  */
object S3 {
  val s3Client = new AmazonS3Client()

  def getObject(bucketName: String, objectName: String) = {
    s3Client.getObject(bucketName, objectName)
  }

  def putObject(bucketName: String, file: File) = {
    s3Client.putObject(bucketName, file.getName, file)
  }
}
