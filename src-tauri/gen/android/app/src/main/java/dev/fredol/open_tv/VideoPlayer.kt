package dev.fredol.open_tv

import android.content.Context
import android.content.Intent
import android.net.Uri

object VideoPlayer {
    /**
     * Opens a video URL in an external player with a chooser dialog
     * so users can pick their preferred player (VLC, MX Player, default, etc.)
     */
    @JvmStatic
    fun openVideo(context: Context, url: String, title: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(url), "video/*")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // Add the title as extra for players that support it
            putExtra("title", title)
            putExtra(Intent.EXTRA_TITLE, title)
        }

        // Create chooser so user can pick their preferred player
        val chooser = Intent.createChooser(intent, "Open with...").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(chooser)
        } catch (e: Exception) {
            // If chooser fails, try direct intent
            try {
                context.startActivity(intent)
            } catch (e2: Exception) {
                Logger.error("Failed to open video: ${e2.message}")
            }
        }
    }

    /**
     * Opens a video URL with specific MIME type for HLS/MPEG-TS streams
     */
    @JvmStatic
    fun openStream(context: Context, url: String, title: String) {
        // Determine MIME type based on URL
        val mimeType = when {
            url.contains(".m3u8") -> "application/x-mpegURL"
            url.contains(".ts") -> "video/mp2t"
            url.contains(".mp4") -> "video/mp4"
            url.contains(".mkv") -> "video/x-matroska"
            else -> "video/*"
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(url), mimeType)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra("title", title)
            putExtra(Intent.EXTRA_TITLE, title)
        }

        val chooser = Intent.createChooser(intent, "Open with...").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(chooser)
        } catch (e: Exception) {
            // Fallback to generic video type
            openVideo(context, url, title)
        }
    }
}
