package com.microgram.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Tag(name = "OAuth2", description = "OAuth2 authentication redirect handlers")
public class OAuth2Controller {

    @Operation(summary = "OAuth2 success redirect", description = "Handle successful OAuth2 authentication")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OAuth2 success page displayed")
    })
    @GetMapping("/oauth2/redirect")
    public String oauth2Redirect(@Parameter(description = "JWT token from OAuth2 provider") 
                                @RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "oauth2-success";
    }

    @Operation(summary = "OAuth2 error redirect", description = "Handle OAuth2 authentication errors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OAuth2 error page displayed")
    })
    @GetMapping("/oauth2/error")
    public String oauth2Error(@Parameter(description = "Error message from OAuth2 provider") 
                             @RequestParam("error") String error, Model model) {
        model.addAttribute("error", error);
        return "oauth2-error";
    }
}