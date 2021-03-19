package com.path_studio.githubuser.models

import com.path_studio.githubuser.R
import lecho.lib.hellocharts.model.SubcolumnValue

object ContributionData {

    fun contributionData(): List<SubcolumnValue>{
        //contribution datas are still dummy
        val values: ArrayList<SubcolumnValue> = ArrayList()
        values.add(SubcolumnValue(22f, R.color.red))
        values.add(SubcolumnValue(8f, R.color.blue))
        values.add(SubcolumnValue(12f, R.color.yellow))
        values.add(SubcolumnValue(3f, R.color.purple))

        values.add(SubcolumnValue(19f, R.color.red))
        values.add(SubcolumnValue(20f, R.color.blue))
        values.add(SubcolumnValue(49f, R.color.yellow))
        values.add(SubcolumnValue(11f, R.color.purple))

        return values
    }

}