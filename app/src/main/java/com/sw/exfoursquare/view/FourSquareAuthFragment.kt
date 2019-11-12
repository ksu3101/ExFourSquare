package com.sw.exfoursquare.view

import com.sw.exfoursquare.R
import com.sw.exfoursquare.base.BaseFragment
import com.sw.model.domain.auth.FourSquareAuthState

/**
 * @author burkd
 * @since 2019-11-13
 */
class FourSquareAuthFragment : BaseFragment<FourSquareAuthState>() {

    companion object {
        fun newInstance() = FourSquareAuthFragment()
    }

    override fun getLayoutId(): Int = R.layout.auth_fragment

}