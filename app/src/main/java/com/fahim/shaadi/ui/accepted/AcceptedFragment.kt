package com.fahim.shaadi.ui.accepted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.setHasFixedSize(true)
        val adapter = DeckRecyclerAdapter(glide = AppModule.injectGlide(requireContext()))
        recyclerview.adapter = adapter

        viewmodel.acceptedProfiles.observe(viewLifecycleOwner) {
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