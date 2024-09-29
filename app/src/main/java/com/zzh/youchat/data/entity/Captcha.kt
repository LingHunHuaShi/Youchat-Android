package com.zzh.youchat.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Captcha(
    val captchaImgUuid: String,
    val captchaImg: String,
)
