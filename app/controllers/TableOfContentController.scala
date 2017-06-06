package controllers

import java.net.URL

import play.api.Logger
import play.api.data.Form
import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import readme.{ReadmeForm, TableOfContentHelper}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class TableOfContentController  extends Controller{

  val logger = Logger(this.getClass)

  val userForm = Form(
    mapping(
      "description" -> nonEmptyText,
      "github_url" -> nonEmptyText
    )(ReadmeForm.apply)(ReadmeForm.unapply)
  )

  val startContent: String =
    """Example :
# Title 1
## Title 2
### Title 3"""

  def readme = Action {
    Ok(views.html.readme(startContent))
  }

  def redirectContentTable = Action { implicit request =>
    val form: ReadmeForm = userForm.bindFromRequest.get
    val contentFromGithub = readGithubLink(form.githubUrl)
    val description = if (contentFromGithub.isEmpty) form.content else contentFromGithub
    Ok(views.html.readme(description,form.githubUrl, TableOfContentHelper.convert(description)))
  }

  /**
    * the url must be in the form https://github.com/{username}/{project}
    * if the text starts with a github link, return the content of README.md file
    *
    * @param url
    * @return either the input or the content of github's README
    */
  def readGithubLink(url: String): String = {
    if (url.startsWith("https://github.com")) {
      // @todo blocking, but should be asynchronous
      val maybeContent = getUrlContent(getGithubReadmeUrl(url))
      Await.result(maybeContent, 2.second)
    }else{
      ""
    }
  }

  def getGithubReadmeUrl(url: String): String = {
    val githubUrl = new URL(url)
    val path = githubUrl.getPath
    "https://raw.githubusercontent.com" + path + "/master/README.md"
  }

  def getUrlContent(readmeUrl: String): Future[String] = Future {
    val f = scala.io.Source.fromURL(readmeUrl)
    try f.mkString finally f.close()
  }

}
