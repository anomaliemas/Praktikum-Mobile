package com.example.humasdirectoryxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.humasdirectoryxml.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prokerName = arguments?.getString("PROKER_NAME")
        val prokerData = ProkerData.listData.find { it.name == prokerName }

        if (prokerData != null) {
            binding.imgDetail.setImageResource(prokerData.photo)
            binding.tvDetailTitle.text = prokerData.name
            binding.tvDetailDate.text = "Tanggal: ${prokerData.date}"
            binding.tvDetailPj.text = "PJ: ${prokerData.pj}"
            binding.tvDetailDesc.text = prokerData.description

            binding.btnDetailInsta.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(prokerData.link)))
            }

            binding.btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}