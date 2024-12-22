package localhost.api.stock.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_stock")
public class ProductStock {

    @Id
    @Column(name = "product_id", updatable = false)
    @EqualsAndHashCode.Include
    private Integer productId;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;
}