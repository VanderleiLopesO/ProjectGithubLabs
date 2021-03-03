package com.lopessoft.projectgithublabs.domain

import java.io.Serializable

sealed class RequestStatus : Serializable
object None: RequestStatus()
object Loading: RequestStatus()
object Loaded: RequestStatus()
object Error : RequestStatus()