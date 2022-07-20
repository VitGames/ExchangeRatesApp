package com.vitgames.softcorptest.main_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vitgames.softcorptest.RateDataInteractor
import com.vitgames.softcorptest.RateDataInteractorImpl
import com.vitgames.softcorptest.popular.PopularFragment
import com.vitgames.softcorptest.popular.PopularViewModel
import com.vitgames.softcorptest.sort.SortedFragment
import com.vitgames.softcorptest.sort.SortedViewModel
import com.vitgames.softcorptest.utils.ViewModelFactory
import com.vitgames.softcorptest.utils.ViewModelKey
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Component(modules = [MainAppModule::class, ViewModelModule::class])
interface MainAppComponent{
    fun inject(target: MainActivity)
    fun inject(target: PopularFragment)
    fun inject(target: SortedFragment)
}

@Module
class MainAppModule{
    
    @Singleton
    @Provides
    fun provideRateDataInteractor(impl: RateDataInteractorImpl): RateDataInteractor = impl

}

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    internal abstract fun bindEditPlacePopularViewModel(viewModel: PopularViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SortedViewModel::class)
    internal abstract fun bindEditPlaceSortedViewModel(viewModel: SortedViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}