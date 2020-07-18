package org.elsmancs.practica.controlador;



import org.elsmancs.practica.dominio.MagicalItem;
import org.elsmancs.practica.servicio.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Controlador {

    @Autowired
    Servicio servicio;
    
    @GetMapping(path="/item/{id}", produces={"application/json"})
    @ResponseBody
    public MagicalItem recuperaItem(@PathVariable Long id) {
        return servicio.cargaItem(id);

    }

    
}