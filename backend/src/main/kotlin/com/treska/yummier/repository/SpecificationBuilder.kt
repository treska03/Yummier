package com.treska.yummier.repository
import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty
import com.treska.yummier.dto.RecipeFilter
import com.treska.yummier.model.Recipe
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

class SpecificationBuilder {
    companion object {
        fun withFilters(filter: RecipeFilter): Specification<Recipe> {
            return Specification { root, query, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()

                filter.title?.let {
                    predicates.add(criteriaBuilder.like(root.get<String>("title"), "%$it%"))
                }

                filter.description?.let {
                    predicates.add(criteriaBuilder.like(root.get<String>("description"), "%$it%"))
                }

                filter.minTimeNeeded?.let {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timeNeeded"), it))
                }

                filter.maxTimeNeeded?.let {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("timeNeeded"), it))
                }

                filter.difficulty?.let {
                    predicates.add(criteriaBuilder.equal(root.get<Difficulty>("difficulty"), it))
                }

                filter.category?.let {
                    predicates.add(criteriaBuilder.equal(root.get<Category>("category"), it))
                }

                filter.minRatingAverage?.let {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ratingAverage"), it))
                }

                filter.maxRatingAverage?.let {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("ratingAverage"), it))
                }

                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }
}