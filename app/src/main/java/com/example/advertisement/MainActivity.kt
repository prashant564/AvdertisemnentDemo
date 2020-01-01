package com.example.advertisement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        btn_banner_ad.setOnClickListener {
            startActivity(Intent(this, BannerAd::class.java))
        }
        btn_interstital_ad.setOnClickListener {
            if (mInterstitialAd.isLoaded) {
                mInterstitialAd.show()
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }
        btn_native_advanced_ad.setOnClickListener {
            startActivity(Intent(this, NativeAdvancedAdActivity::class.java))
        }
        btn_adaptive_banner_ad.setOnClickListener {
            startActivity(Intent(this, AdaptiveBannerAd::class.java))
        }
        btn_reward_ad.setOnClickListener {
            startActivity(Intent(this, RewardVideo::class.java))
        }
        //reload an interstital ad
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }

        }
    }
}
