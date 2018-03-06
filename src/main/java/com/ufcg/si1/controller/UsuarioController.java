package com.ufcg.si1.controller;

import com.ufcg.si1.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @RequestMapping(value= "/api/autentica", method = RequestMethod.POST)
    public ResponseEntity<?> autentica(@RequestBody Usuario usuario) {
        if(!usuario.getLogin().equals("admin") || !usuario.getSenha().equals("banana"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
