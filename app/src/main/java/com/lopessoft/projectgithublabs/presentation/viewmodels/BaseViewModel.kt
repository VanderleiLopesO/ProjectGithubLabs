package com.lopessoft.projectgithublabs.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(
    application: Application
) : AndroidViewModel(application) {

    open fun saveViewModelState() {
        //to be override
    }

}