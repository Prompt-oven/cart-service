package com.promptoven.cartservice.application.service;

import com.promptoven.cartservice.application.mapper.CartDtoMapper;
import com.promptoven.cartservice.application.port.in.CartRequestDto;
import com.promptoven.cartservice.application.port.in.CartUseCase;
import com.promptoven.cartservice.application.port.out.CartOutportDto;
import com.promptoven.cartservice.application.port.out.CartRepositoryPort;
import com.promptoven.cartservice.domain.model.Cart;
import com.promptoven.cartservice.domain.service.CartDomainService;
import com.promptoven.cartservice.global.common.response.BaseResponseStatus;
import com.promptoven.cartservice.global.error.BaseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService implements CartUseCase {

    private final CartRepositoryPort cartRepositoryPort;
    private final CartDomainService cartDomainService;
    private final CartDtoMapper cartDtoMapper;

    public CartService(@Qualifier("cartMysqlAdapter") CartRepositoryPort cartRepositoryPort) {
        this.cartRepositoryPort = cartRepositoryPort;
        this.cartDomainService = new CartDomainService();
        this.cartDtoMapper = new CartDtoMapper();
    }

    @Override
    @Transactional
    public void createCart(CartRequestDto cartCreateRequestDto) {

        cartRepositoryPort.getCartByProductUuidAndMemberUuid(
                cartCreateRequestDto.getProductUuid(),
                cartCreateRequestDto.getMemberUuid()
        ).ifPresentOrElse(
                this::handleExistingCart, // 메서드 참조로 변경
                () -> handleNewCart(cartCreateRequestDto) // 생성 시 추가 파라미터가 필요하므로 람다 유지
        );
    }

    @Override
    public List<CartRequestDto> getCart(CartRequestDto cartGetRequestDto) {

        Cart cart = cartDtoMapper.toDomain(cartGetRequestDto);

        List<CartOutportDto> cartOutportDtoList = cartRepositoryPort.getCart(cartDtoMapper.toDto(cart));

        List<Cart> cartList = cartDomainService.getCart(cartOutportDtoList);

        return cartDtoMapper.toDtoList(cartList);
    }

    @Transactional
    @Override
    public void updateCartItem(CartRequestDto cartUpdateRequestDto) {

        CartOutportDto cartOutportDto = cartRepositoryPort.getCartByProductUuidAndMemberUuid(
                        cartUpdateRequestDto.getProductUuid(), cartUpdateRequestDto.getMemberUuid()).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        Cart cart = cartDomainService.updateCart(cartOutportDto, cartUpdateRequestDto);

        cartRepositoryPort.updateCartItem(cartDtoMapper.toDto(cart));
    }

    @Transactional
    @Override
    public void deleteCartItem(CartRequestDto cartDeleteRequestDto) {

        CartOutportDto cartOutportDto = cartRepositoryPort.getCartByProductUuidAndMemberUuid(
                        cartDeleteRequestDto.getProductUuid(), cartDeleteRequestDto.getMemberUuid())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CART));

        Cart cart = cartDomainService.deleteCart(cartOutportDto);

        cartRepositoryPort.deleteCartItem(cartDtoMapper.toDto(cart));
    }

    // 새로운 카트 생성 처리
    private void handleNewCart(CartRequestDto cartCreateRequestDto) {
        Cart cart = cartDomainService.createCart(cartCreateRequestDto);
        cartRepositoryPort.save(cartDtoMapper.toCreateDto(cart));
    }

    // 이전에 삭제된 카트 활성화 처리
    private void handleExistingCart(CartOutportDto cartOutportDto) {
        if (cartOutportDto.isDeleted()) {
            Cart cart = cartDomainService.updateExistCart(cartOutportDto);
            cartRepositoryPort.updateCartItem(cartDtoMapper.toDto(cart));
        }
    }
}

