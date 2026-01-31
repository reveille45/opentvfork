package dev.fredol.open_tv

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge

class MainActivity : TauriActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
  }

  override fun onWebViewCreate(webView: WebView) {
    super.onWebViewCreate(webView)
    // Add JavaScript interface for video playback
    webView.addJavascriptInterface(VideoPlayerBridge(this), "AndroidVideoPlayer")
  }
}

/**
 * JavaScript interface to allow the web app to launch videos in external players
 */
class VideoPlayerBridge(private val activity: MainActivity) {
  @JavascriptInterface
  fun openVideo(url: String, title: String) {
    activity.runOnUiThread {
      VideoPlayer.openStream(activity, url, title)
    }
  }

  @JavascriptInterface
  fun isAndroid(): Boolean {
    return true
  }
}
