package com.papara.merchant.scene.dashboard

import com.papara.merchant.R
import com.papara.merchant.base.BaseFragment
import com.papara.merchant.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {

    override val layoutId: Int get() = R.layout.fragment_dashboard

    override fun initialize() {
        // no-op
    }
}
