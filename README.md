#Authenticating

The authentication process uses [http://oauth.net OAuth].
You will want to do the authentication process once for each user and then save off their token and secret.


  String CONSUMER_KEY = "<your app key>";
  String CONSUMER_SECRET = "<your app secret>";

  RequestToken token = DailyMileAuthenticator.obtainRequestToken(CONSUMER_KEY,
			  CONSUMER_SECRET, "<call back url>");

  // token.getAuthorizeUrl() will contain the url that users can goto to authenticate

  String veryifcationToken = "";

  // set veryifcationToken to token provided back from the user authentication, the token is provided as a param to your callback url.

  token.getProvider().retrieveAccessToken(token.getConsumer(),
                  veryifcationToken);

  // you will want to persist the the access token and secret somewhere,
  // they will live until the user chooses revokes your access

  String accessToken = token.getConsumer().getToken();
  String accessSecret = token.getConsumer().getTokenSecret());


#Adding a workout

  String CONSUMER_KEY = "<your app key>";
  String CONSUMER_SECRET = "<your app secret>";
  OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                                           CONSUMER_SECRET);
				
  consumer.setTokenWithSecret(accessToken, accessSecret);
		 
  Workout wo = new Workout();
  wo.setFelt(Feeling.great);
  wo.setType(Type.running);
  wo.setDuration(600L);
  wo.setDistanceUnits(Units.miles);
  wo.setDistanceValue("2");
		 
  DailyMileClient client = new DailyMileClient(consumer);
  client.addWorkout(wo, "Created using the API");

#Fetching a user stream

  String CONSUMER_KEY = "<your app key>";
  String CONSUMER_SECRET = "<your app secret>";
  OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                                           CONSUMER_SECRET);
				
  consumer.setTokenWithSecret(accessToken, accessSecret);

  DailyMileClient client = new DailyMileClient(consumer);
  UserStream s = client.getUserStream("someUserName");
  for (Entry e : s.getEntries()) {
    ...
  }


#Fetching a workout

  String CONSUMER_KEY = "<your app key>";
  String CONSUMER_SECRET = "<your app secret>";
  OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                                           CONSUMER_SECRET);
				
  consumer.setTokenWithSecret(accessToken, accessSecret);

  DailyMileClient client = new DailyMileClient(consumer);	 
  Workout wo = client.getWorkout(<id of workout>);


#Adding a comment

  String CONSUMER_KEY = "<your app key>";
  String CONSUMER_SECRET = "<your app secret>";
  OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                                           CONSUMER_SECRET);
				
  consumer.setTokenWithSecret(accessToken, accessSecret);

  DailyMileClient client = new DailyMileClient(consumer);	 
  client.addComment("wow - nice job!", <id of workout>);


#Deleting an entry (such as a workout)

  String CONSUMER_KEY = "<your app key>";
  String CONSUMER_SECRET = "<your app secret>";
  OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
                                           CONSUMER_SECRET);
				
  consumer.setTokenWithSecret(accessToken, accessSecret);

  DailyMileClient client = new DailyMileClient(consumer);	 
  client.deleteEntry(<id of entry>);
