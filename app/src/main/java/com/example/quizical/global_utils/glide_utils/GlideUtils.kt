package com.example.quizical.global_utils.glide_utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

fun ImageView.loadImage(
    imageUrl: String?,
    onError: (e: GlideException?) -> Unit = {},
    onLoaded: (res: Drawable) -> Unit = {},
) {
    if (imageUrl == null)
        return
    if (imageUrl.isEmpty())
        return
    Glide.with(context.applicationContext)
        .load(imageUrl)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>,
                isFirstResource: Boolean,
            ): Boolean {
                onError(e)
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>?,
                dataSource: DataSource,
                isFirstResource: Boolean,
            ): Boolean {
                onLoaded(resource)
                return false
            }

        })
        .into(this)
    imageTintList = null
}

fun Context.loadImage(
    imageUrl: String?,
    onError: (e: GlideException?) -> Unit = {},
    onLoaded: (bitmap: Bitmap) -> Unit = {},
) {
    if (imageUrl == null)
        return
    if (imageUrl.isEmpty())
        return
    Glide.with(applicationContext)
        .asBitmap()
        .load(imageUrl)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>,
                isFirstResource: Boolean,
            ): Boolean {
                onError(e)
                return false
            }

            override fun onResourceReady(
                resource: Bitmap,
                model: Any,
                target: Target<Bitmap>?,
                dataSource: DataSource,
                isFirstResource: Boolean,
            ): Boolean {
                return false
            }

        })
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                onLoaded(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })
}


@Suppress("UNCHECKED_CAST")
fun <T> Context.loadImage(
    imageUrl: String?,
    type: Class<T>,
    onError: (e: GlideException?) -> Unit = {},
    onLoaded: (resource: T) -> Unit = {},
) {
    if (imageUrl == null)
        return
    if (imageUrl.isEmpty())
        return
    Glide.with(applicationContext)
        .asBitmap()
        .load(imageUrl)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>,
                isFirstResource: Boolean,
            ): Boolean {
                onError(e)
                return false
            }

            override fun onResourceReady(
                resource: Bitmap,
                model: Any,
                target: Target<Bitmap>?,
                dataSource: DataSource,
                isFirstResource: Boolean,
            ): Boolean {
                return false
            }

        })
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                if (BitmapDrawable::class.java.isAssignableFrom(type)) {
                    val bitmapDrawable = BitmapDrawable(this@loadImage.resources, resource)
                    onLoaded(bitmapDrawable as T)
                } else {
                    onLoaded(resource as T)
                }

            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })
}
