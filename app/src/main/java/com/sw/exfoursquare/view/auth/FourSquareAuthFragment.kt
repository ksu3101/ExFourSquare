package com.sw.exfoursquare.view.auth

import com.sw.exfoursquare.R
import com.sw.exfoursquare.base.BaseFragment
import com.sw.model.domain.auth.FourSquareAuthState
import com.sw.model.domain.auth.FourSquareAuthVM
import org.koin.android.ext.android.inject

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthFragment : BaseFragment<FourSquareAuthState>() {

    val authFragVM: FourSquareAuthVM by inject()

    companion object {
        fun newInstance() = FourSquareAuthFragment()
    }

    override fun getLayoutId(): Int = R.layout.auth_fragment

}