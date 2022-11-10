package ru.greenpix.moviecatalog.usecase.model

/**
 * Класс, описывающий результат валидации.
 * Используется в [ru.greenpix.moviecatalog.usecase.ValidationUseCase]
 */
sealed class ValidationResult {

    /**
     * Объект, описывающий успешный результат валидации
     */
    object Successful: ValidationResult()

    /**
     * Класс, описывающий проваленный результат валидации
     */
    sealed class Failed : ValidationResult() {

        /**
         * Объект, описывающий проваленный результат валидации уникального имени пользователя
         */
        object Login: Failed()

        /**
         * Объект, описывающий проваленный результат валидации электронной почты
         */
        object Email: Failed()

        /**
         * Объект, описывающий проваленный результат валидации ссылки
         */
        object Url: Failed()

        /**
         * Объект, описывающий проваленный результат валидации даты рождения
         */
        object Birthday: Failed()

        /**
         * Класс, описывающий проваленный результат валидации пароля
         */
        sealed class Password : ValidationResult() {

            /**
             * Объект, описывающий проваленный результат валидации пароля.
             * Причина провала: пароль слишком маленький.
             */
            object MinLength: Password()
        }
    }
}
