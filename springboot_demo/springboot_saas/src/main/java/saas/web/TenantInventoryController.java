package saas.web;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saas.annotation.RootResource;
import saas.annotation.TenantResource;
import saas.domain.Inventory;
import saas.domain.InventoryRepository;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:04
 */
@RestController
@RequestMapping("/tenant_inventories")
@TenantResource
public class TenantInventoryController {
    @Resource
    private InventoryRepository inventoryRepository;

    @RequestMapping("/")
    public String list() {
        StringWriter string = new StringWriter();
        PrintWriter writer = new PrintWriter(string);
        Iterable<Inventory> inventories = inventoryRepository.findAll();
        for (Inventory inventory : inventories) {
            writer.println(inventory.getName());
        }
        return string.toString();
    }

    @RequestMapping("/root")
    @RootResource
    public String rootList() {
        StringWriter string = new StringWriter();
        PrintWriter writer = new PrintWriter(string);
        Iterable<Inventory> inventories = inventoryRepository.findAll();
        for (Inventory inventory : inventories) {
            writer.println(inventory.getName());
        }
        return string.toString();
    }
}
