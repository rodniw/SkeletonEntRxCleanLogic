package dev.rodni.ru.domain.interactor.auth

import dev.rodni.ru.domain.executor.PostExecutionThread
import dev.rodni.ru.domain.repository.AuthRepository
import javax.inject.Inject

//todo create models
open class Login @Inject constructor(
    private val authRepository: AuthRepository,
    postExecutionThread: PostExecutionThread
) {
}