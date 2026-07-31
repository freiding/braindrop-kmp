package by.freiding.braindrop.core.common

sealed class AppException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class DatabaseException(message: String, cause: Throwable? = null) : AppException(message, cause)
    class NetworkException(message: String, cause: Throwable? = null) : AppException(message, cause)
    class UnknownException(cause: Throwable) : AppException(cause.message ?: "Unknown error", cause)
}
