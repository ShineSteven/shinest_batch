package shine.st.main

import java.io.File

import com.typesafe.config.ConfigFactory
import org.apache.commons.io.FileUtils
import shine.st.common.IOUtils
import shine.st.common.aws.S3
import shine.st.commons.OSValidator

/**
  * Created by stevenfanchiang on 2016/3/25.
  */
object SyncDoucment {
  val config = ConfigFactory.load
  val syncBucket = "shinest-synchronize"

  def main(args: Array[String]) {
    if (args.length > 0) {
      val path = config.getString(s"sync.document.${OSValidator.whatOS.toString}.path")
      val dirName = config.getString("sync.document.dir_name")
      val absolutePath = s"$path${File.separator}$dirName"
      val zipFileName = absolutePath + ".zip"

      args(0) match {
        case "upload" =>
          val zipFile = IOUtils.zip(absolutePath, zipFileName)
          S3.putObject(syncBucket, zipFile)

        case "download" =>
          val docObjectContent = S3.getObjectContent(syncBucket, dirName + ".zip")
          IOUtils.inputStreamToFile(docObjectContent, zipFileName)
          FileUtils.deleteDirectory(new File(absolutePath))
          IOUtils.unzip(zipFileName, path)

        case _ =>
      }

    } else {
      println("need parameter")
    }
  }
}

