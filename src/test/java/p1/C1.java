package p1;

import java.io.*;
import java.util.GregorianCalendar;

import twitter4j.*;
import twitter4j.api.TweetsResources;
import twitter4j.auth.*;

public class C1 {

	public static void main(String args[]) throws Exception {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("zCr0zxWPdcDd8OKXnq65JWV3s", "iLoUAifY10Kbe22iLeV805mELn6v5biQuYWPHGa5QESo1qGUNl");
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			System.out.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try {
				if (pin.length() > 0) {
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				} else {
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}
		// persist to the accessToken for future reference.
		storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
		GregorianCalendar d1 = new GregorianCalendar();
		System.out.println(d1.getTimeInMillis());
		Status status = twitter.updateStatus("paperino1"+ d1.getTimeInMillis());
		System.out.println("Successfully updated the status to [" + status.getText() + "].");
		IDs IDs1 = twitter.getBlocksIDs();
		for (long l : IDs1.getIDs()) {
			System.out.println(l);
		}
		TweetsResources twr = twitter.tweets();
		
		System.exit(0);
	}

	private static void storeAccessToken(long useId, AccessToken accessToken) {
		System.out.println(accessToken.getToken());
		System.out.println(accessToken.getTokenSecret());
	}

}
