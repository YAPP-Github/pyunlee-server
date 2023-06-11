package com.yapp.cvs.support

import org.springframework.http.MediaType
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.nio.charset.StandardCharsets

class RestTemplateHandler {
    companion object {
        private val restTemplate: RestTemplate = createRestTemplate()

        fun doGet(url: String, params: Map<String, Any?>): String? {
            val urlString = UriComponentsBuilder.fromHttpUrl(url).apply {
                params.forEach {
                    queryParam(it.key, it.value.toString())
                }
            }.build().toString()
            return restTemplate.getForEntity(urlString, String::class.java).body
        }

        private fun createRestTemplate(): RestTemplate {
            val restTemplate = RestTemplate()
            restTemplate.messageConverters.filterIsInstance(StringHttpMessageConverter::class.java)
                .forEach { converter ->
                    converter.defaultCharset = StandardCharsets.UTF_8
                    converter.supportedMediaTypes = listOf(MediaType.ALL)
                }
            return restTemplate
        }
    }
}
