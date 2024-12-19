package com.example.application.controller.graphql.dataloader

import com.expediagroup.graphql.dataloader.KotlinDataLoader
import com.expediagroup.graphql.generator.extensions.get
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.extensions.getValueFromDataLoader
import com.expediagroup.graphql.server.extensions.getValuesFromDataLoader
import graphql.GraphQLContext
import graphql.schema.DataFetchingEnvironment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import org.dataloader.DataLoader
import org.dataloader.DataLoaderFactory
import org.dataloader.Try
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.EmptyCoroutineContext

abstract class CustomDataLoader<V> : KotlinDataLoader<ID, V> {
    override val dataLoaderName: String = this::class.simpleName ?: "UnknownDataLoader"

    fun getDataLoader(
        graphQLContext: GraphQLContext,
        block: suspend CoroutineScope.(List<ID>) -> List<Try<V>>,
    ): DataLoader<ID, V> = DataLoaderFactory.newDataLoaderWithTry { ids, ble ->
        val coroutineScope = ble.getContext<GraphQLContext>()?.get<CoroutineScope>()
            ?: CoroutineScope(EmptyCoroutineContext)

        coroutineScope.future {
            block(this, ids)
        }
    }
}

abstract class DataLoaderCompanion<V> {
    private val dataLoaderName = this::class.java.enclosingClass?.simpleName ?: "UnknownDataLoader"

    fun getValue(dfe: DataFetchingEnvironment, id: ID): CompletableFuture<V> =
        dfe.getValueFromDataLoader(dataLoaderName, id)

    fun getValues(dfe: DataFetchingEnvironment, ids: List<ID>): CompletableFuture<List<V>> =
        dfe.getValuesFromDataLoader(dataLoaderName, ids)

    fun getValueOrNull(dfe: DataFetchingEnvironment, id: ID?): CompletableFuture<V?> =
        id?.let { dfe.getValueFromDataLoader(dataLoaderName, it) } ?: CompletableFuture.supplyAsync { null }
}
