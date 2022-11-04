package ru.greenpix.moviecatalog.usecase.model

sealed class ValidationResult {

    object Successful: ValidationResult()

    sealed class Failed : ValidationResult() {

        object Login: Failed()
        object Email: Failed()
        object Url: Failed()
        object Birthday: Failed()

        sealed class Password : ValidationResult() {
            object MinLength: Password()
        }
    }
}
