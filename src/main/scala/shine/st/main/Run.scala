package shine.st.main


import java.io.{File, FileInputStream}

import org.apache.commons.io.FileUtils
import org.joda.time.DateTime
import shine.st.commons.IOUtils
import shine.st.commons.amazon.S3

/**
  * Created by stevenfanchiang on 2016/3/24.
  */
object Run extends App {
println(System.getProperty("os.name"))

  //val contentObject = S3.getBucketObject("document/programmer/DB.md")
  //val contentObject = S3.getBucketObject("markdown_help_from_macdown.md")

  //  val meta = contentObject.getObjectMetadata
  //  val input = contentObject.getObjectContent
  //println(meta.getLastModified)

 val file = new File("""D:\tmp\aws2\nzip\document_uat""")
//  file.getParentFile.mkdirs()
FileUtils.deleteDirectory(file)


  IOUtils.zip("""D:\tmp\aws2\document_uat""","""D:\tmp\aws2\document_uat.zip""")
  println("zip finish")
  IOUtils.unzip("""D:\tmp\aws2\document_uat.zip""","""D:\tmp\aws2\\nzip""")

//  IOUtils.unzip("""D:\tmp\aws2\document.zip""","""D:\tmp\aws2\\nzip""")


  //    println(file.toString.substring("""D:\tmp\aws\document""".length() + 1,file.toString.length()))

  //  IOUtils.inputStreamToFile(input, new File("""D:\tmp\aws\document.md"""))
  //  println(new DateTime(file.lastModified()))

  //  IOUtils.unzip("""D:\tmp\aws\document.zip""","""D:\tmp\aws""")
  //  IOUtils.zip("""D:\tmp\aws\zip_test\document""","""D:\tmp\aws\zip_test\document.zip""")

  //  var byteArr = new Array[Byte](file.length().toInt)
  //  val inputStream = new FileInputStream(file)
  //  inputStream.read(byteArr)
  //  byteArr.foreach { x => print(s"${new String(Array(x),"UTF-8")}") }


  //  println
  //  println(getBits(45.toByte))
  //  check
  def check = {
    val ama = new File("""D:\tmp\aws\programmer\DB.md""")
    val local = new File("""D:\ShineSt\technology\document\programmer\DB.md""")

    println(new DateTime((ama.lastModified)))
    println(new DateTime((local.lastModified())))
  }

  def getBits(b: Byte) = {
    var result: String = ""
    val r = for (i <- 0 until 8) yield {
      val t = if ((b & (1 << i)) == 0)
        "0"
      else
        "1"
      //      println(t)
      t
    }
    r.reverse.mkString
  }
}
