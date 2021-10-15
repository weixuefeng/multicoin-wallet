package com.explorer.wallettest.ui.wallet.transfer

import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.explorer.wallettest.R
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.entity.GasSetting
import com.explorer.wallettest.entity.TransferPageParams
import com.explorer.wallettest.event.ASSET_DETAIL
import com.explorer.wallettest.event.LiveDataBus
import com.explorer.wallettest.logger.Logger
import com.explorer.wallettest.ui.base.BaseActivity
import com.explorer.wallettest.utils.password
import com.explorer.wallettest.utils.toHexByteArray
import com.explorer.wallettest.utils.toStoreKey
import com.google.protobuf.ByteString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_transfer_asset.*
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.Ethereum
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import wallet.core.java.AnySigner
import wallet.core.jni.Account
import wallet.core.jni.CoinType
import wallet.core.jni.PrivateKey
import wallet.core.jni.StoredKey
import wallet.core.jni.proto.NewChain
import java.math.BigInteger

/**
 * @author weixuefeng@diynova.com
 * @version $
 * @time: 3/25/21--11:40 PM
 * @description
 * @copyright (c) 2021 Newton Foundation. All rights reserved.
 */
class TransferAssetActivity: BaseActivity<TransferViewModel>() {

    override fun contentViewId(): Int = R.layout.activity_transfer_asset

    private val transferViewModel: TransferViewModel by viewModels { TransferViewModelFactory }

    override fun initView() {

    }

    override fun initIntent() {
        LiveDataBus.with<TransferPageParams>(ASSET_DETAIL).observeStick(this, onAccount)
    }

    override fun initViewModel() {
        viewModel = transferViewModel
        viewModel.onGasSetting().observe(this, onGasSetting)
    }

    private lateinit var mAccount: Account
    private lateinit var mLocalStoredKey: LocalStoreKey
    private var mToAddress: String? = null
    private var mAmount: String? = "0"

    private val onGasButtonClick = View.OnClickListener {
        mToAddress = addressInput.text.toString()
        mAmount = valueInput.text.toString()
        // todo: check to address and value
        // todo: check asset, if erc20, get gas price. else others.
        viewModel.getGasSetting(mAccount.coin().name, mAccount.address(), mToAddress)
    }

    private val onGasSetting = Observer<GasSetting> {
        val storedKey = mLocalStoredKey.toStoreKey()
        val mPrivateKey = storedKey.privateKey(mAccount.coin(), password)
        val mChainId = 1002
        // get count
        val signingInput = NewChain.SigningInput.newBuilder()
        signingInput.apply {
            privateKey = ByteString.copyFrom(mPrivateKey.data())
            toAddress = mToAddress
            chainId = mChainId.toHexByteArray()
            nonce = it.nonce.toHexByteArray()
            // txMode not set, Legacy is the default
            gasPrice = it.gasPrice.toHexByteArray()
            gasLimit = it.gasLimit.toHexByteArray()
            transaction = NewChain.Transaction.newBuilder().apply {
                transfer = NewChain.Transaction.Transfer.newBuilder().apply {
                    val value = Convert.toWei(mAmount, Convert.Unit.ETHER)
                    amount = value.toHexByteArray()
                }.build()
            }.build()
        }
        val rawTransaction = AnySigner.sign(signingInput.build(), CoinType.NEWCHAIN, NewChain.SigningOutput.parser())
        val subscribe = viewModel.sendTransaction(
            mAccount.coin(),
            Numeric.toHexString(rawTransaction.encoded.toByteArray())
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success ->
                    {
                        Logger.d(success, "suss")
                    }
                },
                { error ->
                    {
                        Logger.e(error.message!!, "error")
                    }
                }
            )
    }

    private val onAccount = Observer<TransferPageParams> {
        mAccount = it.account
        mLocalStoredKey = it.localStoreKey
        findViewById<Button>(R.id.getGasButton).setOnClickListener (onGasButtonClick)
    }
}