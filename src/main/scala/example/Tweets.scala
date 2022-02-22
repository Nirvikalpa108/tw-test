package example

import example.Model.{Follow, RawTweet}

object Tweets {
  val tweetsFile: String = "src/main/resources/data/tweets.csv"

  //get tweets of those who are followed
  def getTweets(follows: List[Follow]): List[RawTweet] = {
    val tweetsFromFollowers = CsvParser.tweetsRead(tweetsFile)
    for {
      follow <- follows
      result <- tweetsFromFollowers.filter(_.authorId == follow.destinationUserId)
    } yield result
  }
}
