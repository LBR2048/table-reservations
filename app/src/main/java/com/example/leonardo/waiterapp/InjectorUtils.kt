package com.example.leonardo.waiterapp

import android.app.Application
import android.content.Context
import com.example.leonardo.waiterapp.data.AppDatabase
import com.example.leonardo.waiterapp.data.Webservice
import com.example.leonardo.waiterapp.data.customers.CustomersRepository
import com.example.leonardo.waiterapp.data.tables.TablesRepository
import com.example.leonardo.waiterapp.ui.customers.CustomersViewModelFactory
import com.example.leonardo.waiterapp.ui.tables.TablesViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit

object InjectorUtils {

    fun provideCustomerViewModelFactory(
            application: Application
    ): CustomersViewModelFactory {
        return CustomersViewModelFactory(application, getCustomersRepository(application))
    }

    fun provideTablesViewModelFactory(
            application: Application
    ): TablesViewModelFactory {
        return TablesViewModelFactory(application, getTablesRepository(application))
    }

    private fun getCustomersRepository(context: Context) : CustomersRepository {
        return CustomersRepository(AppDatabase.getInstance(context), getWebservice())
    }

    private fun getTablesRepository(context: Context) : TablesRepository {
        return TablesRepository(AppDatabase.getInstance(context), getWebservice())
    }

    private fun getWebservice(): Webservice {
        val baseUrl = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/"
        val builder = OkHttpClient.Builder()
        val client = builder.build()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        return retrofit.create(Webservice::class.java)
    }

}