package com.yapp.cvs.api.product

import com.yapp.cvs.api.common.dto.OffsetPageDTO
import com.yapp.cvs.api.product.dto.ProductDTO
import com.yapp.cvs.api.product.dto.ProductDetailDTO
import com.yapp.cvs.api.product.dto.ProductSearchDTO
import com.yapp.cvs.configuration.swagger.SwaggerConfig
import com.yapp.cvs.configuration.swagger.SwaggerConfig.Companion.SWAGGER_AUTH_KEY
import com.yapp.cvs.domain.member.entity.Member
import com.yapp.cvs.domain.product.application.ProductProcessor
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@Tag(name = "상품")
@RestController
@RequestMapping("/api/v1/product")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class ProductController(
    private val productProcessor: ProductProcessor
) {
    @GetMapping("/{productId}/detail")
    fun getProductDetail(
        @Parameter(hidden = true) member: Member,
        @PathVariable productId: Long
    ): ProductDetailDTO {
        return ProductDetailDTO.from(productProcessor.getProductDetail(productId, member))
    }

    @GetMapping("/search")
    fun searchProductList(@ParameterObject productSearchDTO: ProductSearchDTO): OffsetPageDTO<ProductDTO> {
        val result = productProcessor.searchProductPageList(productSearchDTO.toOffsetVO(), productSearchDTO.toVO())
        return OffsetPageDTO(result.lastId, result.content.map { ProductDTO.from(it) })
    }

    @GetMapping("/unrated")
    fun getUnratedProductList(
        @Parameter(hidden = true) member: Member,
        @RequestParam offsetProductId: Long? = null,
        @RequestParam(defaultValue = "10") pageSize: Int,
    ): OffsetPageDTO<ProductDTO>  {
        val result = productProcessor.getUnratedProductList(member, offsetProductId, pageSize)
        return OffsetPageDTO(result.lastId, result.content.map { ProductDTO.from(it) })
    }
}
