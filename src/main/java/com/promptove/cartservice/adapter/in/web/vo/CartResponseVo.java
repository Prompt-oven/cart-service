package com.promptove.cartservice.adapter.in.web.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseVo {
	private Long id;
	private String memberUuid;
	private String productUuid;
	private boolean selected;
	private boolean deleted;
	private LocalDateTime createdAt;
}