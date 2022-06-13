package org.d3if4091.kalkulatoramoeba.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.d3if4091.kalkulatoramoeba.R

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.about_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_aboutFragment_to_creatorFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}