package br.com.fiap.di

import android.content.Context
import br.com.fiap.api.AuthInterceptor
import br.com.fiap.api.PokemonService
import br.com.fiap.repository.PokemonRepository
import br.com.fiap.repository.PokemonRepositoryImpl
import br.com.fiap.utils.URLProvider
import br.com.fiap.view.form.FormPokemonViewModel
import br.com.fiap.view.list.ListPokemonsViewModel
import br.com.fiap.view.splash.SplashViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module{
    viewModel {SplashViewModel(get())}
    viewModel { ListPokemonsViewModel(get()) }
    viewModel { FormPokemonViewModel(get()) }
}

val repositoryModule = module{
    single <PokemonRepository> { PokemonRepositoryImpl(get())  }
}

val networkModule = module{
    single <Interceptor> { AuthInterceptor() }
    single { createOkhttpClientAuth(get()) }

    single { createNetworkClient(get(),
        get(named("baseURL"))
        ).create(PokemonService::class.java) }

    single { createPicassoAuth(get(), get()) }
    single (named("baseURL")){ URLProvider.baseURL }


}


private fun createPicassoAuth(context: Context, okHttpClient: OkHttpClient): Picasso {
    return Picasso
        .Builder(context)
        .downloader(OkHttp3Downloader(okHttpClient))
        .build()
}

private fun createNetworkClient(okHttpClient: OkHttpClient, baseUrl:String): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createOkhttpClientAuth(authInterceptor: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}
