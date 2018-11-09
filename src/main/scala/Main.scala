
import scala.concurrent.duration._
import rx.lang.scala._
import scala.concurrent.Future
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global


object Reactive1 extends App {
	val ob1 = Observable.items("Pascal", "Java", "Scala")
	ob1.subscribe(name => println("Another event: "  + name))

	// output
	//	Another event: Pascal
	//	Another event: Java
	//	Another event: Scala
}

object Reactive2 extends App {
	val ob2 = Observable.timer(5.second)
	ob2.subscribe(_ => println("ONE TIMEOUT"))

	println("SLEEPING....")
	Thread.sleep(20000)
	println("FINISH")

	// output
	//  SLEEPING....
	//  <after 5 seconds....>
	//  ONE TIMEOUT
	//  FINISH
}

object Reactive3 extends App {

	val news = Observable[String] { // observable are the elements producing stream of events
		observer => Future { // observers are the elements listening those events, they consume them
			while (true) {
				val htmlResponse = Source.fromURL("http://feeds.bbci.co.uk/news/rss.xml").mkString
				observer.onNext(htmlResponse)
				println("sleeping1...")
				Thread.sleep(5000)
			}
		}
		Subscription()
	}

	news.subscribe(print(_))
	Thread.sleep(60000) // I want to run the app for a long time (1 hour), and during this time I will fetch every 5 seconds news from the url. IS NOT FUNNY
}