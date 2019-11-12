package com.sw.common


/**
 * @author burkd
 * @since 2019-11-09
 */

const val IS_MOCK = false   // todo : 앱 내 에서 변경 할 수 있게 수정..

const val KOIN_CURRENT_ACTIVITY = "current_activity_key"

const val PREF_ACCESS_TOKEN_KEY = "pref_access_token"

const val QUALIFIER_FOURSQUARE_AUTH = "qualifier_foursquare_auth"

const val INTENT_REQUEST_AUTH_CODE = 900

private const val PACKAGE = "com.joelapenna.foursquared"
const val INTENT_RESULT_CODE = "$PACKAGE.fragments.OauthWebviewFragment.INTENT_RESULT_CODE"
const val INTENT_RESULT_ERROR = "$PACKAGE.fragments.OauthWebviewFragment.INTENT_RESULT_ERROR"
const val INTENT_RESULT_DENIED = "$PACKAGE.fragments.OauthWebviewFragment.INTENT_RESULT_DENIED"
const val INTENT_RESULT_ERROR_MESSAGE =
    "$PACKAGE.fragments.OauthWebviewFragment.INTENT_RESULT_ERROR_MESSAGE"