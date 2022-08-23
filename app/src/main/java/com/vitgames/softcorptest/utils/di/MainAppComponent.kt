package com.vitgames.softcorptest.utils.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.vitgames.softcorptest.data.api.ApiManager
import com.vitgames.softcorptest.data.data_base.LocalRateStorageImpl
import com.vitgames.softcorptest.data.data_base.RateMainDataBase
import com.vitgames.softcorptest.domain.LocalRateStorage
import com.vitgames.softcorptest.presentation.MainActivity
import com.vitgames.softcorptest.presentation.PopularFragment
import com.vitgames.softcorptest.presentation.SharedViewModel
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
    @ViewModelKey(SharedViewModel::class)
    internal abstract fun bindEditPlaceSharedViewModel(viewModel: SharedViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}