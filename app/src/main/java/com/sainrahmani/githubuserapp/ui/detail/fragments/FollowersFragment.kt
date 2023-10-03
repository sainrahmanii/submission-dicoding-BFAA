
package com.sainrahmani.githubuserapp.ui.detail.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sainrahmani.githubuserapp.data.ItemsItem
import com.sainrahmani.githubuserapp.databinding.FragmentFollowersBinding
import com.sainrahmani.githubuserapp.ui.detail.DetailActivity

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val fragmentViewModel : FragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager

        val username = arguments?.getString(DetailActivity.EXTRA_FRAGMENT).toString()

        fragmentViewModel.findFollowers(username)

        fragmentViewModel.followers.observe(viewLifecycleOwner) { userFollowers ->
            setUserFollowers(userFollowers)

        }

        fragmentViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUserFollowers(userFollowers: List<ItemsItem>) {

        val adapter = ListFragmentAdapter(userFollowers)
        binding.rvFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbFollowers.visibility = View.VISIBLE
        } else {
            binding.pbFollowers.visibility = View.INVISIBLE
        }
    }

}