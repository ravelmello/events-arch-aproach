package br.com.ravel.command;

import br.com.ravel.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ravel_melo on 11/08/2023
 * @project food-good
 */

@Aggregate
public class FoodCartAgg {
    private static final Logger logger = LoggerFactory.getLogger(FoodCartAgg.class);

    @AggregateIdentifier
    private UUID foodCartId;

    private Map<UUID, Integer> selectedProducts;

    private boolean isConfirmed;

    public FoodCartAgg(){}

    @CommandHandler
    public FoodCartAgg(CreateFoodCartCommand command){
        AggregateLifecycle.apply(new FoodCartCreatedEvent(command.getFoodCartInt())); //chamada do evento
    }

    @CommandHandler
    public void handle(SelectProductCommand command){
        AggregateLifecycle.apply(new ProductSelectedEvent(foodCartId, command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(DeselectProductCommand command){
        AggregateLifecycle.apply(new ProductDeselectedEvent(foodCartId,command.getProductId(), command.getQuantity()));
    }

    @CommandHandler
    public void handle(OrderConfirmedCommand command) {
        AggregateLifecycle.apply(new OrderConfirmedCommand(foodCartId));
    }

    @CommandHandler
    public void handle(ProductCreateCommand command){
        AggregateLifecycle.apply(new ProductCreateCommand(command.getProductId(), command.getProductName()));
    }

    @EventSourcingHandler
    public void on(FoodCartCreatedEvent event) {
        foodCartId = event.getFoodCartId();
        selectedProducts = new HashMap<>();
        isConfirmed = false;
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        String productName = event.getProductName();
        String productId = event.getProductId();
    }

    @EventSourcingHandler
    public void on(ProductSelectedEvent event) {
        selectedProducts.merge(event.getFoodCartId(), event.getQuantity(), Integer::sum);
    }

    @EventSourcingHandler
    public void on(ProductDeselectedEvent event) {
        selectedProducts.computeIfPresent(event.getProductId(),
                (productId, quantity) -> quantity -= event.getQuantity()
        );
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        isConfirmed = true;
    }




}
