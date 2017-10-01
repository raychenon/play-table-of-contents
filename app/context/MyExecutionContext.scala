package context

import javax.inject.Inject

import akka.actor.ActorSystem
import play.libs.concurrent.CustomExecutionContext;

class MyExecutionContext @Inject() (actorSystem: ActorSystem)
  extends CustomExecutionContext(actorSystem, "akka-dispatcher")