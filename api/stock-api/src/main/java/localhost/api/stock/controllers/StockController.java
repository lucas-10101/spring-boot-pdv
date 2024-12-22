package localhost.api.stock.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import localhost.api.stock.services.ProductStockService;
import localhost.commonslibrary.api.security.Authorities;
import localhost.modellibrary.api.stock.ProductStockModel;


@RestController
@RequestMapping(path = "/stock")
public class StockController {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ModelMapper mapper;

    @Secured(Authorities.StockApi.MANAGE_STOCK)
    @PostMapping("/{productId}")
    public ResponseEntity<Void> createStockForProduct(@PathVariable Integer productId) {
        
        this.productStockService.createStockForProduct(productId);

        return ResponseEntity.ok(null);
    }
    

    @Secured(Authorities.StockApi.READ_STOCK)
    @GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductStockModel> readStockForProduct(@PathVariable Integer productId) {

        var mapped = mapper.map(productStockService.readStockForProduct(productId), ProductStockModel.class);

        return ResponseEntity.ok(mapped);
    }

    @Secured(Authorities.StockApi.READ_STOCK)
    @GetMapping()
    public ResponseEntity<Collection<ProductStockModel>> listStock() {

        var list = productStockService.listStock().stream().map((e) -> {
            return mapper.map(e, ProductStockModel.class);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @Secured(Authorities.StockApi.MANAGE_STOCK)
    @PatchMapping(path = "/increase-stock")
    public ResponseEntity<Void> increaseStockQuantityFor(@RequestBody ProductStockModel stockModel) {
        this.productStockService.increaseStockQuantityFor(stockModel.getProductId(), stockModel.getQuantityInStock());
        return ResponseEntity.ok(null);
    }

    @Secured(Authorities.StockApi.MANAGE_STOCK)
    @PatchMapping(path = "/decrease-stock")
    public ResponseEntity<Void> decreaseStockQuantityFor(@RequestBody ProductStockModel stockModel) {
        this.productStockService.decreaseStockQuantityFor(stockModel.getProductId(), stockModel.getQuantityInStock());
        return ResponseEntity.ok(null);
    }

}
