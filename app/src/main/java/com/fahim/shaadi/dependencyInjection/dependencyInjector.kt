package com.fahim.shaadi.dependencyInjection

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.fahim.shaadi.R
import dagger.hilt.android.qualifiers.ApplicationContext

class dependencyInjector(applicationContext: Context) {

    val glide: RequestManager by lazy {
        injectGlide(context = applicationContext)
    }
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )

}