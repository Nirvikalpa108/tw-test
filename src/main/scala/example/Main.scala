package example

import example.DisplayName.getDisplayName
import example.Follows.{getFollows, processUserId}
import example.TimestampParser.{cliOutput, formatTweets, getMostRecentTweets, parseTimestamp}
import example.Tweets._

import scala.util.control.NonFatal

//This is looking great!
//TODO fix code: Should be able to run in CLI with $ run 989489610
//TODO: check that the timestamps are ordered correctly on output
//(maybe ordering by date, but not time?)
//The "text" field can contain commas, so you should limit the number of columns you parse from this file to include them correctly
//Tweets can contain "special" characters. so handle the text appropriately
//find a memory efficient solution to loading tweets and taking top 10.
// add README

object Main {
  def main(args: Array[String]): Unit = {
    try {
      val input = args.head
      val userId = processUserId(input)
      val follows = getFollows(userId)
      val tweetsFromFollows = getTweets(follows)
      val tweetsFromFollowsWithDisplayName = getDisplayName(tweetsFromFollows)
      val tweetsWithParsedTimestamp = parseTimestamp(tweetsFromFollowsWithDisplayName)
      val mostRecentTweets = getMostRecentTweets(tweetsWithParsedTimestamp)
      val formattedTweets = formatTweets(mostRecentTweets)
      println(s"${formattedTweets.map(cliOutput)}")
    } catch {
      case NonFatal(error) =>
        println(error.getMessage)
    }
  }
}


