package com.sw.model.domain.common

import androidx.annotation.StringRes
import com.sw.model.base.redux.Action
import com.sw.model.base.redux.State

/**
 * @author ksu3101
 * @since 2019-10-16
 */
sealed class MessageState : State

object HandledMessageState : MessageState()

data class ShowingGeneralToastState(
        @StringRes val messageResId: Int
) : MessageState()

data class ShowingErrorToastState(
        @StringRes val messageResId: Int,
        val message: String? = null
) : MessageState()

data class ShowingOneButtonDialogState(
        @StringRes val title: Int,
        @StringRes val messageResId: Int,
        val isErrorDialog: Boolean = false
) : MessageState()

data class ShowingReTryActionDialogState(
        @StringRes val title: Int,
        @StringRes val messageResId: Int,
        val retryAction: Action
) : MessageState()