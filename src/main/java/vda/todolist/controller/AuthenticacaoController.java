package vda.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.web.bind.annotation.*;
import vda.todolist.DTO.DadosAuthenticacao;
import vda.todolist.model.Usuario;
import vda.todolist.security.DadosTokenJWT;
import vda.todolist.service.TokenService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAuthenticacao dados) {
        var authenticationTokentoken = new UsernamePasswordAuthenticationToken(dados.login().trim(), dados.senha().trim());

        var authentication = manager.authenticate(authenticationTokentoken);

        var tokenJWT = tokenService.gerarToken( (Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }


}