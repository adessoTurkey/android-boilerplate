package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.injection.scope.MovieScope
import com.adesso.movee.scene.movie.MovieFragment
import com.adesso.movee.scene.movie.MovieModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class FragmentsModule {

    @MovieScope
    @ContributesAndroidInjector(modules = [MovieModule::class])
    abstract fun contributeMovieFragment(): MovieFragment
}
