package com.promptoven.cartservice.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.promptoven.cartservice.adapter.in.web.vo.CartCreateRequestVo;
import com.promptoven.cartservice.adapter.in.web.vo.CartDeleteRequestVo;
import com.promptoven.cartservice.adapter.in.web.vo.CartGetRequestVo;
import com.promptoven.cartservice.adapter.in.web.vo.CartResponseVo;
import com.promptoven.cartservice.adapter.in.web.vo.CartUpdateVo;
import com.promptoven.cartservice.application.port.in.CartRequestDto;

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

	public CartRequestDto toGetDto(CartGetRequestVo cartGetRequestVo) {
		return CartRequestDto.builder()
			.memberUuid(cartGetRequestVo.getMemberUuid())
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
			.memberUuid(cartUpdateVo.getMemberUuid())
			.productUuid(cartUpdateVo.getProductUuid())
			.selected(cartUpdateVo.isSelected())
			.build();
	}

	public CartRequestDto toDeleteDto(CartDeleteRequestVo cartDeleteRequestVo) {
		return CartRequestDto.builder()
			.memberUuid(cartDeleteRequestVo.getMemberUuid())
			.productUuid(cartDeleteRequestVo.getProductUuid())
			.build();
	}
}