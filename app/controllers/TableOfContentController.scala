package controllers

import java.net.URL
import javax.inject.Inject

import models.ReadmeForm
import play.api.Logger
import play.api.data.Form
import play.api.mvc._
import play.api.data.Forms._
import readme.TableOfContentHelper
import util.HtmlUtil

import scala.concurrent.{ExecutionContext, Future}

class TableOfContentController @Inject()(cc: ControllerComponents) extends AbstractController(cc){

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

  def redirectContentTable = Action.async { implicit request =>
    val form: ReadmeForm = userForm.bindFromRequest.get
    readGithubLink(form.githubUrl).map(contentFromGithub => {
      val description = if (contentFromGithub.isEmpty) form.content else contentFromGithub
      Ok(HtmlUtil.prettify(views.html.readme(description, form.githubUrl, TableOfContentHelper.convert(description).fullText)))
    })
  }

  /**
    * the url must be in the form https://github.com/{username}/{project}
    * if the text starts with a github link, return the content of README.md file
    *
    * @param url
    * @return either the input or the content of github's README
    */
  def readGithubLink(url: String): Future[String] = {
    if (url.startsWith("https://github.com")) {
      readContentFromUrl(getGithubReadmeUrl(url))
    }else{
      Future("")
    }
  }

  /**
    *
    * @param url ex : https://github.com/raychenon/play-table-of-contents
    * @return  https://raw.githubusercontent.com/raychenon/play-table-of-contents/master/README.md
    */
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
