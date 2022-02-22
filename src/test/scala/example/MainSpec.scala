package example

import example.DisplayName.{getDisplayName, usersFile}
import example.Follows.{followsFile, getFollows, processUserId}
import example.Model.{Follow, FormattedTweet, RawTweet, TweetWithDisplayName, User}
import example.Tweets.{getTweets, tweetsFile}
import example.TimestampParser.{formatTweets, getMostRecentTweets, parseTimestamp}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class MainSpec extends AnyFreeSpec with Matchers {
  "transform a user id parameter" - {
    "from a String to an Int" in {
      processUserId("1") shouldEqual 1
    }
    "and throw an exception if unable to transform into an Int" in {
      an[Exception] should be thrownBy processUserId("a")
    }
  }
  "get the followers of a user" - {
    val followsCsv = CsvParser.followsRead(followsFile)
    "load the followers csv file and check the size is correct" in {
      followsCsv.size shouldEqual 130393
    }
    "get the correct head of the file" in {
      followsCsv.head shouldEqual Follow("989489610", "10224712")
    }
    "retrieve the correct values for line 130392 in the followers csv file" in {
      followsCsv(130392) shouldEqual Follow("24257941", "4558")
    }
    "get the followers of a user successfully" in {
      getFollows(989489610) should contain (Follow("989489610", "10224712"))
      getFollows(989489610) should contain(Follow("989489610", "393537534"))
    }
  }
  "get the tweets of a user's followers" - {
    "load the tweets csv file successfully" in {
      val csv = CsvParser.tweetsRead(tweetsFile)
      csv.size shouldEqual 51594
      val rawTweet = RawTweet("643576332438388737","989489610","1442275529","@marcua oooo I want to hear/read the story! These are my kind of bugs!")
      csv.head shouldEqual rawTweet
    }
    "get tweets from followerId 10224712" in {
      val result = RawTweet("644844719030337536", "10224712", "1442577936", "Tech conferences... @ Skills Matter https://t.co/Rexwr0UEOz")
      getTweets(List(Follow("989489610", "10224712"))).head shouldEqual result
    }
  }
  "get the tweet author's display name" - {
    "load the users csv file successfully" in {
      val csv = CsvParser.usersRead(usersFile)
      csv.size shouldEqual 286
      val user = User("989489610","epcjones", "Evan Jones")
      csv.head shouldEqual user
    }
    "get the display name for a tweet" in {
      val tweet: List[RawTweet] = List(RawTweet("643576332438388737", "989489610","1442275529", "@marcua oooo I want to hear/read the story! These are my kind of bugs!"))
      getDisplayName(tweet) shouldEqual List((TweetWithDisplayName("643576332438388737", "989489610","1442275529", "@marcua oooo I want to hear/read the story! These are my kind of bugs!", "Evan Jones")))
    }
    "get a display name for another tweet" in {
      val anotherTweet: List[RawTweet] = List(RawTweet("642385434308444160","430543248","1441991597","@rallat Let me explain you: Nexus7 behind a two-way mirror ;)"))
      getDisplayName(anotherTweet) shouldEqual List(TweetWithDisplayName("642385434308444160","430543248","1441991597","@rallat Let me explain you: Nexus7 behind a two-way mirror ;)", "Hannah Mittelstaedt"))
    }
  }
  "parse the tweet timestamp and sort by time" - {
    "convert from unix time since epoch to human readable datetime" in {
    val tweet = List(TweetWithDisplayName("636288943219179520", "430543248", "1440538080", "@hannahmitt Yolo SoLoMo", "Hannah Mittelstaedt"))
    parseTimestamp(tweet) shouldEqual List(TweetWithDisplayName("636288943219179520", "430543248","2015-08-25T22:28:00", "@hannahmitt Yolo SoLoMo", "Hannah Mittelstaedt"))
    }
    "sort tweets by most recent" in {
      val unsortedList = List(TweetWithDisplayName("", "","2015-08-25T22:28:00", "", "" ), TweetWithDisplayName("", "","2015-07-25T22:28:00", "", "" ), TweetWithDisplayName("", "","2013-08-25T22:28:00", "", "" ), TweetWithDisplayName("", "","2015-08-25T21:28:00", "", "" ))
      val sortedList = List(TweetWithDisplayName("", "","2015-08-25T22:28:00", "", "" ), TweetWithDisplayName("", "","2015-08-25T21:28:00", "", "" ), TweetWithDisplayName("", "","2015-07-25T22:28:00", "", "" ), TweetWithDisplayName("", "","2013-08-25T22:28:00", "", "" ))
      getMostRecentTweets(unsortedList) shouldEqual sortedList

    }
  }
  "format results" - {
    "output the display name, human readable timestamp and tweet text" in {
      val tweets = List(TweetWithDisplayName("", "","2015-08-25T22:28:00", "Hello", "Amina" ), TweetWithDisplayName("", "","2015-08-25T21:28:00", "Goodbye", "Adam" ), TweetWithDisplayName("", "","2015-07-25T22:28:00", "Hi", "Adewusi" ), TweetWithDisplayName("", "","2013-08-25T22:28:00", "You're winning", "Fisher" ))
      val formattedTweets = List(FormattedTweet("Amina", "2015-08-25T22:28:00", "Hello"), FormattedTweet("Adam", "2015-08-25T21:28:00","Goodbye"), FormattedTweet("Adewusi", "2015-07-25T22:28:00", "Hi"), FormattedTweet("Fisher", "2013-08-25T22:28:00", "You're winning"))
      formatTweets(tweets) shouldEqual formattedTweets
    }
  }
}
