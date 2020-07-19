package org.elsmancs.practica.servicio;

import org.elsmancs.practica.dominio.MagicalItem;
import org.elsmancs.practica.repositorio.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio repo;
    
    public MagicalItem cargaItem(Long id) {
        return repo.cargaItem(id);
    }
    
    
    public boolean isSavedItem(String item_name) {
    	if (repo.loadItem(item_name) == null) {
    		return false;
    	} else {
    		return true;
        }
    }


	public void createItem(String name, int quality, String type) {
		repo.createItem(name, quality, type);	
	}
}