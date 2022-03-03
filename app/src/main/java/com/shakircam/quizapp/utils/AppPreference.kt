package com.shakircam.quizapp.utils

interface AppPreference {
    companion object{
        const val HIGHEST_SCORE = "highest_score"
    }

    fun getString(key:String):String ?
    fun setString(key:String,value:String)

    fun getInt(key:String):Int ?
    fun setInt(key:String,value:Int)

    fun getFloat(key:String):Float ?
    fun setFloat(key:String,value:Float)

    fun getBoolean(key:String):Boolean ?
    fun setBoolean(key:String,value:Boolean)
}