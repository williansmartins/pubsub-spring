package com.williansmartins.pubsub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.williansmartins.pubsub.config.PubSubConfig;
import com.williansmartins.pubsub.domain.Informe;

@RestController
public class ApiController {
	
	@Autowired
	private PubSubConfig.PubSubOutboundGateway messagingGateway;

	@RequestMapping(value = "gerar/informe/{documento}/{nome}/{sobrenome}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> send(@PathVariable(value="documento") String documento, @PathVariable(value="nome") String nome, @PathVariable(value="sobrenome") String sobrenome) {
		Informe informe = new Informe(nome + " " + sobrenome, documento);
		messagingGateway.sendToPubSub("com.williansmartins.topico1", informe);
		return new ResponseEntity<>("A mensagem [" + informe + "] foi enviada!", HttpStatus.OK);
	}
	
	
}
