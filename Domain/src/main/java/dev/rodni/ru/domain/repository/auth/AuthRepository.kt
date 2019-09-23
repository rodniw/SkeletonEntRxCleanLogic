package dev.rodni.ru.domain.repository.auth

interface AuthRepository {
    //todo change params
    fun login(login: String, password: String)
    fun register(login: String, email: String, password: String)
    fun requestPasswordRecovery(login: String)
    fun sendConfirmCode(code: String)
    fun setNewPassword(password: String)
}