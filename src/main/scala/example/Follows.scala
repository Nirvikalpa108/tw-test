package example

import example.Model.Follow

object Follows {
  val followsFile: String = "src/main/resources/data/follows.csv"
  // get an Int from CLI String input
  def processUserId(userId: String): Int = userId.toInt

  //who does the user id follow?
  def getFollows(userId: Int): List[Follow] = {
    val userAndTheirFollowers = CsvParser.followsRead(followsFile)
    for {
      followsForAGivenUserId <- userAndTheirFollowers.filter(_.sourceUserId == userId.toString)
    } yield followsForAGivenUserId
  }
}
