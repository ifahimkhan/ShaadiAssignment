package com.fahim.shaadi.ui.declined

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
import com.fahim.shaadi.databinding.FragmentDeclinedBinding
import com.fahim.shaadi.dependencyInjection.AppModule
import com.fahim.shaadi.ui.dashboard.DeckRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeclinedFragment : Fragment() {

    private var _binding: FragmentDeclinedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(DeclinedViewModel::class.java)

        _binding = FragmentDeclinedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerview: RecyclerView = binding.recyclerviewDeclined
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
                    viewModel.updateProfile(selected.copy(isAccepted = -1))
                }

            }
            ).attachToRecyclerView(this)
        }
        val textView: TextView = binding.textHome
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        viewModel.declinedProfiles.observe(viewLifecycleOwner) {
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