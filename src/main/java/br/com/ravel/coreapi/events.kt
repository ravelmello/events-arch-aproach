package br.com.ravel.coreapi

import java.util.UUID

/**
 * @project foodgood
 * @author ravel_melo on 11/08/2023
 *
 */

class FoodCartCreatedEvent(
        val foodCartId: UUID
)

data class ProductCreatedEvent(
        val productId: String,
        val productName: String
)

data class ProductSelectedEvent(
        val foodCartId: UUID,
        val productsId: UUID,
        val quantity: Int
)

data class ProductDeselectedEvent(
        val foodCartId: UUID,
        val productId: UUID,
        val quantity: Int
)

data class OrderConfirmedEvent(
        val foodCartId: UUID
)