package org.grafana.ms2.config;

import java.util.NoSuchElementException;

 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler  {

	
	  @ExceptionHandler({NotFoundException.class})
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  public ResponseEntity<ApplicationError> handleNotFound(Exception ex) {
		  
		  ApplicationError error = new ApplicationError("Data Not Found"
				  											, ex.getMessage()
				  											, "4001"
				  											,ex.getLocalizedMessage()
				  											, HttpStatus.NOT_FOUND.value()
				  											,null, null);
		  return new ResponseEntity<ApplicationError>(error, HttpStatus.NOT_FOUND) ;
	  
	  }

	  @ExceptionHandler({NoSuchElementException.class})
	  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	  public ResponseEntity<ApplicationError> handleErrors(Exception ex) {
		  
		  ApplicationError error = new ApplicationError("No process Entity or Method"
				  											, ex.getMessage()
				  											, "4002"
				  											,ex.getLocalizedMessage()
				  											, HttpStatus.UNPROCESSABLE_ENTITY.value()
				  											,null, null);
		  return new ResponseEntity<ApplicationError>(error, HttpStatus.UNPROCESSABLE_ENTITY) ;
	  
	  }
	  
	  
	  @ExceptionHandler({NumberFormatException.class})
	  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	  public ResponseEntity<ApplicationError> handleGeneralErrors(Exception ex) {
		  
		  ApplicationError error = new ApplicationError("No process Entity or Method"
				  											, ex.getMessage()
				  											, "5001"
				  											,ex.getLocalizedMessage()
				  											, HttpStatus.INTERNAL_SERVER_ERROR.value()
				  											,null, null);
		  return new ResponseEntity<ApplicationError>(error, HttpStatus.INTERNAL_SERVER_ERROR) ;
	  
	  }
}
