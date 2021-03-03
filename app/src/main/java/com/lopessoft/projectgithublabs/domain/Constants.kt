package com.lopessoft.projectgithublabs.domain

object Constants {
    const val API_BASE_URL = "https://api.github.com/"
    const val JAVA = "language:Java"
    const val STARS = "stars"
}

sealed class ApiParameters(val value: String)
object JavaLanguage : ApiParameters(Constants.JAVA)
object StarsSort : ApiParameters(Constants.STARS)