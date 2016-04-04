package shine.st.commons

import java.io._
import java.util.zip.{ZipEntry, ZipInputStream, ZipOutputStream}

/**
  * Created by stevenfanchiang on 2016/3/25.
  */
object IOUtils {
  def inputStreamToFile(inputStream: java.io.InputStream, fileName: String) = {
    val file = new File(fileName)
    val fos = new java.io.FileOutputStream(file)
    fos.write(
      Stream.continually(inputStream.read).takeWhile(-1 !=).map(_.toByte).toArray
    )
    fos.close()

    file
  }

  def recursiveMkdir(file: File): Boolean = {
    if (!file.getParentFile.exists()) {
      recursiveMkdir(file.getParentFile)
      file.getParentFile.mkdir()
    } else
      true
  }

  def unzip(zipFileName: String, outputFolder: String) = {
    val buffer = new Array[Byte](1024)
    lazy val zis: ZipInputStream = new ZipInputStream(new FileInputStream(zipFileName))
    var ze: ZipEntry = zis.getNextEntry()

    try {
      val folder = new File(outputFolder)
      if (!folder.exists()) {
        folder.mkdir()
      }

      while (ze != null) {
        val fileName = ze.getName()

        val unzipFile = new File(s"$outputFolder${File.separator}$fileName")
        if (!unzipFile.getParentFile.exists())
          unzipFile.getParentFile.mkdirs()

        if (!ze.isDirectory) {
          val fos = new FileOutputStream(unzipFile)

          var len: Int = zis.read(buffer)
          while (len > 0) {
            fos.write(buffer, 0, len)
            len = zis.read(buffer)
          }

          fos.close()
        }
        ze = zis.getNextEntry()
      }
    }
    finally {
      zis.closeEntry()
      zis.close()
    }
  }


  def zip(fileName: String, zipOutput: String) = {
    val source = new File(fileName)
    val relativelyPath = source.getAbsolutePath.substring(0, source.getAbsolutePath.indexOf(source.getName))
    println(relativelyPath)

    def generateFileList(node: File, fileNameList: List[String]): List[String] = {
      if (node.isFile)
        node.getAbsolutePath.substring(relativelyPath.length) :: fileNameList
      else {
        node.list().map { fileName => new File(node, fileName) }.flatMap { file => generateFileList(file, fileNameList) }.toList
      }
    }

    val fileList = generateFileList(source, Nil)

    val zipFile = new File(zipOutput)
    lazy val zos = new ZipOutputStream(new FileOutputStream(zipFile))

    try {
      val buffer = new Array[Byte](1024)

      fileList.foreach { fileName =>
        val in = new FileInputStream(relativelyPath + File.separator + fileName)

        zos.putNextEntry(new ZipEntry(fileName))
        zos.write(
          Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
        )
      }

      //            val buffer = new Array[Byte](1024)
      //
      //            fileList.foreach { fileName =>
      //              val in = new FileInputStream(relativelyPath + File.separator + fileName)
      //              zos.putNextEntry(new ZipEntry(fileName))
      //              var len = in.read(buffer)
      //              while (len > 0) {
      //                zos.write(buffer, 0, len)
      //                len = in.read(buffer)
      //              }
      //            }
      zos.closeEntry()
    } finally {
      zos.close()
    }

    zipFile
  }
}
