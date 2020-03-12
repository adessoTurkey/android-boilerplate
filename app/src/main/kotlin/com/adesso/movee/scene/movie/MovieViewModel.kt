package com.adesso.movee.scene.movie

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adesso.movee.base.BaseAndroidViewModel
import com.adesso.movee.domain.FetchPopularMoviesUseCase
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.MovieUiModel
import javax.inject.Inject
import kotlinx.coroutines.launch

class MovieViewModel @Inject constructor(
    application: Application,
    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
) : BaseAndroidViewModel(application) {

    private val _popularMovies = MutableLiveData<List<MovieUiModel>>()
    val popularMovies: LiveData<List<MovieUiModel>> get() = _popularMovies

    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies() {
        bgScope.launch {
            val popularMoviesResult = fetchPopularMoviesUseCase.run(UseCase.None)

            onUIThread {
                popularMoviesResult.either(::handleFailure, ::postPopularMovieList)
            }
        }
    }

    private fun postPopularMovieList(movies: List<MovieUiModel>) {
        _popularMovies.value = movies
    }
}
