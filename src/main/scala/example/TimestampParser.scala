package example

import example.Model.{FormattedTweet, TweetWithDisplayName}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object TimestampParser {
  val dateTimeFormatPattern = "yyyy-MM-dd'T'HH:mm:ss"
  val tweetsToReturn = 10

  def parseTimestamp(tweetsWithUnparsedTimestamp: List[TweetWithDisplayName]): List[TweetWithDisplayName] = {
    tweetsWithUnparsedTimestamp.map{ tweet =>
      val timestamp = new DateTime(tweet.timestamp.toLong * 1000).toLocalDateTime.toString(dateTimeFormatPattern)
      TweetWithDisplayName(tweet.tweetId, tweet.authorId, timestamp, tweet.text, tweet.displayName)
    }
  }

  private def sortDateTime(dateTime: String): DateTime =
    DateTimeFormat.forPattern(dateTimeFormatPattern).parseDateTime(dateTime)

  def getMostRecentTweets(unsortedTweets: List[TweetWithDisplayName]): List[TweetWithDisplayName] = {
    unsortedTweets.sortBy(tweet => sortDateTime(tweet.timestamp)).reverse.take(tweetsToReturn)
  }

  def formatTweets(tweets: List[TweetWithDisplayName]): List[FormattedTweet] = {
    tweets.map{ tweet =>
      FormattedTweet(tweet.displayName, tweet.timestamp, tweet.text)
    }
  }

  def cliOutput(tweet: FormattedTweet): String =  "\n" + tweet.displayName + " " + tweet.timestamp + " " + tweet.text
}
