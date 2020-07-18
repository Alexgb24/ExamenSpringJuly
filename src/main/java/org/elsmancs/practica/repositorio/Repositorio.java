package org.elsmancs.practica.repositorio;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import javax.persistence.PersistenceContext;

import org.elsmancs.practica.dominio.MagicalItem;
import org.elsmancs.practica.dominio.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class Repositorio {

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private OrdenRepositorio repositorioCrud;
    
    public Wizard loadWizard(String nombre) {
        return em.find(Wizard.class, nombre);
    }

    public MagicalItem loadItem(String name) {
        Optional<List<MagicalItem>> item = repositorioCrud.findByName(name);
        return item.isPresent()? item.get().get(0) : null;
    }

    public void createItem(String name, int quality, String type) {

        MagicalItem item = new MagicalItem();
        item.setName(name);
        item.setQuality(quality);
        item.setType(type);
        em.persist(item);

    }  
    
    public void createItems(List<MagicalItem> items) {
		for (MagicalItem item: items) {
			this.createItem(item.getName(), item.getQuality(), item.getType());
		}
	}
    
    public MagicalItem cargaItem(Long id) {
        return em.find(MagicalItem.class, id);
    }
    
}