package com.ustundagsemih.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class APIController {

	@GetMapping("/api")
	public String index(HttpServletRequest request) {
        String test = request.getQueryString();
        System.out.println(test);
        return test;
	}

}
