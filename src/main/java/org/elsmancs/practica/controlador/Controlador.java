package org.elsmancs.practica.controlador;

import javax.transaction.Transactional;

import org.elsmancs.practica.dominio.MagicalItem;
import org.elsmancs.practica.servicio.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Transactional
    @PostMapping(path="/item/create")
    @ResponseBody
    public String itemGuardado(@RequestParam String name, @RequestParam int quality, @RequestParam String type) {
        servicio.createItem(name, quality, type);
        if (servicio.isSavedItem(name)) {
        	return "Saved";
    	} else {
    		return "Not Saved";
    	}
    }
}