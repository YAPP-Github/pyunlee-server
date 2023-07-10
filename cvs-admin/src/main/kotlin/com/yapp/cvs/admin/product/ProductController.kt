package com.yapp.cvs.admin.product

import com.yapp.cvs.admin.product.dto.ProductSearchFormDTO
import com.yapp.cvs.admin.product.dto.ProductUpdateRequestDTO
import com.yapp.cvs.domain.product.application.ProductProcessor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/product")
class ProductController(
    private val productProcessor: ProductProcessor
) {

    @GetMapping("/list")
    fun getProductList(
        searchFormDto: ProductSearchFormDTO,
        model: Model
    ): String{
        val result = productProcessor.searchProductPage(searchFormDto.toPageSearchVO(), searchFormDto.toVO())
        model.addAttribute("result", result)
        return "product/list"
    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable productId: Long,
        model: Model
    ): String{
        val vo = productProcessor.getProductDetail(productId)
        model.addAttribute("contents", vo)
        return "product/detail"
    }

    @PutMapping("/{productId}")
    @ResponseBody
    fun updateProductDetail(
        @PathVariable productId: Long,
        @RequestBody productUpdateRequestDTO: ProductUpdateRequestDTO
    ) {
        productProcessor.updateProduct(productId, productUpdateRequestDTO.toVO())
    }
}