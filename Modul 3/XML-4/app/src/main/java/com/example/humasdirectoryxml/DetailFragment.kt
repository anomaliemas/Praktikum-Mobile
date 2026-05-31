package com.example.humasdirectoryxml

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.humasdirectoryxml.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // Inisialisasi ViewModel menggunakan Factory
    private val viewModel: ProkerViewModel by viewModels {
        ProkerViewModelFactory("Ahmad Luthfi Maulana")
    }

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

        // Memantau data list dari ViewModel untuk DetailFragment
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.prokerList.collect { list ->
                    val prokerData = list.find { it.name == prokerName }

                    if (prokerData != null) {
                        binding.imgDetail.setImageResource(prokerData.photo)
                        binding.tvDetailTitle.text = prokerData.name
                        binding.tvDetailDate.text = "Tanggal: ${prokerData.date}"
                        binding.tvDetailPj.text = "PJ: ${prokerData.pj}"
                        binding.tvDetailDesc.text = prokerData.description

                        binding.btnDetailInsta.setOnClickListener {
                            Timber.i("Tombol Explicit Intent ditekan untuk Instagram PJ")
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(prokerData.link)))
                        }

                        binding.btnBack.setOnClickListener {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}