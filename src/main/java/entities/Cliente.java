package entities;

import enums.ClienteTipologia;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email_cliente"})})
@Getter
@Setter
public class Cliente extends PanacheEntity {

    @Column(name = "nome_cliente")
    @NotBlank(message = "Il campo 'Nome' è obbligatorio")
    public String nome;

    @Column(name = "indirizzo_cliente")
    @NotBlank(message = "Il campo 'Indirizzo' è obbligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "L'indirizzo non è valido. Può contenere solo lettere, numeri e spazi")
    public String indirizzo;

    @Column(name = "email_cliente")
    @NotBlank(message = "Il campo 'E-mail' è obbligatorio")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "L'indirizzo e-mail non è valido deve contenere caratteri alfanumerici, '@' e un dominio")
    public String email;

    @Column(name = "numero_telefono_cliente")
    @NotBlank(message = "Il campo 'Numero di telefono' è obbligatorio")
    public String numeroTelefono;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    @NotNull(message = "Il campo 'Tipologia cliente' è obbligatorio")
    public ClienteTipologia tipoCliente;

    @Override
    public String toString() {
        return String.format("Cliente{" +
                "nome='%s'," +
                " indirizzo='%s'," +
                " email='%s'," +
                " numeroTelefono='%s'," +
                " tipoCliente=%s" +
                "}", nome, indirizzo, email, numeroTelefono, tipoCliente);
    }
}
