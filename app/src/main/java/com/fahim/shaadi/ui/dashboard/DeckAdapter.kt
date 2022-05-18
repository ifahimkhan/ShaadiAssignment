package com.fahim.shaadi.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.fahim.shaadi.R
import com.fahim.shaadi.data.database.ProfileModel


class DeckAdapter(profileModel: ArrayList<ProfileModel>, context: Context) :
    BaseAdapter() {
    // on below line we have created variables
    // for our array list and context.
    private val profileModel: ArrayList<ProfileModel>
    private val context: Context
    override fun getCount(): Int {
        // in get count method we are returning the size of our array list.
        return profileModel.size
    }

    override fun getItem(position: Int): Any {
        // in get item method we are returning the item from our array list.
        return profileModel[position]
    }

    override fun getItemId(position: Int): Long {
        // in get item id we are returning the position.
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // in get view method we are inflating our layout on below line.
        var v = convertView
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        }
        // on below line we are initializing our variables and setting data to our variables.
        (v!!.findViewById<View>(R.id.name) as TextView).setText(profileModel[position].name)
        /*(v.findViewById<View>(R.id.idTVCourseDescription) as TextView).setText(
            profileModel[position].getCourseDescription()
        )
        (v.findViewById<View>(R.id.idTVCourseDuration) as TextView).setText(profileModel[position].getCourseDuration())
        (v.findViewById<View>(R.id.idTVCourseTracks) as TextView).setText(profileModel[position].getCourseTracks())
        (v.findViewById<View>(R.id.idIVCourse) as ImageView).setImageResource(
            profileModel[position].getImgId()
        )*/
        return v
    }

    // on below line we have created constructor for our variables.
    init {
        this.profileModel = profileModel
        this.context = context
    }
}
