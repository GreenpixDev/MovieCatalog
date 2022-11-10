package ru.greenpix.moviecatalog.ui.screen.movie.mapper

import ru.greenpix.moviecatalog.domain.Review
import ru.greenpix.moviecatalog.mapper.Mapper
import ru.greenpix.moviecatalog.ui.screen.movie.model.MovieReviewModel
import java.time.format.DateTimeFormatter

class MovieReviewMapper : Mapper<Review, MovieReviewModel> {

    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }

    override fun map(source: Review): MovieReviewModel = MovieReviewModel(
        id = source.id,
        author = source.author?.username ?: "",
        avatarUrl = source.author?.avatarUrl ?: "",
        comment = source.reviewText ?: "",
        anonymous = source.isAnonymous,
        date = source.createDateTime
            .toLocalDate()
            .format(FORMATTER),
        rating = source.rating
    )
}