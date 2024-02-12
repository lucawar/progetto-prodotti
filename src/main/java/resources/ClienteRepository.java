package resources;

import entities.Cliente;
import enums.ClienteTipologia;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Slf4j
public class ClienteRepository implements PanacheRepository<Cliente> {

    // QUERY DINAMICA PER RICERCA DEI CLIENTI TRAMITE 1 O PIU ATTRIBUTI
   public List<Cliente> findByAttribute(String nome, String indirizzo, String email, String numeroTelefono, ClienteTipologia tipoCliente, int offset, int limit){

       String queryString = "FROM Cliente c WHERE 1 = 1";
       Map<String, Object> params = new HashMap<>();

       if (nome != null) {
           queryString += " AND c.nome = :nome";
           params.put("nome", nome);
       }
       if (indirizzo != null) {
           queryString += " AND c.indirizzo = :indirizzo";
           params.put("indirizzo", indirizzo);
       }
       if (email != null) {
           queryString += " AND c.email = :email";
           params.put("email", email);
       }
       if (numeroTelefono != null) {
           queryString += " AND c.numeroTelefono = :numeroTelefono";
           params.put("numeroTelefono", numeroTelefono);
       }
       if (tipoCliente != null) {
           queryString += " AND c.tipoCliente = :tipoCliente";
           params.put("tipoCliente", tipoCliente);
       }

       PanacheQuery<Cliente> query = find(queryString, params);
       query.page(offset / limit, limit);
       log.info("CLIENTI FILTRATI CON SUCCESSO");
       return query.list();
   }
   }

