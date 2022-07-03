package com.ustundagsemih.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class APIController {

	@GetMapping("/api2")
	public String index(HttpServletRequest request) {
        String queryString = request.getQueryString();
        System.out.println(queryString);
        return queryString;
	}

}
