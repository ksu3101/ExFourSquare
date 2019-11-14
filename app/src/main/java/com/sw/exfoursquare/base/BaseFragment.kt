package com.sw.exfoursquare.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.sw.exfoursquare.BR
import com.sw.model.base.BaseLifecycleOwnViewModel
import com.sw.model.base.helper.NavigationHelper
import com.sw.model.base.exts.rx.isStateType
import com.sw.model.base.redux.State
import com.sw.model.domain.AppStore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

/**
 * @author ksu3101
 * @since 2019-10-18
 */
abstract class BaseFragment<S : State> : Fragment() {
    protected val stateStore: AppStore by inject()
    protected val vm: BaseLifecycleOwnViewModel<S> by inject()
    protected val navigationHelper: NavigationHelper<S> by inject()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var binder : ViewDataBinding

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        compositeDisposable.clear()
        compositeDisposable.add(
                stateStore.getStateListener()
                        .flatMap { Observable.fromIterable(it.states.values) }
                        .isStateType()
                        .distinctUntilChanged()
                        .subscribe {
                            if (it as? S == null) throw IllegalStateException("$it is not available states.")
                            if (!vm.render(it)) {
                                navigationHelper.handle(it)
                            }
                        }
        )

        binder = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binder.setVariable(BR.vm, vm)
        binder.lifecycleOwner = viewLifecycleOwner

        return binder.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        vm.dispose()
    }

}
