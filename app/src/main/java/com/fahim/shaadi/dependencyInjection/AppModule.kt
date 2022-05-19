package com.fahim.shaadi.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.fahim.bookapptesting.util.Constant
import com.fahim.shaadi.R
import com.fahim.shaadi.api.RetrofitAPI
import com.fahim.shaadi.data.database.AppDatabase
import com.fahim.shaadi.data.database.ProfileDAO
import com.fahim.shaadi.data.repository.ProfileInterface
import com.fahim.shaadi.data.repository.ProfileRepository
import com.fahim.shaadi.ui.dashboard.DeckRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "appDb").build()

    @Singleton
    @Provides
    fun injectProfileDao(database: AppDatabase) = database.profileDao();


    @Singleton
    @Provides
    fun injectRetrofitAPI(): RetrofitAPI {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ProfileDAO, api: RetrofitAPI) =
        ProfileRepository(dao, api) as ProfileInterface

    @Singleton
    @Provides
    fun injectDeckAdapter(glide: RequestManager) =
        DeckRecyclerAdapter(glide)

}