package example

import example.Model.{Follow, RawTweet, User}

import scala.io.Source

object CsvParser {
    def followsRead(fileName: String): List[Follow] = {
      val file = Source.fromFile(fileName)
      for {
        line <- file.getLines.toList
        values = line.split(",")
      } yield {
        Follow(values(0), values(1))
      }
    }

    def tweetsRead(fileName: String): List[RawTweet] = {
      val file = Source.fromFile(fileName)
      for {
        line <- file.getLines().toList
        values = line.split(",")
      } yield RawTweet(values(0), values(1), values(2), values(3))
    }

    def usersRead(filename: String): List[User] = {
      val file = Source.fromFile(filename)
      for {
        line <- file.getLines().toList
        values = line.split(",")
      } yield User(values(0), values(1), values(2))
    }
}
