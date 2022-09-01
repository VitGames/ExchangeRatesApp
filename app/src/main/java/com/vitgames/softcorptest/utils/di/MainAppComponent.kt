package com.vitgames.softcorptest.utils.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.vitgames.softcorptest.data.api.ApiManager
import com.vitgames.softcorptest.data.data_base.LocalRateStorageImpl
import com.vitgames.softcorptest.data.data_base.RateMainDataBase
import com.vitgames.softcorptest.domain.LocalRateStorage
import com.vitgames.softcorptest.presentation.main_screen.MainActivity
import com.vitgames.softcorptest.presentation.main_screen.MainViewModel
import com.vitgames.softcorptest.presentation.favorite_screen.FavoriteFragment
import com.vitgames.softcorptest.presentation.popular_screen.PopularFragment
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Singleton
@Component(modules = [MainAppModule::class, ViewModelModule::class])
interface MainAppComponent {
    fun inject(target: MainActivity)
    fun inject(target: PopularFragment)
    fun inject(target: FavoriteFragment)
}

@Module
class MainAppModule(private val context: Context) {

    @Provides
    fun provideRetrofitApiInterface(): ApiManager.ApiInterface =
        ApiManager().getClient().create(ApiManager.ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideDataBase(): RateMainDataBase =
        Room.databaseBuilder(context, RateMainDataBase::class.java, "database").build()

    @Singleton
    @Provides
    fun provideLocalRateStorage(impl: LocalRateStorageImpl): LocalRateStorage = impl
}

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindEditPlaceSharedViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}