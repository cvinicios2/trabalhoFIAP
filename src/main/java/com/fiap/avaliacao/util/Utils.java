package com.fiap.avaliacao.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Utils {

	private static Twitter twitter = null;

	public static Twitter createTwitter() {

		try {
			Properties prop = getProp();

			if (twitter == null) {
				twitter = TwitterFactory.getSingleton();
				AccessToken accessToken = new AccessToken(prop.getProperty("acessToken"),
						prop.getProperty("acessTokenSecret"));
				twitter.setOAuthConsumer(prop.getProperty("apiKey"), prop.getProperty("apiSecretKey"));
				twitter.setOAuthAccessToken(accessToken);
			}
			return twitter;
		} catch (IOException e) {
			System.out.println("Erro de acesso ao arquivo Properties");
			e.printStackTrace();
			return null;
		}
	}

	private static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./resources/config.properties");
		props.load(file);
		return props;

	}

}