package com.explorer.wallettest

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.explorer.wallettest.database.LocalStoreKey
import com.explorer.wallettest.database.LocalStoreKeyDB
import com.explorer.wallettest.logger.LogInterceptor
import com.google.protobuf.ByteString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.web3j.crypto.RawTransaction
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.http.HttpService
import org.web3j.utils.Async
import org.web3j.utils.Convert
import org.web3j.utils.Numeric
import wallet.core.java.AnySigner
import wallet.core.jni.CoinType
import wallet.core.jni.HDWallet
import wallet.core.jni.StoredKey
import wallet.core.jni.proto.Ethereum
import wallet.core.jni.proto.NewChain
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.experimental.and


class MainActivity : AppCompatActivity() {
    init {
    }
    val password = "123456".toByteArray()

    val keystore = """
            {"address": "0fb8eeda0139ee6f40d34c031d95d07f92f8e2aa", "crypto": {"cipher": "aes-128-ctr", "cipherparams": {"iv": "84c063999d6b06ecbd5c9cb18d6c9568"}, "ciphertext": "698b2385dafbb017d907a4ffa80d67a41b60bd5593d648ec0854dc78a4e805ec", "kdf": "scrypt", "kdfparams": {"dklen": 32, "n": 262144, "r": 1, "p": 8, "salt": "b57742f877ba736de9496458295a53c7"}, "mac": "3f2ff4db02452b1bd58e5c146325641aeeff041dfbab9eb64ed4488449064820"}, "id": "65648104-9b35-4407-9711-684100cdc94b", "version": 3}
        """.trimIndent()
    private val seedPhrase = "kid urge decide loyal pink sustain hen start endless exhaust couch faculty"
    private val passphrase = ""
    private var mToAddress = "0xfF827d59F973BC57eEc55dce2955e03e6c81dB30"
    private lateinit var web3j: Web3j
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 'Import' a wallet
    }

