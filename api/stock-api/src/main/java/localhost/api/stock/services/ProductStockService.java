package localhost.api.stock.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import localhost.api.stock.entities.ProductStock;
import localhost.api.stock.repositories.ProductStockRepository;
import localhost.modellibrary.api.exceptions.ResourceNotFoundException;

@Service
public class ProductStockService {

    @Autowired
    private ProductStockRepository productStockRepository;

    public ProductStock readStockForProduct(Integer productId) {
        return productStockRepository.findById(productId).orElseThrow(new ResourceNotFoundException());
    }

    public void increaseStockQuantityFor(Integer productId, Integer quantity) {

        if (productId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        var stockInfo = productStockRepository.findById(productId).orElseThrow(new ResourceNotFoundException());

        stockInfo.setQuantityInStock(stockInfo.getQuantityInStock() + quantity);
        productStockRepository.save(stockInfo);
    }

    public void decreaseStockQuantityFor(Integer productId, Integer quantity) {

        if (productId == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        var stockInfo = productStockRepository.findById(productId).orElseThrow(new ResourceNotFoundException());

        stockInfo.setQuantityInStock(stockInfo.getQuantityInStock() - quantity);
        productStockRepository.save(stockInfo);
    }

    public Collection<ProductStock> listStock() {
        return productStockRepository.findAll();
    }

    
    public boolean createStockForProduct(Integer productId){

        if (productId == null) {
            throw new IllegalArgumentException();
        } else if (this.productStockRepository.existsById(productId)){
            return true;
        }

        ProductStock productStock = new ProductStock();
        productStock.setProductId(productId);
        productStock.setQuantityInStock(0);

        return this.productStockRepository.save(productStock).getProductId() != null;
    }

}
