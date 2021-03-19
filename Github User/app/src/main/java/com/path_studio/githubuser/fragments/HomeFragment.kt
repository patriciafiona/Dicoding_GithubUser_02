package com.path_studio.githubuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.path_studio.githubuser.R
import com.path_studio.githubuser.activities.MainActivity
import com.path_studio.githubuser.databinding.FragmentHomeBinding
import com.path_studio.githubuser.databinding.FragmentProfileBinding
import com.path_studio.githubuser.models.ContributionData
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.Column
import lecho.lib.hellocharts.model.ColumnChartData
import lecho.lib.hellocharts.view.ColumnChartView

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        setCharts()

        val myWebView: WebView = binding.contributorImg
        myWebView.loadUrl("https://ghchart.rshah.org/patriciafiona")

        return view
    }

    private fun setCharts(){
        val contrib_chart: ColumnChartView = binding.contributionsChart
        contrib_chart.isInteractive = true
        contrib_chart.zoomType = ZoomType.HORIZONTAL_AND_VERTICAL

        //Onclick Listener
        contrib_chart.setOnClickListener {
            Toast.makeText(activity, (activity as MainActivity).applicationContext.getText(R.string.chartDetail), Toast.LENGTH_LONG).show()
        }

        //In most cased you can call data model methods in builder-pattern-like manner.
        val column: Column = Column().setValues(ContributionData.contributionData())
        column.setHasLabels(true)
        val columns: MutableList<Column> = ArrayList()
        columns.add(column)

        val data = ColumnChartData()
        data.columns = columns
        data.axisXBottom = Axis.generateAxisFromRange(1f,12f, 1f)

        contrib_chart.columnChartData = data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Show Search Bar
        (activity as MainActivity).setSearchBarVisibility(1)
        (activity as MainActivity).clearSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}