package com.samstarling.futures

import com.twitter.util.Future

object BasicsAndComposition extends App {

  type Urn = String
  type Url = String
  case class Track(urn: Urn, user: Urn, title: String)
  case class TrackWithAvatar(track: Track, avatar: Url)

  def getTrack(urn: Urn): Future[Track] =
    Future.value(Track("urn:tracks:123", "urn:users:123", "Foo Bar Baz"))

  def getAvatar(userUrn: Urn): Future[Url] =
    Future.value(s"https://artwork.com/users/$userUrn")

  /* Futures can be mapped */
  def getTrackUserId(trackUrn: Urn): Future[Urn] =
    getTrack(trackUrn).map(_.urn)

  /* Futures can be sequenced using flatMap */
  def userAvatarForTrack(trackUrn: Urn): Future[Url] =
    getTrack(trackUrn).flatMap(t => getAvatar(t.user))

  /* This can also be expressed as a for comprehension */
  def userAvatarForTrack2(trackUrn: Urn): Future[Url] = {
    for {
      track <- getTrack(trackUrn)
      avatar <- getAvatar(track.user)
    } yield avatar
  }

  /* But a for comprehension can give us access to intermediate objects */
  def trackWithAvatar(trackUrn: Urn): Future[TrackWithAvatar] = {
    for {
      track <- getTrack(trackUrn)
      avatar <- getAvatar(track.user)
    } yield TrackWithAvatar(track, avatar)
  }
}
