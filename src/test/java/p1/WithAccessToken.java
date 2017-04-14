package p1;

import java.util.GregorianCalendar;

import twitter4j.*;
import twitter4j.api.TrendsResources;
import twitter4j.auth.*;

public class WithAccessToken {

	public static void main(String args[]) throws Exception {
		// The factory instance is re-useable and thread safe.
		TwitterFactory factory = new TwitterFactory();
		AccessToken accessToken = loadAccessToken(
				Integer.parseInt("0"/* args[0] */));
		Twitter twitter = factory.getInstance();
		// twitter.setOAuthConsumerKey("zCr0zxWPdcDd8OKXnq65JWV3s",
		// "iLoUAifY10Kbe22iLeV805mELn6v5biQuYWPHGa5QESo1qGUNl");
		twitter.setOAuthConsumer("zCr0zxWPdcDd8OKXnq65JWV3s", "iLoUAifY10Kbe22iLeV805mELn6v5biQuYWPHGa5QESo1qGUNl");
		twitter.setOAuthAccessToken(accessToken);

		GregorianCalendar d1 = new GregorianCalendar();
		System.out.println(d1.getTimeInMillis());
		Status status = twitter.updateStatus("paperino1" + d1.getTimeInMillis());
		// Status status = twitter.updateStatus(args[1]);
		System.out.println("Successfully updated the status to [" + status.getText() + "].");

		Query q1 = new Query("#chilhavisto");
		q1.count(200);
		q1.since("2017-01-01");
		QueryResult qr1 = twitter.search(q1);
		System.out.println("Result count " + qr1.getCount());
		int i = 0;
		for (Status status1 : qr1.getTweets()) {
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println("Tweet no. " + i++);
			System.out.println("Tweet ID. " + status1.getId());
			System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
			System.out.println(status1.getCreatedAt());
			System.out.println(status1.getText());
		}
		System.out.println("twitter.getId() " + twitter.getId());

		for (Trend tr : twitter.trends().getPlaceTrends(23424853).getTrends())
			System.out.println("twitter trends " + tr.getName());

		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
		IDs idlist1 = twitter.getFollowersIDs(-1);
		for (long oneID : idlist1.getIDs()) {
			System.out.println(twitter.lookupUsers(oneID).get(0).getName());
		}
		idlist1 = twitter.getFriendsIDs(-1);
		for (long oneID : idlist1.getIDs()) {
			System.out.println(twitter.lookupUsers(oneID).get(0).getName());
		}

		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				System.out.println(status.getUser().getName() + " : " + status.getText());
				System.out.println("/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*");
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}
		};
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer("zCr0zxWPdcDd8OKXnq65JWV3s",
				"iLoUAifY10Kbe22iLeV805mELn6v5biQuYWPHGa5QESo1qGUNl");
		twitterStream.setOAuthAccessToken(accessToken);
		twitterStream.addListener(listener);
		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitterStream.sample();
		twitterStream.filter("#chilhavisto");

		Thread.sleep(60000);

		System.exit(0);
	}

	private static AccessToken loadAccessToken(int useId) {
		String token = "212847997-6njkH0uIXyEVSzxxSahX0VugG2yVh7jQuGvc0OQ8";
		String tokenSecret = "gGJBXeas01AaryA1oEr9EXFpi9BE8MWwAsKE7EvgSScDV";
		return new AccessToken(token, tokenSecret);
	}

}
