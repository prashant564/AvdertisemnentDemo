package com.example.advertisement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_adaptive_banner_ad.*

class AdaptiveBannerAd : AppCompatActivity() {

    private lateinit var adView: AdView

    private var initialLayoutComplete = false
    // Determine the screen width (less decorations) to use for the ad width.
    // If the ad hasn't been laid out, default to the full screen width.
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = ad_view_container.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationBannerAdSizeWithWidth(this, adWidth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_banner_ad)

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) { }

        adView = AdView(this)
        ad_view_container.addView(adView)
        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        ad_view_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
    }
    /** Called when leaving the activity  */
    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    /** Called when returning to the activity  */
    public override fun onResume() {
        super.onResume()
        adView.resume()
    }

    /** Called before the activity is destroyed  */
    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }

    private fun loadBanner() {
        adView.adUnitId = AD_UNIT_ID

        adView.adSize = adSize

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        val adRequest = AdRequest.Builder().addTestDevice("223248A0E7A58F69D554312F6BF78A85").build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    companion object {
        // This is an ad unit ID for a test ad. Replace with your own banner ad unit ID.
        private val AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"
    }
}
