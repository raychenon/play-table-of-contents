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
      "description" -> nonEmptyText
    )(ReadmeForm.apply)(ReadmeForm.unapply)
  )

  val githubForm = Form(
    mapping(
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

  def readFromGithub = Action { implicit request =>
    val form: ReadmeForm = githubForm.bindFromRequest.get
    val desc = readGithubLink(form.content)
    Ok(views.html.readme(desc, form.content,TableOfContentHelper.convert(desc)))
  }

  def redirectContentTable = Action { implicit request =>
    val form: ReadmeForm = userForm.bindFromRequest.get
    Ok(views.html.readme(form.content,"", TableOfContentHelper.convert(form.content)))
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
