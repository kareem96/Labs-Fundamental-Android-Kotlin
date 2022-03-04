package com.kareemdev.livadataapi

open class Event <out T> (private val content: T){

    @Suppress("MemberVisibilityCanPrivate")
    var hasBeenHandled = false
    private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent() : T = content
}