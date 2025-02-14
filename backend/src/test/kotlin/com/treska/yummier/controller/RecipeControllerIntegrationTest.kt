package com.treska.yummier.controller


import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

const val BASE_URL = "/api/v1/recipes"

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Nested
    inner class Positive {
        @Test
        fun `should create and retrieve a recipe`() {
            val jsonPayload = objectMapper.writeValueAsString(correctPostRequestBody)

            val postResult = mockMvc.post(BASE_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = jsonPayload
            }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.title", equalTo("Test Recipe"))
                }
                .andReturn()

            val postResponse = postResult.response.contentAsString
            val jsonNode = objectMapper.readTree(postResponse)
            val recipeId = jsonNode.get("id").asLong()

            mockMvc.get("$BASE_URL/{id}", recipeId)
                .andExpect {
                    status { isOk() }
                    jsonPath("$.id", equalTo(recipeId.toInt()))
                    jsonPath("$.title", equalTo("Test Recipe"))
                }
        }

        @Test
        fun `should return paginated recipes`() {
            mockMvc.get(BASE_URL) {
                param("page", "0")
                param("size", "10")
            }
                .andExpect {
                    status { isOk() }
                }
        }

        @Test
        fun `should delete a recipe and return not found on subsequent retrieval`() {
            val jsonPayload = objectMapper.writeValueAsString(correctPostRequestBody)

            val postResult = mockMvc.post(BASE_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = jsonPayload
            }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.title", equalTo("Test Recipe"))
                }
                .andReturn()

            val postResponse = postResult.response.contentAsString
            val jsonNode = objectMapper.readTree(postResponse)
            val recipeId = jsonNode.get("id").asLong()

            mockMvc.delete("$BASE_URL/{id}", recipeId)
                .andExpect {
                    status { isOk() }
                }

            mockMvc.get("$BASE_URL/{id}", recipeId)
                .andExpect {
                    status { isNotFound() }
                }
        }

        @Test
        fun `pagination should work`() {
            for (i in 1..15) {
                val jsonPayload = objectMapper.writeValueAsString(correctPostRequestBody + ("title" to "#$i: ${correctPostRequestBody.get("title")}"))

                mockMvc.post(BASE_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = jsonPayload
                }
                    .andExpect {
                        status { isOk() }
                    }
                    .andReturn()
            }
            mockMvc.get(BASE_URL) {
                param("page", "1")
                param("size", "10")
            }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.content.size()", equalTo(5))
                }
        }


        @Test
        fun `filtering by review average should work`() {
            for (i in 1..3) {
                val jsonPayload = objectMapper.writeValueAsString(correctPostRequestBody + ("title" to "#$i: ${correctPostRequestBody.get("title")}"))

                mockMvc.post(BASE_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = jsonPayload
                }
                    .andExpect {
                        status { isOk() }
                    }
                    .andReturn()
            }

            for (i in 1..5) {
                val jsonPayload = objectMapper.writeValueAsString(correctPostReviewBody + ("grade" to i))

                mockMvc.post("$BASE_URL/{recipeId}/reviews", 1) {
                    contentType = MediaType.APPLICATION_JSON
                    content = jsonPayload
                }
                    .andExpect {
                        status { isOk() }
                    }
            }

            mockMvc.get(BASE_URL) {
                param("minRatingAverage", "2")
                param("maxRatingAverage", "5")
            }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.content.size()", equalTo(1))
                }
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class Negative {
        @Test
        fun `should return not found for non-existing recipe`() {
            val nonExistingId = 999_999_999
            mockMvc.get("$BASE_URL/{id}", nonExistingId)
                .andExpect {
                    status { isNotFound() }
                }
        }

        @ParameterizedTest
        @MethodSource("provideIncorrectValues")
        fun `should not create and retrieve a recipe with incorrect values`(incorrectInput: Map<String, Any>) {
            val jsonPayload = objectMapper.writeValueAsString(incorrectInput)

            mockMvc.post(BASE_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = jsonPayload
            }
                .andExpect {
                    status { isBadRequest() }
                }
                .andReturn()
        }

        private fun provideIncorrectValues(): List<Map<String, Any>> {
            return incorrectPostRequestBodyList
        }

        private val incorrectPostRequestBodyList = listOf(
            correctPostRequestBody + ("timeNeeded" to "incorrectValueThatIsNaN"),
            correctPostRequestBody + ("difficulty" to "NOT_DIFFICULTY_VALUE"),
            correctPostRequestBody + ("category" to "NOT_CATEGORY_VALUE"),
            correctPostRequestBody + ("ingredients" to "singleIngredient"),
        )
    }

    private val correctPostRequestBody = mapOf(
        "title" to "Test Recipe",
        "description" to "A test recipe description",
        "timeNeeded" to 30,
        "difficulty" to "EASY",
        "category" to "LUNCH",
        "ingredients" to listOf("Ingredient 1", "Ingredient 2"),
        "instructions" to listOf("Step 1", "Step 2")
    )

    private val correctPostReviewBody = mapOf(
        "recipeId" to 1,
        "content" to "Some review",
        "grade" to 3
    )
}