package https

import javax.net.ssl._

import play.core.ApplicationProvider
import play.server.api._
import play.core.server._

// https://github.com/playframework/play-scala-tls-example/blob/292fd3f5c1c164841860aa33d2f23d9c3a104c5b/app/https/CustomSSLEngineProvider.scala
class CustomSSLEngineProvider( serverConfig: ServerConfig,appProvider: ApplicationProvider) extends SSLEngineProvider {

  override def createSSLEngine(): SSLEngine = {
    SSLContext.getDefault.createSSLEngine
  }

}