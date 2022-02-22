package example

object Model {

  case class Follow(sourceUserId: String, destinationUserId: String)

  case class RawTweet(tweetId: String, authorId: String, timestamp: String, text: String)

  case class TweetWithDisplayName(tweetId: String, authorId: String, timestamp: String, text: String, displayName: String)

  case class User(userId: String, twitterScreenName: String, displayName: String)

  case class FormattedTweet(displayName: String, timestamp: String, text: String)
}
