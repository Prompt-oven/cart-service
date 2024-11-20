package com.promptoven.cartservice.adapter.web.mapper;

import com.promptoven.cartservice.adapter.web.vo.CartCreateRequestVo;
import com.promptoven.cartservice.adapter.web.vo.CartDeleteRequestVo;
import com.promptoven.cartservice.adapter.web.vo.CartResponseVo;
import com.promptoven.cartservice.adapter.web.vo.CartUpdateVo;
import com.promptoven.cartservice.application.port.dto.in.CartRequestDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartVoMapper {

    public CartRequestDto toDto(CartCreateRequestVo cartCreateRequestVo) {
        return CartRequestDto.builder()
                .memberUuid(cartCreateRequestVo.getMemberUuid())
                .productUuid(cartCreateRequestVo.getProductUuid())
                .selected(true)
                .deleted(false)
                .build();
    }

    public CartRequestDto toGetDto(String memberUuid) {
        return CartRequestDto.builder()
                .memberUuid(memberUuid)
                .build();
    }

    public List<CartResponseVo> toVoList(List<CartRequestDto> cartRequestDtoList) {
        return cartRequestDtoList.stream().map(cartRequestDto -> CartResponseVo.builder()
                .id(cartRequestDto.getId())
                .memberUuid(cartRequestDto.getMemberUuid())
                .productUuid(cartRequestDto.getProductUuid())
                .selected(cartRequestDto.isSelected())
                .deleted(cartRequestDto.isDeleted())
                .createdDate(cartRequestDto.getCreatedDate())
                .lastModifiedDate(cartRequestDto.getLastModifiedDate())
                .build()).toList();
    }

    public CartRequestDto toUpdateDto(CartUpdateVo cartUpdateVo) {
        return CartRequestDto.builder()
                .id(cartUpdateVo.getCartId())
                .selected(cartUpdateVo.isSelected())
                .build();
    }

    public CartRequestDto toDeleteDto(CartDeleteRequestVo cartDeleteRequestVo) {
        return CartRequestDto.builder()
                .id(cartDeleteRequestVo.getCartId())
                .build();
    }
}