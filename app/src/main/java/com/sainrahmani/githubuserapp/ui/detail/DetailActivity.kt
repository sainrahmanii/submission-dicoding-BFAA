package com.sainrahmani.githubuserapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.sainrahmani.githubuserapp.R
import com.sainrahmani.githubuserapp.data.UserDetailResponse
import com.sainrahmani.githubuserapp.databinding.ActivityDetailBinding
import com.sainrahmani.githubuserapp.ui.detail.fragments.SectionPagerAdapter

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel: DetailMainViewModel by viewModels()

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_FRAGMENT = "extra_fragment"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userLogin = intent.getStringExtra(EXTRA_USER)


        initializeUserDetail(userLogin.toString())
        setupTabs(userLogin.toString())


        // showing the back button in action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = userLogin


    }

    private fun initializeUserDetail(userLogin: String) {
        mainViewModel.findDetail(userLogin)

        mainViewModel.detail.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainViewModel.snackbar.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupTabs(userLogin: String) {
        val usernameBundle = Bundle()
        usernameBundle.putString(EXTRA_FRAGMENT, userLogin)

        val sectionsPagerAdapter = SectionPagerAdapter(this, usernameBundle)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setDetailUser(detailUser: UserDetailResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(detailUser.avatarUrl)
                .into(icProfile)

            tvName.text = (detailUser.name ?: "").toString()
            tvUsername.text = "${detailUser.login}"
            tvFollowers.text = getString(R.string.data_followers, detailUser.followers.toString())
            tvFollowing.text = getString(R.string.data_following, detailUser.following.toString())
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbUserDetail.visibility = View.VISIBLE
        } else {
            binding.pbUserDetail.visibility = View.INVISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}