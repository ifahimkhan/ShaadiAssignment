package com.fahim.shaadi.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.daprlabs.cardstack.SwipeDeck.SwipeEventCallback
import com.fahim.bookapptesting.util.Status
import com.fahim.shaadi.data.database.ProfileModel
import com.fahim.shaadi.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = DeckAdapter(ArrayList(), requireContext())
        _binding!!.swipeDeck.adapter = adapter
        _binding!!.swipeDeck.setEventCallback(object : SwipeEventCallback {
            override fun cardSwipedLeft(position: Int) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(requireContext(), "Card Swiped Left", Toast.LENGTH_SHORT).show()
                dashboardViewModel.declinedProfile(_binding!!.swipeDeck.adapter.getItem(position) as ProfileModel)

            }

            override fun cardSwipedRight(position: Int) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(requireContext(), "Card Swiped Right", Toast.LENGTH_SHORT).show()
                dashboardViewModel.acceptedProfile(_binding!!.swipeDeck.adapter.getItem(position) as ProfileModel)
            }

            override fun cardClicked(position: Int) {
                TODO("Not yet implemented")
            }

            override fun cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(requireContext(), "No more courses present", Toast.LENGTH_SHORT)
                    .show()
            }

            fun cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN")
            }

            fun cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP")
            }
        })


        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        dashboardViewModel.localProfileLiveData.observe(viewLifecycleOwner) { profileList ->
            if (profileList != null && profileList.size > 0) {
                //submitlist
                textView.visibility=View.GONE
                _binding?.let {
                    it.swipeDeck.adapter =
                        DeckAdapter(profileList as ArrayList<ProfileModel>, requireContext())
                }
                Log.e("TAG", "localProfileLiveData: " + profileList.size)
            } else {
                textView.visibility=View.VISIBLE
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
}