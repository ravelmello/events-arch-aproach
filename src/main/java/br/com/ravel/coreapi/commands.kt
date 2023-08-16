package br.com.ravel.coreapi

import org.axonframework.commandhandling.RoutingKey
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

/**
 * @project foodgood
 * @author ravel_melo on 11/08/2023
 *
 */
class CreateFoodCartCommand(
       @RoutingKey val foodCartInt: UUID
)

data class ProductCreateCommand(
        @TargetAggregateIdentifier val productId: String,
        val productName: String
)

data class SelectProductCommand(
       @TargetAggregateIdentifier val foodCartId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class DeselectProductCommand(
        @TargetAggregateIdentifier  val foodCartId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class OrderConfirmedCommand(
        @TargetAggregateIdentifier   val foodCartId: UUID
)
