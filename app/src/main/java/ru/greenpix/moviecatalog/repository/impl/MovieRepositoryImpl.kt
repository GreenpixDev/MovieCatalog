package ru.greenpix.moviecatalog.repository.impl

import ru.greenpix.moviecatalog.domain.*
import ru.greenpix.moviecatalog.repository.MovieRepository
import ru.greenpix.moviecatalog.retrofit.MovieApi
import java.time.LocalDateTime

class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getPage(pageNumber: Int): Page<Movie> {
        return movieApi.getPage(pageNumber).let { pageDto ->
            Page(
                pageSize = pageDto.pageInfo.pageSize,
                totalPageCount = pageDto.pageInfo.pageCount,
                pageNumber = pageDto.pageInfo.currentPage,
                content = pageDto.movies.map { movieDto ->
                    Movie(
                        id = movieDto.id,
                        name = movieDto.name,
                        poster = movieDto.poster,
                        year = movieDto.year,
                        country = movieDto.country,
                        genres = movieDto.genres
                            .mapNotNull { genre -> genre.name },
                        rating = movieDto.reviews
                            .map { review -> review.rating }
                            .average()
                    )
                }
            )
        }
    }

    override suspend fun getDetails(movieId: String): MovieDetails {
        return movieApi.getDetails(movieId).let { movieDto ->
            MovieDetails(
                id = movieDto.id,
                name = movieDto.name,
                poster = movieDto.poster,
                year = movieDto.year,
                country = movieDto.country,
                genres = movieDto.genres
                    .mapNotNull { genre -> genre.name },
                reviews = movieDto.reviews.map { reviewDto ->
                    Review(
                        id = reviewDto.id,
                        rating = reviewDto.rating,
                        reviewText = reviewDto.reviewText,
                        isAnonymous = reviewDto.isAnonymous,
                        createDateTime = LocalDateTime.parse(reviewDto.createDateTime),
                        author = reviewDto.author?.let { userDto ->
                            User(
                                userId = userDto.userId,
                                username = userDto.nickName,
                                avatar = userDto.avatar
                            )
                        }
                    )
                },
                time = movieDto.time,
                tagline = movieDto.tagline,
                description = movieDto.description,
                director = movieDto.director,
                budget = movieDto.budget,
                fees = movieDto.fees,
                ageLimit = movieDto.ageLimit
            )
        }
    }
}