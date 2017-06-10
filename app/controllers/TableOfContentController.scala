package controllers

import java.net.URL

import play.api.Logger
import play.api.data.Form
import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import readme.{ReadmeForm, TableOfContentHelper}
import util.HtmlUtil

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

class TableOfContentController  extends Controller{

  val logger = Logger(this.getClass)

  val userForm = Form(
    mapping(
      "description" -> text,
      "github_url" -> text
    )(ReadmeForm.apply)(ReadmeForm.unapply)
  )

  val startContent: String =
    """Example :
# Title 1
## Title 2
### Title 3"""

  def readme = Action {
    Ok(HtmlUtil.prettify(views.html.readme(startContent)))
  }

  def redirectContentTable = Action { implicit request =>
    val form: ReadmeForm = userForm.bindFromRequest.get
    val contentFromGithub = readGithubLink(form.githubUrl)
    val description = if (contentFromGithub.isEmpty) form.content else contentFromGithub
    Ok(HtmlUtil.prettify(views.html.readme(description,form.githubUrl, TableOfContentHelper.convert(description))))
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
      val maybeContent = readContentFromUrl(getGithubReadmeUrl(url))
      Await.result(maybeContent, 5.second)
    }else{
      ""
    }
  }

  def getGithubReadmeUrl(url: String): String = {
    val githubUrl = new URL(url)
    val path = githubUrl.getPath.substring(1)
    val endIndex = path.indexOf("/",path.indexOf("/") + 1)
    val userNproject = if(endIndex == -1) path else path.substring(0,endIndex)
    s"https://raw.githubusercontent.com/${userNproject}/master/README.md"
  }

  def readContentFromUrl(mdUrl: String): Future[String] = Future {
    val f = scala.io.Source.fromURL(mdUrl)
    try f.mkString finally f.close()
  }

}