//    private fun getLocalStoreKey() {
//        GlobalScope.launch {
//            val localkey =
//                LocalStoreKeyDB.getInstance().localStoreKeyDao().getAllLocalStoreKeys()
//                    .get(0)
//            val store = StoredKey.importJSON(localkey.keystore.toByteArray())
//            val wallet = store.wallet(password)
//            store.accountForCoin(CoinType.ETHEREUM, wallet)
//            localkey.keystore = String(store.exportJSON())
//            LocalStoreKeyDB.getInstance().localStoreKeyDao().updateLocalStoreKey(localkey)
//        }
//    }
//
//    private fun addLocalStoreKey() {
//        log(Thread.currentThread().name)
//        GlobalScope.launch {
//            log(Thread.currentThread().name)
//            val storeKey = createStoreKey()
//            val localStore = LocalStoreKey(storeKey.identifier(), storeKey.isMnemonic, String(storeKey.exportJSON()))
//            LocalStoreKeyDB.getInstance(baseContext).localStoreKeyDao().addLocalStoreKey(localStore)
//        }
//
//    }
//
//    private fun createStoreKey() : StoredKey {
//        val password = "123456".toByteArray()
//        val storedKey = StoredKey("store-key", password)
//        return storedKey
//    }
//
//    private fun createMultiWallet() {
//        val storedKey = StoredKey("store-key", password)
//        val wallet = storedKey.wallet(password)
//        log(wallet.mnemonic())
//        val newAccount = storedKey.accountForCoin(CoinType.NEWCHAIN, wallet)
//        log(String(storedKey.exportJSON()))
//        log("account identifier: ${storedKey.identifier()}")
//
//        val ethAccount = storedKey.accountForCoin(CoinType.ETHEREUM, wallet)
//        val btcAccount = storedKey.accountForCoin(CoinType.BITCOIN, wallet)
//        log("address: ${newAccount.address()}, pub: ${newAccount.extendedPublicKey()}")
//        log("address: ${ethAccount.address()}, pub: ${ethAccount.extendedPublicKey()}")
//        log("address: ${btcAccount.address()}, pub: ${btcAccount.extendedPublicKey()}")
//
//        log(String(storedKey.exportJSON()))
//        storedKey.store(filesDir.path + File.pathSeparator + "keystore.json")
//        log("account count: ${storedKey.accountCount()}")
//        log("account identifier: ${storedKey.identifier()}")
//        log("account mnemonic: ${storedKey.decryptMnemonic(password)}")
//        for(i in 0..storedKey.accountCount()) {
//            log(" ${storedKey.account(i)}")
//        }
//    }
//
//    private fun createMnemonic() {
//        val wallet = HDWallet(128, "")
//        log("create wallet")
//        log(wallet.mnemonic())
//    }
//
//    private fun importKeystore() {
//        val store = StoredKey("wallet", "11111".toByteArray())
//        val json = store.exportJSON()
//        log(String(json))
//        log("${store.accountCount()}")
//        val wallet = HDWallet(128, "")
//        //    public static native StoredKey importPrivateKey(byte[] privateKey, String name, byte[] password, CoinType coin);
//        val priv = "0xdbe4616161f04b1b3e848acf8195614e4b85891e0cc23c935aa55d89dc693607"
//        val s2 = StoredKey.importPrivateKey(Numeric.hexStringToByteArray(priv), "NEW WALLET", "111111".toByteArray(), CoinType.NEWCHAIN)
//        val json2 = s2.exportJSON()
//        log(String(json2))
//        log("${s2.accountCount()}")
//    }
//
//    private fun exportWallet() {
//
//    }
//
//
//    private fun ByteArray.toHexString(withPrefix: Boolean = true): String {
//        val stringBuilder = StringBuilder()
//        if(withPrefix) {
//            stringBuilder.append("0x")
//        }
//        for (element in this) {
//            stringBuilder.append(String.format("%02x", element and 0xFF.toByte()))
//        }
//        return stringBuilder.toString()
//    }
//
//    private suspend fun getBalanceSub(address: String): BigInteger {
//        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().balance
//    }
//
//    private suspend fun getTransactionCountSub(address: String): BigInteger {
//        return web3j.ethGetTransactionCount(
//                address, DefaultBlockParameterName.LATEST
//        ).send().transactionCount
//    }
//
//    private suspend fun getPriceSub(address: String): BigInteger {
//        return web3j.ethGasPrice().send().gasPrice
//    }
//
//    private suspend fun getGasLimit(transaction: Transaction): BigInteger {
//        return web3j.ethEstimateGas(transaction).send().amountUsed
//    }
//
//    private suspend fun sendTransaction(signedTransaction: String): String {
//        return web3j.ethSendRawTransaction(signedTransaction).send().transactionHash
//    }
//
//
//
//    private fun BigInteger.toByteString(): ByteString {
//        return ByteString.copyFrom(this.toByteArray())
//    }
//
//    private fun String.hexStringToByteArray() : ByteArray {
//        val HEX_CHARS = "0123456789ABCDEF"
//        val result = ByteArray(length / 2)
//        for (i in 0 until length step 2) {
//            val firstIndex = HEX_CHARS.indexOf(this[i].toUpperCase());
//            val secondIndex = HEX_CHARS.indexOf(this[i + 1].toUpperCase());
//            val octet = firstIndex.shl(4).or(secondIndex)
//            result.set(i.shr(1), octet.toByte())
//        }
//        return result
//    }
//
//    private fun showLog(log: String) {
//        val tv =  TextView(this)
//        tv.text = log
//        println(log)
//    }
//
//    fun sendNew(view: View) {
//        val wallet = HDWallet(seedPhrase, passphrase)
//        showLog("Mnemonic: \n${wallet.mnemonic()}")
//        val priv = wallet.getKeyForCoin(CoinType.NEWCHAIN)
//        showLog("priv: ${Numeric.toHexString(priv.data())}")
//        web3j = Web3j.build(
//                HttpService(
//                        "https://devnet.newchain.cloud.diynova.com/",
//                        OkHttpClient().newBuilder().addInterceptor(LogInterceptor()).build(),
//                        true
//                ), 3 * 1000L, Async.defaultExecutorService()
//        )
//        val defaultAddress = wallet.getAddressForCoin(CoinType.NEWCHAIN)
//
//        GlobalScope.launch {
//            val balance = getBalanceSub(defaultAddress)
//            showLog("balance is: $balance")
//            val transactionCount = getTransactionCountSub(defaultAddress)
//            showLog("nonce: $transactionCount")
//            val mGasPrice = getPriceSub(defaultAddress)
//            showLog("gasPrice: $mGasPrice")
//
//            val value = BigDecimal.valueOf(10)
//            val tx: Transaction = Transaction.createEtherTransaction(
//                    defaultAddress,
//                    transactionCount,
//                    mGasPrice,
//                    BigInteger.ZERO,
//                    mToAddress,
//                    Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()
//            )
//            showLog("transaction: $tx")
//
//            val mGasLimit = getGasLimit(tx)
//            showLog("gasLimit: $mGasLimit")
//
//            val rawTransaction: RawTransaction = RawTransaction.createEtherTransaction(
//                    transactionCount,
//                    mGasPrice,
//                    mGasLimit,
//                    mToAddress,
//                    Convert.toWei(value, Convert.Unit.ETHER).toBigInteger()
//            )
//            showLog("rawTransaction: $rawTransaction")
//            val signInput = NewChain.SigningInput.newBuilder().apply {
//                this.privateKey = ByteString.copyFrom(priv.data())
//                toAddress = mToAddress
//                chainId = ByteString.copyFrom(BigInteger.valueOf(1002).toByteArray())
//                nonce = ByteString.copyFrom(transactionCount.toByteArray())
//                gasPrice = ByteString.copyFrom(mGasPrice.toByteArray())
//                gasLimit = ByteString.copyFrom(mGasLimit.toByteArray())
//                transaction = NewChain.Transaction.newBuilder().apply {
//                    transfer = NewChain.Transaction.Transfer.newBuilder().apply {
//                        amount = ByteString.copyFrom(Convert.toWei(value, Convert.Unit.ETHER).toBigInteger().toByteArray())
//                    }.build()
//                }.build()
//            }
//            val sign = AnySigner.sign(signInput.build(), CoinType.NEWCHAIN, NewChain.SigningOutput.parser())
//            println("signRes: ${Numeric.toHexString(sign.toByteArray())}")
//            val transactionHash = sendTransaction(Numeric.toHexString(sign.encoded.toByteArray()))
//            println("transactionHash: $transactionHash")
//
//        }
//    }
//
//    fun sendETH(view: View) {
//        mToAddress = "0x3DDf056e0ec36c51F6238D701c91aff79E22b00E"
//        val wallet = HDWallet(seedPhrase, passphrase)
//        showLog("Mnemonic: \n${wallet.mnemonic()}")
//        val priv = wallet.getKeyForCoin(CoinType.ETHEREUM)
//        showLog("priv: ${Numeric.toHexString(priv.data())}")
//        web3j = Web3j.build(
//                HttpService(
//                        "https://rinkeby.infura.io/v3/de2723146cea4b719644329ba1c7b047",
//                        OkHttpClient().newBuilder().addInterceptor(LogInterceptor()).build(),
//                        true
//                ), 3 * 1000L, Async.defaultExecutorService()
//        )
//        val defaultAddress = wallet.getAddressForCoin(CoinType.ETHEREUM)
//        println(defaultAddress)
//        GlobalScope.launch {
//            val balance = getBalanceSub(defaultAddress)
//            showLog("balance is: $balance")
//            val transactionCount = getTransactionCountSub(defaultAddress)
//            showLog("nonce: $transactionCount")
//            val mGasPrice = getPriceSub(defaultAddress)
//            showLog("gasPrice: $mGasPrice")
//            showLog("chainId: ${web3j.netVersion().send().netVersion}")
//
//            val value = BigDecimal.valueOf(10)
//            val tx: Transaction = Transaction.createEtherTransaction(
//                    defaultAddress,
//                    transactionCount,
//                    mGasPrice,
//                    BigInteger.ZERO,
//                    mToAddress,
//                    value.toBigInteger()
//            )
//            showLog("transaction: $tx")
//
//            val mGasLimit = getGasLimit(tx)
//            showLog("gasLimit: $mGasLimit")
//
//            val rawTransaction: RawTransaction = RawTransaction.createEtherTransaction(
//                    transactionCount,
//                    mGasPrice,
//                    mGasLimit,
//                    mToAddress,
//                    value.toBigInteger())
//            showLog("rawTransaction: $rawTransaction")
//            val signInput = Ethereum.SigningInput.newBuilder().apply {
//                this.privateKey = ByteString.copyFrom(priv.data())
//                toAddress = mToAddress
//                chainId = ByteString.copyFrom(BigInteger.valueOf(4).toByteArray())
//                nonce = ByteString.copyFrom(transactionCount.toByteArray())
//                gasPrice = ByteString.copyFrom(mGasPrice.toByteArray())
//                gasLimit = ByteString.copyFrom(mGasLimit.toByteArray())
//                transaction = Ethereum.Transaction.newBuilder().apply {
//                    transfer = Ethereum.Transaction.Transfer.newBuilder().apply {
//                        amount = ByteString.copyFrom(value.toBigInteger().toByteArray())
//                    }.build()
//                }.build()
//            }
//            val sign = AnySigner.sign(signInput.build(), CoinType.ETHEREUM, Ethereum.SigningOutput.parser())
//            println("signRes: ${Numeric.toHexString(sign.toByteArray())}")
//            val transactionHash = sendTransaction(Numeric.toHexString(sign.encoded.toByteArray()))
//            println("transactionHash: $transactionHash")
//
//        }
//    }

    private fun log(msg: String) {
        Log.i("WalletNewPay", msg)
    }
}