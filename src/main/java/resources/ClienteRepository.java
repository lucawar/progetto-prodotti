package resources;

import entities.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class ClienteRepository implements PanacheRepository<Cliente> {

    // QUERY DINAMICA PER RICERCA DEI CLIENTI TRAMITE 1 O PIU ATTRIBUTI

}
