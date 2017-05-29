package controllers

import java.net.URL

import play.api.Logger
import play.api.data.Form
import play.api.mvc.{Action, Controller}
import play.api.data.Forms._
import readme.{ReadmeForm, TableOfContentHelper}

class TableOfContentController  extends Controller{

  val logger = Logger(this.getClass)

  val userForm = Form(
    mapping(
      "description" -> nonEmptyText
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
    val desc = readGithubLink(form.description)
    Ok(views.html.readme(desc, TableOfContentHelper.convert(desc)))
  }

  /**
    * the url must be in the form https://github.com/{username}/{project}
    * if the text starts with a github link, return the content of README.md file
    * @param description
    * @return either the input or the content of github's README
    */
  def readGithubLink(description: String): String = {
    if (description.startsWith("https://github.com")){
      val githubUrl = new URL(description)
      val path = githubUrl.getPath
      val readmeUrl = new URL("https://raw.githubusercontent.com" + path + "/master/README.md")
      // @todo must be asynchronous
      val result = scala.io.Source.fromURL(readmeUrl).mkString
      result
    }else{
      description
    }
  }

}
