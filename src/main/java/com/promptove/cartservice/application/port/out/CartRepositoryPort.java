package com.promptove.cartservice.application.port.out;

import java.util.List;
import java.util.Optional;

//output port
public interface CartRepositoryPort {

	Optional<CartOutportDto> getCartByProductUuidAndMemberUuid(String productUuid, String memberUuid);

	void save(CartOutportDto cartOutportDto);

	List<CartOutportDto> getCart(CartOutportDto cartOutportDto);

	void updateCartItem(CartOutportDto cartOutportDto);

	void deleteCartItem(CartOutportDto cartOutportDto);
}
