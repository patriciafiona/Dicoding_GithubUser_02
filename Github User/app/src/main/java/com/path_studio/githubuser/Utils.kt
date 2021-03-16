package com.path_studio.githubuser

object Utils {

    fun convertNumberFormat(num: Int): String{
        var temp: String = ""
        if(num >= 1000){
            temp = String.format("%.1f", num.toDouble()/1000.0) + " K"
        }else if (num >= 1000000){
            temp = String.format("%.1f", num.toDouble()/1000000.0) + " M"
        }else{
            temp = num.toString()
        }
        return temp
    }

    fun checkEmptyValue(temp: String): String{
        return if (temp.isBlank()){
            "-"
        }else{
            temp
        }
    }

}