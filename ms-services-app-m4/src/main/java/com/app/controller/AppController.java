package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.entity.Client;
import com.app.model.port.ClientResponse;
import com.app.model.repository.ClientRepository;

@RestController
@RequestMapping("/api")
public class AppController {
	
	@Autowired
    private ClientRepository clientRepository;
	
	
	@RequestMapping(path = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody  ResponseEntity<ClientResponse> getClientInfo(){
		return new ResponseEntity<ClientResponse>(ClientResponse.builder().idClient(1).nameClient("randonName").build(), HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(path = "/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody  ResponseEntity<List<Client>> gelAllinfo(){
		
		List <Client> clients = clientRepository.findAll();
		
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
		
	}

}
