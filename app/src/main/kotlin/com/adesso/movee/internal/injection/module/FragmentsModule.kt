package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.injection.scope.MovieScope
import com.adesso.movee.scene.moviedetail.MovieDetailFragment
import com.adesso.movee.scene.movielist.MovieListFragment
import com.adesso.movee.scene.movielist.MovieListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentsModule {

    @MovieScope
    @ContributesAndroidInjector(modules = [MovieListModule::class])
    abstract fun contributeMovieListFragment(): MovieListFragment

    @MovieScope
    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment
}
