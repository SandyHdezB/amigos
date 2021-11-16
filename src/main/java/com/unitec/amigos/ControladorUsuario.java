package com.unitec.amigos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ControladorUsuario {
    //metodo que representa cada uno de los estados atransferir es decir, va un GET
    //POST, PUT y DELETE. como minimo

        //aqui viene ya el uso de la inversion de control
    @Autowired RepositorioUsuario repoUsuario;

    //implementamos el codigo para guardar un usuario en Mongo BD
    @PostMapping("/usuario")
    public Estatus guardar (@RequestBody String json) throws Exception{
    //primero leemos y convertimos el objeto son a objeto java
        ObjectMapper mapper = new ObjectMapper();
        Usuario u=mapper.readValue(json,Usuario.class);
        //este usuario en formato son lo guardamos en mongoDB
        repoUsuario.save(u);
        //creamos un objeto de tipo estatus y est objeto lo retonamos al cliente (androidd o post)
        Estatus estatus=new Estatus();
        estatus.setSuccess(true);
        estatus.setMensaje("Tu usuario se guardo con exito!!!");
        return  estatus;

    }

    @GetMapping("/usuario/{id}")
    public Usuario obtenerPorId(@PathVariable String id){
        //leemos un usuario con el metodo findById pasandole como argumento el id(email) que queremos
        //apoyandonos de repoUsuario
          Usuario u= repoUsuario.findById(id).get();
        return u;
    }
    //metodo buscar
    @GetMapping("/usuario")
    public List<Usuario> buscarTodos (){
        return repoUsuario.findAll();
    }

    //metodo para actualizar
    @PutMapping("/usuario")
    public Estatus actualizar(@RequestBody String json) throws Exception{
        //primero debemos verificar que exista el usuario por lo tanto lo buscamos primero
        ObjectMapper mapper=new ObjectMapper();
        Usuario u=mapper.readValue(json,Usuario.class);
        Estatus e=new Estatus();
        if (repoUsuario.findById(u.getEmail()).isPresent()){
            //lo volvemos a guardar
            repoUsuario.save(u);
            e.setMensaje("Usuario se actualizo con exito");
            e.setSuccess(true);
        }else{
            e.setMensaje("Este usuario no existe, no se actualizara");
            e.setSuccess(false);
        }
        return e;
    }
    @DeleteMapping ("/usuario/{id}")
    public Estatus borrar (@PathVariable String id){
        Estatus estatus=new Estatus();
        if (repoUsuario.findById(id).isPresent()){
            repoUsuario.deleteById(id);
            estatus.setSuccess(true);
            estatus.setMensaje("Usuario borrado con exito");
        }else{
            estatus.setSuccess(false);
            estatus.setMensaje("Este usuario no existe");
        }
        return estatus;
    }




}
