package com.fahim.shaadi.ui.declined

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.shaadi.databinding.FragmentDeclinedBinding
import com.fahim.shaadi.dependencyInjection.AppModule
import com.fahim.shaadi.ui.dashboard.DeckRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeclinedFragment: Fragment() {

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
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        val adapter = DeckRecyclerAdapter(glide = AppModule.injectGlide(requireContext()))
        recyclerview.adapter = adapter

        val textView: TextView = binding.textHome
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        viewModel.declinedProfiles.observe(viewLifecycleOwner) {
            adapter.submitList(it)
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