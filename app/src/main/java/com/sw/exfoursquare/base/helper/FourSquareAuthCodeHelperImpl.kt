package com.sw.exfoursquare.base.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.sw.model.base.helper.FourSquareAuthCodeHelper
import android.content.pm.PackageManager
import com.sw.common.INTENT_REQUEST_AUTH_CODE
import com.sw.exfoursquare.base.BaseActivity


/**
 * access token 을 얻기 위한 auth code 를 얻는 작업
 * (기존 샘플 코드 참고 하여 작업)
 *
 * https://github.com/foursquare/foursquare-android-oauth/blob/master/foursquare-oauth-library/src/main/java/com/foursquare/android/nativeoauth/FoursquareOAuth.java
 *
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthCodeHelperImpl(
    val activity: BaseActivity
) : FourSquareAuthCodeHelper {
    companion object {
        const val CLIENT_ID = "TJQTRCHD20Q324CH1JAK2R3FASAJ10R0BRBL51ZP20H4W2ZX"

        private const val URI_MARKET_PAGE = "market://details?id=com.joelapenna.foursquared"
        private const val MARKET_REFERRER = "utm_source=foursquare-android-oauth&utm_term=%s"

        private const val URI_SCHEME = "foursquareauth"
        private const val URI_AUTHORITY = "authorize"
        private const val PARAM_CLIENT_ID = "client_id"
        private const val PARAM_SIGNATURE = "androidKeyHash"
        private const val PARAM_VERSION = "v"
    }

    override fun connectToFourSquare() {
        val intent = getAuthCodeRetrieverIntent()
        if (isPlayStoreIntent(intent)) {
            activity.startActivity(intent)
        } else {
            activity.startActivityForResult(intent, INTENT_REQUEST_AUTH_CODE)
        }
    }

    private fun getAuthCodeRetrieverIntent(): Intent {
        val builder = Uri.Builder()
        builder.scheme(URI_SCHEME)
        builder.authority(URI_AUTHORITY)
        builder.appendQueryParameter(PARAM_CLIENT_ID, CLIENT_ID)
        builder.appendQueryParameter(PARAM_VERSION, "20130509")
        builder.appendQueryParameter(
            PARAM_SIGNATURE,
            "75:6A:05:40:44:A1:68:32:7B:2D:7F:F5:84:0F:C8:B8:C0:66:27:40"
        )

        val intent = Intent(Intent.ACTION_VIEW, builder.build())
        if (isIntentAvailable(intent)) {
            return intent
        }
        return getPlayStoreIntent()
    }

    private fun isIntentAvailable(intent: Intent): Boolean {
        val packageManager = activity.packageManager
        val resolveInfo = packageManager.queryIntentActivities(
            intent, PackageManager.MATCH_DEFAULT_ONLY
        )
        return resolveInfo.size > 0
    }

    private fun getPlayStoreIntent(): Intent {
        val referrer = String.format(MARKET_REFERRER, CLIENT_ID)
        return Intent(
            Intent.ACTION_VIEW,
            Uri.parse(URI_MARKET_PAGE)
                .buildUpon()
                .appendQueryParameter("referrer", referrer)
                .build()
        )
    }

    private fun isPlayStoreIntent(intent: Intent): Boolean {
        val marketUri = Uri.parse(URI_MARKET_PAGE)
        val uri = intent.data
        return (Intent.ACTION_VIEW == intent.action
                && marketUri.scheme == uri!!.scheme
                && marketUri.host == uri.host
                && marketUri.getQueryParameter("id") == uri.getQueryParameter("id"))
    }

}