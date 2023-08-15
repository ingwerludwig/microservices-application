package com.javagrind.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteFilter {

    private static List<String> openAPIEndpoints = new ArrayList<>(Arrays.asList(
            "/api/auth/signin",
            "/api/auth/signup",
            "/api/auth/signout"
    ));

    public Predicate<ServerHttpRequest> isSecured =
            request -> openAPIEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
}
