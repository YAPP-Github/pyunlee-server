package com.yapp.cvs.support

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JsoupHandler {
    companion object {
        private const val TIMEOUT = 10000

        fun doFormPost(url: String, data: Map<String, String>): Document {
            var connection = Jsoup.connect(url)
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .timeout(TIMEOUT)

            data.forEach { entry -> connection = connection.data(entry.key, entry.value) }

            return connection.post()
        }
    }
}
