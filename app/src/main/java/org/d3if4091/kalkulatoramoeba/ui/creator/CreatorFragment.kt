package org.d3if4091.kalkulatoramoeba.ui.creator

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.GravityInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if4091.kalkulatoramoeba.ApiStatus
import org.d3if4091.kalkulatoramoeba.R
import org.d3if4091.kalkulatoramoeba.databinding.FragmentCreatorBinding

class CreatorFragment : Fragment() {

    private lateinit var binding: FragmentCreatorBinding

    private val viewModel: CreatorViewModel by lazy {
        ViewModelProvider(this).get(CreatorViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(viewLifecycleOwner) {
            binding.tvName.text = it.name
            binding.tvCompany.text = it.company
            binding.tvUsername.text = getString(R.string.username, it.login)
            binding.tvBio.text = getString(R.string.bio, it.bio)
            binding.tvLocation.text = getString(R.string.location, it.location)
            binding.tvGithubUrl.text = getString(R.string.github_url, it.html_url)
        }
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.viewGroup.visibility = View.VISIBLE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }
}