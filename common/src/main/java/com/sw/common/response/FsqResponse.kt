package com.sw.common.response

/**
 * @author burkd
 * @since 2019-11-14
 */
data class FsqResponse<T>(
    val meta: Meta,
    val notifications: List<Notification>,
    val response: T? = null
) {
    fun isError(): Boolean = meta.isError()

    fun isAvailableResponse(): Boolean = this.response != null
}

data class Meta(
    val code: Int,
    val requestId: String,
    val errorType: String? = null,
    val errorDetail: String? = null
) {
    fun isError(): Boolean = code != 200
}

fun Meta.toResponseException(): FsqResponseException =
    FsqResponseException(
        this.code,
        this.requestId,
        this.errorType!!,
        this.errorDetail!!
    )

data class Notification(
    val type: String
    // todo
)