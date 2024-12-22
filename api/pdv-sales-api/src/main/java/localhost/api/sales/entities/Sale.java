package localhost.api.sales.entities;

import java.time.LocalDateTime;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import localhost.modellibrary.api.sales.SaleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "sale")
public class Sale {

    @Id
    @Column(updatable = false, insertable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(updatable = false, nullable = false)
    private String owner;

    @Column(nullable = false)
    private LocalDateTime moment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status;

    @OneToMany(mappedBy = "sale", fetch = FetchType.EAGER)
    private Collection<SaleItem> items;
}
