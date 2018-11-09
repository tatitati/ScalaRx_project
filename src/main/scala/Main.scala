
import scala.concurrent.duration._
import rx.lang.scala._
import scala.concurrent.Future
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global


object Main extends App {
	val ob1 = Observable.items("Pascal", "Java", "Scala")

	ob1.subscribe(name => println("ONE: "  + name))
	ob1.subscribe(name => println("TWO: "  + name))

	// output
	//	ONE: Pascal
	//	ONE: Java
	//	ONE: Scala
	//	TWO: Pascal
	//	TWO: Java
	//	TWO: Scala

	val ob2 = Observable.timer(2.second)
	ob2.subscribe(_ => println("ONE TIMEOUT"))
	ob2.subscribe(_ => println("TWO TIMEOUT"))

	println("SLEEPING....")
	Thread.sleep(10000)
	println("FINISH")

	// output
	//  SLEEPING....
	//  TWO TIMEOUT
	//  ONE TIMEOUT
	//  FINISH
}

object ReactiveBbc extends App {

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