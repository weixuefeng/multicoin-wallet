package com.explorer.wallettest.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.explorer.wallettest.R
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.entity.UnWrapAccount
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.repository.PreferenceRepository
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.utils.gson
import com.explorer.wallettest.utils.wrap
import kotlinx.android.synthetic.main.wallet_fragment.*

class WalletFragment : Fragment() {

    companion object {
        val TAG = "WalletFragment"
        fun newInstance() = WalletFragment()
    }

    private var unWrapAccount: UnWrapAccount? = null
    private var mLocalStoreKey: LocalStoreKey? = null
    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
        // TODO: Use the ViewModel

        ivWallet.setOnClickListener {
            Router.openWalletManagerActivity(requireContext())
        }
        send.setOnClickListener {
            Router.openTransferAssetActivity(requireContext(), unWrapAccount!!.wrap(), mLocalStoreKey!!)
        }
        receive.setOnClickListener {
            Router.openReceiveActivity(requireContext(), unWrapAccount!!.wrap())
        }
    }

    override fun onResume() {
        super.onResume()
        PreferenceRepository.getInstance().getCurrentAccount().asLiveData().observe(this) {
            if(it != null) {
                unWrapAccount = it
                Logger.d(it?.address, "address")
                chainName.text = "${it?.coin?.name}:${it?.address}"
                topLayout.visibility = View.VISIBLE
            }
        }
        PreferenceRepository.getInstance().getCurrentLocalStoreKey().asLiveData().observe(this) {
            if(it != null) {
                mLocalStoreKey = it
            }
        }
    }
}