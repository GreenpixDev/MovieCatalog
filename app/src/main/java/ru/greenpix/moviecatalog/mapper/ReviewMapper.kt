package ru.greenpix.moviecatalog.mapper

import ru.greenpix.moviecatalog.domain.Review
import ru.greenpix.moviecatalog.dto.ReviewDto
import java.time.LocalDateTime

class ReviewMapper(
    private val userMapper: UserMapper
) : Mapper<ReviewDto, Review> {

    override fun map(source: ReviewDto): Review = Review(
        id = source.id,
        rating = source.rating,
        reviewText = source.reviewText,
        isAnonymous = source.isAnonymous,
        createDateTime = LocalDateTime.parse(source.createDateTime),
        author = source.author?.let { userMapper.map(it) }
    )
}