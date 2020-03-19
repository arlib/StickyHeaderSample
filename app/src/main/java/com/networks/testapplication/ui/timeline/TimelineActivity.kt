package com.networks.testapplication.ui.timeline

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jakewharton.threetenabp.AndroidThreeTen
import com.networks.testapplication.R
import com.networks.testapplication.utils.OnRangeStateChangeListener
import com.networks.testapplication.utils.TimelineRange
import com.networks.testapplication.utils.TimelineTime
import kotlinx.android.synthetic.main.activity_timeline.*

class TimelineActivity : AppCompatActivity(), ItemScrollChangeListener{

    private val timelineViewScrollPosition = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        AndroidThreeTen.init(this)


        val ranges = arrayListOf(
            TimelineRange(TimelineTime(3,30), TimelineTime(4,30)),
                    TimelineRange(TimelineTime(11,0), TimelineTime(14,0))
        )

        selectable_timeline_view.setSelectedColor(ContextCompat.getColor(this, R.color.colorAccent))

        selectable_timeline_view.setMaximumSelectableRanges(10)
        selectable_timeline_view.setUnselectableRanges(ranges,false)
        selectable_timeline_view.setOnRangeSelectedListener(object : OnRangeStateChangeListener{
            override fun onRangeSelected(from: TimelineTime, to: TimelineTime) {

                Toast.makeText(this@TimelineActivity,
                    "Range ${selectable_timeline_view.selectedRangeStartTime.hour}:${selectable_timeline_view.selectedRangeStartTime.minute} to ${selectable_timeline_view.selectedRangeEndTime.hour}:${selectable_timeline_view.selectedRangeEndTime.minute} Selected",
                    Toast.LENGTH_LONG).show()

            }

            override fun onRangeDeselected(from: TimelineTime, to: TimelineTime) {

                Toast.makeText(this@TimelineActivity,
                    "Range ${selectable_timeline_view.selectedRangeStartTime?.hour?:"null"}:${selectable_timeline_view.selectedRangeStartTime?.minute?:"null"} to ${selectable_timeline_view.selectedRangeEndTime?.hour?:"null"}:${selectable_timeline_view.selectedRangeEndTime?.minute?:"null"} Selected",
                    Toast.LENGTH_LONG).show()

            }
        })

        select_range_button.setOnClickListener {
            selectable_timeline_view.selectRange(TimelineRange(
                TimelineTime(10,0),
                TimelineTime(11,30)))
        }

        deselect_range_button.setOnClickListener {
            selectable_timeline_view.deselectRange(TimelineRange(
                TimelineTime(10,0),
                TimelineTime(10,30)))
        }


    }

    override fun getScrollPosition(): Pair<LifecycleOwner, LiveData<Int>> {
        return Pair(this,timelineViewScrollPosition)
    }

    override fun onItemScrolled(newPosition: Int) {
        timelineViewScrollPosition.value = newPosition
    }
}
