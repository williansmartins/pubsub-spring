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
import com.williansmartins.pubsub.domain.User;

@RestController
public class ApiController {
	
	@Autowired
	private PubSubConfig.PubSubOutboundGateway messagingGateway;

	@RequestMapping(value = "send/{nome}/{idade}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> send(@PathVariable(value="nome") String nome, @PathVariable(value="idade") int idade) {
		User user = new User(nome, idade);
		messagingGateway.sendToPubSub("com.williansmartins.topico1", user);
		return new ResponseEntity<>("A mensagem [" + user.toString() + "] foi enviada!", HttpStatus.OK);
	}
	
	
}
