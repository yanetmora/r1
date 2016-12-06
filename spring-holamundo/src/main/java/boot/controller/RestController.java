package boot.controller;

import  org.springframework.boot.SprintAplication;
import org.springframework.boot.autoconfigiure.SprintBootApplication;






@RestController
public class RestController {
	@GetMapping("/");
public String hello(){
	return "Hello World!";
			
}


}
