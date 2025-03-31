package com.app.model.port;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientResponse {

	private int  idClient;
	private String nameClient;
}
