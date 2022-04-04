package part1recap

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Try,Success,Failure}

object ScalaRecap {
  val aBoolean: Boolean = false

  //expressiohns
  val anIfExpression: String = if (2>3) "bigger" else "smaller"

  // instructions vs expressions

  /*
  instructions are executed (imperative), expressions are evaluated (building block for fp)
   */

  val theUnit = println("hello") //looks like an instruction but in scala this is still an expression




  //Options
  val anOption:Option[Int] = Option(43)
  val doubleOptopn = anOption.map(_*2)
  val optionDescription = anOption match {
    case Some(value) => s"option is not empty"
  }


  // Futures
  // Will finish at some point, uses an execution context which is basically a threadpool

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(8))
  val future = Future(1 + 999)

  // register callback when it finishes
  future.onComplete(t => t match{
    case Success(value) => println(s"value is $value")
    case Failure(value) => println(s"reason of failure is $value")
  })

  val aPartialFunction: PartialFunction[Try[Int], Unit] = {
    case Success(value) => println(s"value is $value")
    case Failure(value) => println(s"reason of failure is $value")
  }

  //map, flatMap, filter
  val doubledAsyncMOL: Future[Int] = future.map(_*2)

  //implicits
  //1 - to pass implicit arguments and values (some arguments that are not in control and don't want to think about)

  implicit val timeout: Int = 3000
  def setTimeout(f: () => Unit)(implicit tout: Int) = {// implicit val == given instance
    Thread.sleep(tout) //(using tout: Int)
    f()
  }

  setTimeout(() => println("timeout")) //compiler automatically injects tout because its marked as implicit

  //2 extension methods, methods that dont belong to a particular type but want to add them later for convenience
  implicit class MyRichInt(number: Int){
   def isEven: Boolean = number %2 == 0
  }

  val is2Even = 2.isEven //new RichInt(2).isEven, happens if its in scope


  case class Person(name: String, age: Int)
  //3 conversions - discouraged anyway
  implicit def string2Person(name: String): Person =
    new Person(name, 57)


  val daniel: Person = "Daniel" //string2Person("Daniel")

  //OOP
  class Animal
  class Cat extends Animal
  trait Carnivore {
    def eat(animal: Animal): Unit
  }


}
