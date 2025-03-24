package org.grafana.config;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
 

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, includeFieldNames = true)

public class ApplicationError  implements Serializable{


	private String errorProcessor;
	private String message;
	private String code;
	private String exceptionMessage;
	private int httpStatusCode;
	private String url;

	private Map<String, Object> errorAttributes;
	
}
