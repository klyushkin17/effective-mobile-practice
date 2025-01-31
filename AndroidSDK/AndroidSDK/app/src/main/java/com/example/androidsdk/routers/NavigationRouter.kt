package com.example.androidsdk.routers

import androidx.fragment.app.Fragment

interface NavigationRouter {
    fun navigateTo(fragment: Fragment)
}