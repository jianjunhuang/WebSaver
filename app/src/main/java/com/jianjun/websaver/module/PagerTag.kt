package com.jianjun.websaver.module

import androidx.annotation.StringDef

public const val TAG_ALL = "All"
public const val TAG_READ = "Read"
public const val TAG_UNREAD = "Unread"

@StringDef(TAG_ALL, TAG_READ, TAG_UNREAD)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
public annotation class PagerTag