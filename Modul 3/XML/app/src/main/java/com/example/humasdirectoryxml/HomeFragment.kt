package com.example.humasdirectoryxml

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.humasdirectoryxml.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUnggulan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val top3Data = ProkerData.listData.take(3)
        binding.rvUnggulan.adapter = ProkerAdapter(top3Data, true) { name ->
            navigateToDetail(name)
        }
        binding.rvUnggulan.addItemDecoration(HorizontalSpaceItemDecoration(12))

        binding.rvDaftar.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDaftar.adapter = ProkerAdapter(ProkerData.listData, false) { name ->
            navigateToDetail(name)
        }
    }

    private fun navigateToDetail(name: String) {
        val bundle = Bundle()
        bundle.putString("PROKER_NAME", name)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class HorizontalSpaceItemDecoration(private val horizontalSpaceDp: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
                outRect.right = (horizontalSpaceDp * view.resources.displayMetrics.density).toInt()
            }
        }
    }
}