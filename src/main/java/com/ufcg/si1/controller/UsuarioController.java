package com.ufcg.si1.controller;

import com.ufcg.si1.model.Reserva;
import com.ufcg.si1.model.Usuario;
import com.ufcg.si1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value= "/api/autentica", method = RequestMethod.POST)
    public ResponseEntity<?> autentica(@RequestBody Usuario usuario) {
        if(!usuario.getLogin().equals("admin") || !usuario.getSenha().equals("banana")){
            if(usuarioService.usuarioExiste(usuario)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @RequestMapping(value="/api/usuario", method=RequestMethod.POST)
    public ResponseEntity<?> cadastraUsuario(@RequestBody Usuario usuario) {
        try {
            if(usuarioService.usuarioExiste(usuario)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            usuarioService.saveUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/api/usuario/reservas", method=RequestMethod.POST)
    public ResponseEntity<?> cadastraReserva(@RequestParam("usuario") String login, @RequestParam("password") String senha, @RequestBody Reserva reserva) {
        boolean reservaSucedida = usuarioService.registraReserva(login, senha, reserva);

        if(reservaSucedida)
            return new ResponseEntity<>(HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/api/usuario/reservas", method=RequestMethod.GET)
    public ResponseEntity<?> getAllReservas(@RequestParam("usuario") String login, @RequestParam("password") String senha) {
        return new ResponseEntity<>(usuarioService.getAllReservas(login, senha), HttpStatus.OK);
    }

}
