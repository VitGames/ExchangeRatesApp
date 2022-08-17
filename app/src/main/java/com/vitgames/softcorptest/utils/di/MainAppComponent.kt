package com.vitgames.softcorptest.utils.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vitgames.softcorptest.data.SharedPreferenceStorage
import com.vitgames.softcorptest.data.SharedPreferenceStorageImpl
import com.vitgames.softcorptest.data.api.ApiManager
import com.vitgames.softcorptest.presentation.*
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
}

@Module
class MainAppModule{

    @Provides
    fun provideRetrofitApiInterface(): ApiManager.ApiInterface = ApiManager().getClient().create(ApiManager.ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideSharedPreferenceStorage(impl: SharedPreferenceStorageImpl): SharedPreferenceStorage = impl

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