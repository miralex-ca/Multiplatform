package com.muralex.multiplatform.android.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.muralex.multiplatform.android.R


object ContactActions {

    fun shareApp(context: Context) {
        ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setChooserTitle(context.getString(R.string.text_share))
            .setText(context.getString(R.string.text_share_app))
            .startChooser()
    }

    fun shareURL(context: Context, url: String) {
        ShareCompat.IntentBuilder(context)
            .setType("text/plain")
            .setChooserTitle(context.getString(R.string.text_share))
            .setText(context.getString(R.string.share_url) + url)
            .startChooser()
    }

    fun openUrl(context: Context, url: String) {
        try {
            val webpage = Uri.parse(url)
            val myIntent = Intent(Intent.ACTION_VIEW, webpage)
            context.startActivity(myIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, context.getString(R.string.no_mail_client), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

}