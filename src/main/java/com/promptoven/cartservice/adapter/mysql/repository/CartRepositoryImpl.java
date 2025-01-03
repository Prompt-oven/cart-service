package com.promptoven.cartservice.adapter.mysql.repository;

import com.promptoven.cartservice.adapter.mysql.mapper.CartEntityMapper;
import com.promptoven.cartservice.application.port.out.dto.CartOutportDto;
import com.promptoven.cartservice.application.port.out.call.CartRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// output adapter
@RequiredArgsConstructor
@Repository
@Component("cartMysqlAdapter")
public class CartRepositoryImpl implements CartRepositoryPort {

    private final CartJpaRepository cartJpaRepository;
    private final CartEntityMapper cartEntityMapper;

    @Override
    public Optional<CartOutportDto> getCartByProductUuidAndMemberUuid(String productUuid, String memberUuid) {

        return cartJpaRepository.findByProductUuidAndMemberUuid(productUuid, memberUuid).map(cartEntityMapper::toDto);
    }

    @Override
    public Optional<CartOutportDto> getCartByCartId(Long id) {

        return cartJpaRepository.findById(id).map(cartEntityMapper::toDto);
    }

    @Override
    public void save(CartOutportDto cartOutportDto) {

        cartJpaRepository.save(cartEntityMapper.toEntity(cartOutportDto));
    }

    @Override
    public List<CartOutportDto> getCart(CartOutportDto cartOutportDto) {

        return cartJpaRepository.findByMemberUuidAndDeletedFalse(cartOutportDto.getMemberUuid()).stream()
                .map(cartEntityMapper::toDto).toList();
    }

    @Override
    public void updateCartItem(CartOutportDto cartOutportDto) {

        cartJpaRepository.save(cartEntityMapper.toUpdateEntity(cartOutportDto));
    }

    @Override
    public void deleteCartItem(CartOutportDto cartOutportDto) {

        cartJpaRepository.save(cartEntityMapper.toDeleteEntity(cartOutportDto));
    }
}
