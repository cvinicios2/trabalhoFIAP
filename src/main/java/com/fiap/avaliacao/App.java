package com.fiap.avaliacao;

import com.fiap.avaliacao.controller.TwitterFuncions;

public class App {

	public static void main(String[] args) {

		String user = "@michelpf";
		TwitterFuncions tf = new TwitterFuncions();
		tf.buscaTweets("#jvm");
		tf.enviaQtdeTweetsUltimaSemana(user);
		tf.enviaQtdeRetweetsUltimaSemana(user);
		tf.enviaQtdeFavoritacoesUltimaSemana(user);
		tf.enviaPrimeiroEUltimoAutor(user);
		tf.enviaDataMaisRecenteEAntiga(user);

	}
}