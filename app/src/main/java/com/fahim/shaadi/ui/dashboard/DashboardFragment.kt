package com.fahim.shaadi.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.fahim.bookapptesting.util.Status
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.databinding.FragmentDashboardBinding
import com.fahim.shaadi.dependencyInjection.AppModule
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment(), CardStackListener {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isFirstInstance = true

    private val mProfileList: ArrayList<ProfileModel> = ArrayList()
    private val cardStackView by lazy { binding.cardStackView }
    private val manager by lazy { CardStackLayoutManager(requireContext(), this) }
    private val adapter by lazy { CardStackAdapter(AppModule.injectGlide(requireContext())) }
    private val dashboardViewModel by lazy { ViewModelProvider(this).get(DashboardViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initialize()


        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        dashboardViewModel.localProfileLiveData.observe(viewLifecycleOwner) { profileList ->
            if (profileList != null && profileList.size > 0) {
                textView.visibility = View.GONE
                Log.e("TAG", "localProfileLiveData: $isFirstInstance" + profileList.size)
                Log.e("TAG", "localProfileLiveData: ${Thread.currentThread().name}" + profileList.size)
                mProfileList.clear()
                mProfileList.addAll(profileList)

                adapter.submitList(mProfileList)
                cardStackView.adapter = adapter
                adapter.notifyItemRangeInserted(0, profileList.size)
                /*if (profileList.size == 10 || adapter.itemCount ==0) {
                    adapter.submitList(mProfileList)
                    adapter.notifyItemRangeInserted(0, profileList.size)

                }*/
            } else {
                textView.visibility = View.VISIBLE
                dashboardViewModel.getProfiles()
            }
        }
        dashboardViewModel.profileLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE

                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Error Occured..", Toast.LENGTH_LONG).show()

                }

            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            dashboardViewModel.getProfiles()
        }
        val position = manager.topPosition - 1
        when (direction) {
//            Direction.Left -> dashboardViewModel.declinedProfile(adapter.currentList[position])
//            Direction.Right -> dashboardViewModel.acceptedProfile(adapter.currentList[position])
        }
    }


    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item)
        Log.d("CardStackView", "onCardAppeared: ($position) ")
    }

    override fun onCardDisappeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ")
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(10)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())

        cardStackView.layoutManager = manager
        adapter.submitList(emptyList())
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

}