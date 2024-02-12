package entities;

import enums.ClienteTipologia;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Cliente extends PanacheEntity {

    @Column(name = "nome_cliente")
    public String nome;

    @Column(name = "cognome_cliente")
    public String cognome;

    @Column(name = "indirizzo_cliente")
    public String indirizzo;

    @Column(name = "email_cliente")
    public String email;

    @Column(name = "numero_telefono_cliente")
    public String numeroTelefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    public ClienteTipologia tipoCliente;

    @Override
    public String toString() {
        return String.format("Cliente{" +
                "nome='%s'," +
                " cognome='%s'," +
                " indirizzo='%s'," +
                " email='%s'," +
                " numeroTelefono='%s'," +
                " tipoCliente=%s" +
                "}", nome, cognome, indirizzo, email, numeroTelefono, tipoCliente);
    }
}
