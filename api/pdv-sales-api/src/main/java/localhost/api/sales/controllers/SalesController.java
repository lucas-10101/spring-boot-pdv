package localhost.api.sales.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import localhost.api.sales.entities.Sale;

@RestController
@RequestMapping(path = "/sales")
public class SalesController {

    /**
     * @TODO make logic to allow access only to the owner of the sale, or admins, or
     *       having the READ_ANY(?) role.
     * @param param
     * @return
     */
    @GetMapping("path")
    // @PreAuthorize("#{}") call bean to get the sale id and check against the owner
    // ???
    // new expression handlers ?
    public String getSale(@RequestBody Sale saleId) {
        return new String();
    }
}
