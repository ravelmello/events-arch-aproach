package br.com.ravel.gui;

import br.com.ravel.coreapi.CreateFoodCartCommand;
import br.com.ravel.coreapi.ProductCreateCommand;
import br.com.ravel.coreapi.ProductSelectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author ravel_melo on 11/08/2023
 * @project foodgood
 */
@RequestMapping("/foodCard")
@RestController
public class FoodOrderController {

    private final CommandGateway commandGateway;

    public FoodOrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public CompletableFuture<UUID> createFoodCart() {
        return commandGateway.send(new CreateFoodCartCommand(UUID.randomUUID()));
    }


    @PostMapping("/product/create/{productName}")
    public void createProduct(
            @PathVariable("productName") String productName){
        String productId = UUID.randomUUID().toString();
        commandGateway.send(new ProductCreateCommand(productId, productName));
    }

    @PostMapping("/{foodCartId}/select/{productId}/quantity/{quantity}")
    public void selectProduct(@PathVariable("foodCartId") String foodCartId,
                              @PathVariable("productId") String productCartId,
                              @PathVariable("quantity") int quantity) {

        commandGateway.send(new ProductSelectedEvent(UUID.fromString(foodCartId),
                                UUID.fromString(productCartId),
                                quantity));

    }

    @PostMapping("/{foodCartId}/deselect/{productId}/quantity/{quantity}")
    public void deselectProduct(@PathVariable("foodCartId") String foodCartId,
                                @PathVariable("productId") String productCartId,
                                @PathVariable("quantity") int quantity){

        commandGateway.send(new ProductSelectedEvent(UUID.fromString(foodCartId),
                UUID.fromString(productCartId),
                quantity));
    }








}
