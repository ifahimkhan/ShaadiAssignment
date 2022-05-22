package com.fahim.shaadi.ui.accepted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.shaadi.databinding.FragmentAcceptedBinding
import com.fahim.shaadi.dependencyInjection.AppModule
import com.fahim.shaadi.ui.dashboard.DeckRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AcceptedFragment : Fragment() {

    private var _binding: FragmentAcceptedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewmodel =
            ViewModelProvider(this).get(AcceptedViewModel::class.java)

        _binding = FragmentAcceptedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        viewmodel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val recyclerview: RecyclerView = binding.recyclerviewAccepted
        val mAdapter = DeckRecyclerAdapter(glide = AppModule.injectGlide(requireContext()))
        recyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = mAdapter
            ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    val layoutPosition = viewHolder.layoutPosition
                    val selected = mAdapter.currentList[layoutPosition]
                    viewmodel.updateProfile(selected.copy(isAccepted = -1))
                }

            }
            ).attachToRecyclerView(this)
        }



        viewmodel.acceptedProfiles.observe(viewLifecycleOwner) {
            mAdapter.submitList(it)
            if (it.size == 0) {
                textView.visibility = View.VISIBLE
            } else {
                textView.visibility = View.GONE
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}