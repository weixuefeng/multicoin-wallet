package com.explorer.wallettest.ui.home.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import com.explorer.wallettest.R
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.repository.PreferenceRepository
import com.explorer.wallettest.router.Router
import com.explorer.wallettest.utils.gson
import com.explorer.wallettest.utils.toJson
import kotlinx.android.synthetic.main.wallet_fragment.*
import kotlinx.coroutines.flow.onCompletion

class WalletFragment : Fragment() {

    companion object {
        val TAG = "WalletFragment"
        fun newInstance() = WalletFragment()
    }

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
    }

    override fun onResume() {
        super.onResume()
        PreferenceRepository.getInstance().getCurrentAccount().asLiveData().observe(this) {
            Logger.d(gson.toJson(it), TAG)
            chainName.text = "${it?.coin?.name}:${it?.address}"
        }
    }
}