package com.tmdb.movie.util.view

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun makeSnackAction(message: String, undoText: String, view: View, action: () -> Unit) =
    Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        .setAction(undoText) {
            action()
        }.show()
