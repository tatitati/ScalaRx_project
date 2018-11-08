import rx.lang.scala._
import scala.concurrent.duration._


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


	val classics = List("Il buono, il brutto, il cattivo.", "Back to the future", "Die Hard")
	val o = Observable.from(classics)

	o.subscribe(new Observer[String] {
		override def onNext(m: String) = println(s"Movies Watchlist - $m")
		override def onError(e: Throwable) = println(s"Ooops - $e!")
		override def onCompleted() = println(s"No more movies.")
	})
}

//object ObservablesLifetime extends App {
//	val classics = List("Il buono, il brutto, il cattivo.", "Back to the future", "Die Hard")
//	val o = Observable.from(classics)
//
//	o.subscribe(new Observer[String] {
//		override def onNext(m: String) = println(s"Movies Watchlist - $m")
//		override def onError(e: Throwable) = println(s"Ooops - $e!")
//		override def onCompleted() = println(s"No more movies.")
//	})
//}