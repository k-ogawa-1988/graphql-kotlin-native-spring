package com.example.application.controller.graphql.dataloader

import com.example.application.controller.graphql.type.Author
import com.example.application.controller.graphql.type.toApp
import com.example.domain.query.repository.AuthorRepository
import com.expediagroup.graphql.generator.scalars.ID
import graphql.GraphQLContext
import org.dataloader.DataLoader
import org.dataloader.Try
import org.springframework.stereotype.Component

@Component
class AuthorDataLoader(private val authorRepository: AuthorRepository) : CustomDataLoader<Author>() {
    companion object : DataLoaderCompanion<Author>()

    override fun getDataLoader(graphQLContext: GraphQLContext): DataLoader<ID, Author> {
        return super.getDataLoader(graphQLContext) { ids ->
            authorRepository.findByIds(ids.map { it.value.toLong() })
                .map { it.toApp() }
                .associateBy { it.id }
                .let { ids.map { id -> Try.succeeded(it[id]) } }
        }
    }
}
