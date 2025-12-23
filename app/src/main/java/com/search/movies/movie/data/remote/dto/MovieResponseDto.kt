package com.search.movies.movie.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto(
    @SerialName("results") val result: List<MovieDto>
)
