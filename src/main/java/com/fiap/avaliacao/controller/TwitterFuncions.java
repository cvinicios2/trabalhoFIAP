package com.fiap.avaliacao.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.fiap.avaliacao.model.Tweet;
import com.fiap.avaliacao.util.Utils;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterFuncions {

	private Twitter twitter = Utils.createTwitter();
	private String busca;

	private static ArrayList<Tweet> listaTweets = new ArrayList<Tweet>();

	public void enviaQtdeTweetsUltimaSemana(String user) {

		if (listaTweets.size() > 0) {
			Map<String, Integer> map = new TreeMap<String, Integer>();
			for (Tweet lt : listaTweets) {

				String dataTweet = new SimpleDateFormat("dd/MM/yyyy").format(lt.getData());

				if (!map.containsKey(dataTweet)) {
					map.put(dataTweet, 0);
				}
				map.put(dataTweet, map.get(dataTweet) + 1);
			}

			String mensagem = "Quantidade de Tweets " + busca + " da ultima semana por dia: ";
			System.out.println(mensagem);
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				System.out.println("Data: " + entry.getKey() + " - Tweets: " + entry.getValue());
				enviaTweet(mensagem + " Data: " + entry.getKey() + " Qtde: " + entry.getValue(), user);
			}
		} else {
			System.out.println("Execute o metodo buscaTweets antes do metodo enviaQtdeTweetsUltimaSemana!");
		}
	}

	public void enviaQtdeRetweetsUltimaSemana(String user) {

		if (listaTweets.size() > 0) {
			Map<String, Integer> map = new TreeMap<String, Integer>();
			for (Tweet lt : listaTweets) {

				if (lt.getRetweets() > 0) {
					String dataTweet = new SimpleDateFormat("dd/MM/yyyy").format(lt.getData());

					if (!map.containsKey(dataTweet)) {
						map.put(dataTweet, 0);
					}
					map.put(dataTweet, map.get(dataTweet) + lt.getRetweets());
				}
			}
			String mensagem = "Quantidade de Retweets " + busca + " da ultima semana por dias:";
			System.out.println("\n" + mensagem);
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				System.out.println("Data: " + entry.getKey() + " - Retweets: " + entry.getValue());
				enviaTweet(mensagem + " Data: " + entry.getKey() + " Qtde: " + entry.getValue(), user);
			}

		} else {
			System.out.println("Execute o metodo buscaTweets antes do metodo enviaQtdeRetweetsUltimaSemana!");
		}
	}

	public void enviaQtdeFavoritacoesUltimaSemana(String user) {

		if (listaTweets.size() > 0) {
			Map<String, Integer> map = new TreeMap<String, Integer>();
			for (Tweet lt : listaTweets) {

				if (lt.getRetweets() > 0) {
					String dataTweet = new SimpleDateFormat("dd/MM/yyyy").format(lt.getData());

					if (!map.containsKey(dataTweet)) {
						map.put(dataTweet, 0);
					}
					map.put(dataTweet, map.get(dataTweet) + lt.getFavoritos());
				}
			}
			String mensagem = "Quantidade de Favoritações " + busca + " da ultima semana por dias:";
			System.out.println("\n" + mensagem);
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				System.out.println("Data: " + entry.getKey() + " - Favoritações: " + entry.getValue());
				enviaTweet(mensagem + " Data: " + entry.getKey() + " Qtde: " + entry.getValue(), user);
			}
		} else {
			System.out.println("Execute o metodo buscaTweets antes do metodo enviaQtdeFavoritacoesUltimaSemana!");
		}

	}

	public void enviaPrimeiroEUltimoAutor(String user) {

		if (listaTweets.size() > 0) {
			Collections.sort(listaTweets, new Comparator<Tweet>() {
				public int compare(Tweet tweet1, Tweet tweet2) {

					return tweet1.getUser().toLowerCase().compareTo(tweet2.getUser().toLowerCase());
				}
			});

			String mensagem = " Primeiro Autor: " + listaTweets.get(0).getUser() + " Último Autor: "
					+ listaTweets.get(listaTweets.size() - 1).getUser() + " " + busca;
			enviaTweet(mensagem, user);
			System.out.println("\nPrimeiro autor: " + listaTweets.get(0).getUser());
			System.out.println("Ultimo autor: " + listaTweets.get(listaTweets.size() - 1).getUser());
		} else {
			System.out.println("Execute o metodo buscaTweets antes do metodo enviaPrimeiroEUltimoAutor!");
		}

	}

	public void enviaDataMaisRecenteEAntiga(String user) {

		if (listaTweets.size() > 0) {
			Collections.sort(listaTweets, new Comparator<Tweet>() {
				public int compare(Tweet tweet1, Tweet tweet2) {

					return tweet1.getData().compareTo(tweet2.getData());
				}
			});
			

			String dataPrimeiroTweet = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(listaTweets.get(0).getData());

			System.out.println("\nData do primeiro Tweet: " + dataPrimeiroTweet);
			String dataUltimoTweet = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
					.format(listaTweets.get(listaTweets.size() - 1).getData());
			System.out.println("Data do ultimo Tweet: " + dataUltimoTweet);
			
			String mensagem = "Data do primeiro tweet: " + dataPrimeiroTweet + " Data do ultimo tweet: "
					+ dataUltimoTweet + " " + busca;
			enviaTweet(mensagem, user);

		} else {
			System.out.println("Execute o metodo buscaTweets antes do metodo enviaDataMaisRecenteEAntiga!");
		}
	}

	public void buscaTweets(String palavra) {
		try {
			this.busca = palavra;
			Query query = new Query(palavra);

			LocalDate dataSince = LocalDate.now().minusDays(7);
			query.setSince('"' + dataSince.toString() + '"');
			query.setCount(200);

			QueryResult result = twitter.search(query);
			do {
				result = twitter.search(query);
				query = result.nextQuery();

				for (Status status : result.getTweets()) {

					Date dataTweet = status.getCreatedAt();
					Date dataS = new SimpleDateFormat("yyyy-MM-dd").parse(dataSince.toString());

					if (dataTweet.after(dataS)) {
						int id = (int) status.getId();
						String user = status.getUser().getName();
						String text = status.getText();
						int retweets = status.getRetweetCount();
						int favoritos = status.getFavoriteCount();
						Tweet tweet = new Tweet(id, user, text, retweets, dataTweet, favoritos);

						listaTweets.add(tweet);
					}
				}
			} while (result.hasNext());
		} catch (TwitterException e) {
			System.out.println("Erro de acesso ao twitter");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Erro de conversao de datas");
			e.printStackTrace();
		}
	}

	public void enviaTweet(String message, String user) {

		try {
			this.twitter.updateStatus(user + " " + message);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
