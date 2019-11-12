package com.sw.exfoursquare.view

import android.os.Bundle
import com.sw.common.extensions.replaceFragment
import com.sw.exfoursquare.R
import com.sw.exfoursquare.base.BaseActivity

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.auth_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        replaceFragment(
            replaceFragment = FourSquareAuthFragment.newInstance(),
            containerResId = R.id.auth_activity_frag_container
        )
    }

}