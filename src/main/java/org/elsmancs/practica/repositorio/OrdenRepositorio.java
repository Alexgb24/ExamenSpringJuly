package org.elsmancs.practica.repositorio;

import java.util.List;
import java.util.Optional;
import org.elsmancs.practica.dominio.MagicalItem;
import org.springframework.data.repository.CrudRepository;

public interface OrdenRepositorio extends CrudRepository<MagicalItem, Long>{
    public Optional<List<MagicalItem>> findByName(String name);
}