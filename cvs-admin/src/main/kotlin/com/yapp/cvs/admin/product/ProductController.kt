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
        @ModelAttribute searchForm: ProductSearchFormDTO,
        model: Model
    ): String{
        val result = productProcessor.searchProductPage(searchForm.toPageSearchVO(), searchForm.toVO())
        model.addAttribute("searchForm", searchForm)
        model.addAttribute("result", result)
        return "product/list"
    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable productId: Long,
        model: Model
    ): String{
        val vo = productProcessor.getProductDetail(productId, 1L)
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