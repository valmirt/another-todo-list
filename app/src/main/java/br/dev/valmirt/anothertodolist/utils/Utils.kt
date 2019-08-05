package br.dev.valmirt.anothertodolist.utils

import android.content.Context
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.model.Filter
import br.dev.valmirt.anothertodolist.model.Filter.*


fun String.toModelFilter (): Filter {
    return when (this) {
        "ACTIVE" -> ACTIVE
        "COMPLETED" -> COMPLETED
        else -> ALL
    }
}

fun Filter.toTranslatedString(context: Context?): String {
    return when(this) {
        ALL -> context?.getString(R.string.all_string) ?: "All"
        ACTIVE -> context?.getString(R.string.active_string) ?: "Active"
        COMPLETED -> context?.getString(R.string.completed_string) ?: "Completed"
    }
}
