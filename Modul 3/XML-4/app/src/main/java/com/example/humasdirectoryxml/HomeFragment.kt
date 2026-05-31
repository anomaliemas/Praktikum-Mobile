package com.example.humasdirectoryxml

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.humasdirectoryxml.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Inisialisasi ViewModel menggunakan Factory
    private val viewModel: ProkerViewModel by viewModels {
        ProkerViewModelFactory("Ahmad Luthfi Maulana")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Mengatur LayoutManager untuk kedua RecyclerView
        binding.rvUnggulan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvDaftar.layoutManager = LinearLayoutManager(requireContext())

        // 2. Mengamati data list dari ViewModel untuk ditampilkan ke RecyclerView
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.prokerList.collect { list ->
                    // Memasukkan data ke Program Unggulan (Horizontal)
                    binding.rvUnggulan.adapter = ProkerAdapter(list, true) { name ->
                        viewModel.onProkerClicked(name)
                    }

                    // Memasukkan data ke Daftar Program Kerja (Vertikal)
                    binding.rvDaftar.adapter = ProkerAdapter(list, false) { name ->
                        viewModel.onProkerClicked(name)
                    }
                }
            }
        }

        // 3. Mengamati event navigasi dari ViewModel untuk pindah ke DetailFragment
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToDetail.collect { prokerName ->
                    prokerName?.let {
                        Timber.i("Navigasi ke detail proker: $it")
                        val bundle = Bundle().apply { putString("PROKER_NAME", it) }
                        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)

                        // Reset state navigasi di ViewModel agar tidak memicu navigasi ulang saat rotasi layar
                        viewModel.onDetailNavigated()
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