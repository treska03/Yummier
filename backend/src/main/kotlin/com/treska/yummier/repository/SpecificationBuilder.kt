package com.treska.yummier.repository
import com.treska.yummier.common.Category
import com.treska.yummier.common.Difficulty
import com.treska.yummier.dto.recipe.RecipeFilter
import com.treska.yummier.model.Recipe
import com.treska.yummier.model.Review
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Subquery
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

                if (filter.minRatingAverage != null || filter.maxRatingAverage != null) {
                    val subquery: Subquery<Double> = query!!.subquery(Double::class.java)
                    val reviewRoot = subquery.from(Review::class.java)
                    subquery.select(criteriaBuilder.avg(reviewRoot.get<Int>("grade")))
                    subquery.where(criteriaBuilder.equal(reviewRoot.get<Recipe>("recipe"), root))

                    filter.minRatingAverage?.let { minRating ->
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(subquery, minRating.toDouble()))
                    }

                    filter.maxRatingAverage?.let { maxRating ->
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(subquery, maxRating.toDouble()))
                    }
                }

                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }
}