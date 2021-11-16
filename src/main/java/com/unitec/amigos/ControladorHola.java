package com.unitec.amigos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorHola {
    //este es el primer recurso de tu hola mundo de tu sericio REST que se usa el metodo GET
    @GetMapping("/hola")
    public String saludar (){
        return "Hola desde mi primer servicio REST";
    }



}
