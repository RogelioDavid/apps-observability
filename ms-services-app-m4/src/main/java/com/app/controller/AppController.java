package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.infra.client.SellerClient;
import com.app.model.entity.Client;
import com.app.model.port.ClientResponse;
import com.app.model.repository.ClientRepository;
import com.app.model.transfer.Post;

@RestController
@RequestMapping("/api")
public class AppController {
	
	@Autowired
    private ClientRepository clientRepository;
	
	@Autowired
    private  RestTemplate restTemplate;
	
	@Autowired
	private SellerClient client;
	
	
	@RequestMapping(path = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody  ResponseEntity<ClientResponse> getClientInfo(@RequestHeader(value = "X-Tenant-ID", required = false) String actionName){
		return new ResponseEntity<ClientResponse>(ClientResponse.builder().idClient(1).nameClient("randonName").build(), HttpStatus.OK);
		
	}
	
	
	
	@RequestMapping(path = "/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody  ResponseEntity<List<Client>> gelAllinfo(@RequestHeader(value = "X-Tenant-ID", required = false) String actionName){
		
		List <Client> clients = clientRepository.findAll();
		
		return new ResponseEntity<List<Client>>(clients, HttpStatus.OK);
		
	}


	@RequestMapping(path = "/findUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody  ResponseEntity<ClientResponse> gelAllinfo2(@RequestHeader(value = "X-Tenant-ID", required = false) String actionName){
		
		return getClientInfo(actionName);
		
	}

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        Post post = restTemplate.getForObject(url, Post.class);
        return ResponseEntity.ok(post);
    }

    
    @GetMapping("/posts-feing/{id}")
    public Post getPostByIdFeing(@PathVariable Long id) {
    	return  client.getPostById(id);
       
    }

}
