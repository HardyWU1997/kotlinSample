package com.example.kotlinsampie.activity

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsampie.R
import kotlinx.android.synthetic.main.activity_nfc.*
import okhttp3.internal.and
import java.io.IOException
import java.nio.charset.Charset

class NfcActivity : AppCompatActivity() {


    var nfcAdapter: NfcAdapter? = null
    var pendingIntent: PendingIntent? = null

    val TAG = "Receiver"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        //        初始化NfcAdapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        //       如果沒有NfcAdapter，則顯示設備沒有NFC


//       如果沒有NfcAdapter，則顯示設備沒有NFC
        if (nfcAdapter == null) {
            Toast.makeText(this, "NO NFC Capabilities",
                    Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //創建一個PendingIntent對象，以便Android系統可以
        //掃描標籤時，請使用標籤的詳細信息填充該標籤。
        //PendingIntent.getActivity(Context,requestcode(identifier for
        //                           intent),intent,int)
        pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, this.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
    }


    override fun onResume() {
        super.onResume()

        nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, null, null)
    }


    override fun onPause() {
        super.onPause()
        //暫停播放
        if (nfcAdapter != null) {
            nfcAdapter!!.disableForegroundDispatch(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent?.let { resolveIntent(it) }
    }

    private fun resolveIntent(intent: Intent) {
        val action = intent.action
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            val tag = (intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?)!!
            val payload: ByteArray = detectTagData(tag)!!.toByteArray()
        }
    }

    private fun detectTagData(tag: Tag): String? {
        val sb = StringBuilder()
        val id = tag.id
        sb.append("ID (hex): ").append(toHex(id)).append('\n')
        sb.append("ID (reversed hex): ").append(toReversedHex(id)).append('\n')
        sb.append("ID (dec): ").append(toDec(id)).append('\n')
        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n')
        Log.d("NFC:", "ID (hex):" + toHex(id))
        Log.d("NFC:", "ID (reversed hex):" + toReversedHex(id))
        Log.d("NFC:", "ID (dec):" + toDec(id))
        Log.d("NFC:", "ID (reversed dec):" + toReversedDec(id))
        NfcTextView.setText(toReversedDec(id).toString())
        val prefix = "android.nfc.tech."
        sb.append("Technologies: ")
        for (tech in tag.techList) {
            sb.append(tech.substring(prefix.length))
            sb.append(", ")
        }
        sb.delete(sb.length - 2, sb.length)
        for (tech in tag.techList) {
            if (tech == MifareClassic::class.java.name) {
                sb.append('\n')
                var type = "Unknown"
                try {
                    val mifareTag = MifareClassic.get(tag)
                    when (mifareTag.type) {
                        MifareClassic.TYPE_CLASSIC -> type = "Classic"
                        MifareClassic.TYPE_PLUS -> type = "Plus"
                        MifareClassic.TYPE_PRO -> type = "Pro"
                    }
                    sb.append("Mifare Classic type: ")
                    sb.append(type)
                    sb.append('\n')
                    sb.append("Mifare size: ")
                    sb.append(mifareTag.size.toString() + " bytes")
                    sb.append('\n')
                    sb.append("Mifare sectors: ")
                    sb.append(mifareTag.sectorCount)
                    sb.append('\n')
                    sb.append("Mifare blocks: ")
                    sb.append(mifareTag.blockCount)
                } catch (e: Exception) {
                    sb.append("Mifare classic error: " + e.message)
                }
            }
            if (tech == MifareUltralight::class.java.name) {
                sb.append('\n')
                val mifareUlTag = MifareUltralight.get(tag)
                var type = "Unknown"
                when (mifareUlTag.type) {
                    MifareUltralight.TYPE_ULTRALIGHT -> type = "Ultralight"
                    MifareUltralight.TYPE_ULTRALIGHT_C -> type = "Ultralight C"
                }
                sb.append("Mifare Ultralight type: ")
                sb.append(type)
            }
        }
        Log.v(TAG, sb.toString())
        return sb.toString()
    }


    private fun toHex(bytes: ByteArray): String? {
        val sb = java.lang.StringBuilder()
        for (i in bytes.indices.reversed()) {
            val b: Int = bytes[i] and 0xff
            if (b < 0x10) sb.append('0')
            sb.append(Integer.toHexString(b))
            if (i > 0) {
                sb.append(" ")
            }
        }
        return sb.toString()
    }

    private fun toReversedHex(bytes: ByteArray): String? {
        val sb = java.lang.StringBuilder()
        for (i in bytes.indices) {
            if (i > 0) {
                sb.append(" ")
            }
            val b: Int = bytes[i] and 0xff
            if (b < 0x10) sb.append('0')
            sb.append(Integer.toHexString(b))
        }
        return sb.toString()
    }

    private fun toDec(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices) {
            val value: Long = (bytes[i] and 0xff).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    private fun toReversedDec(bytes: ByteArray): Long {
        var result: Long = 0
        var factor: Long = 1
        for (i in bytes.indices.reversed()) {
            val value: Long = (bytes[i] and 0xff).toLong()
            result += value * factor
            factor *= 256L
        }
        return result
    }

    fun writeTag(mifareUlTag: MifareUltralight) {
        try {
            mifareUlTag.connect()
            mifareUlTag.writePage(4, "get ".toByteArray(Charset.forName("US-ASCII")))
            mifareUlTag.writePage(5, "fast".toByteArray(Charset.forName("US-ASCII")))
            mifareUlTag.writePage(6, " NFC".toByteArray(Charset.forName("US-ASCII")))
            mifareUlTag.writePage(7, " now".toByteArray(Charset.forName("US-ASCII")))
        } catch (e: IOException) {
            Log.e(TAG, "IOException while writing MifareUltralight...", e)
        } finally {
            try {
                mifareUlTag.close()
            } catch (e: IOException) {
                Log.e(TAG, "IOException while closing MifareUltralight...", e)
            }
        }
    }

    fun readTag(mifareUlTag: MifareUltralight?): String? {
        try {
            mifareUlTag!!.connect()
            val payload = mifareUlTag.readPages(4)
            return String(payload, Charset.forName("US-ASCII"))
        } catch (e: IOException) {
            Log.e(TAG, "IOException while reading MifareUltralight message...", e)
        } finally {
            if (mifareUlTag != null) {
                try {
                    mifareUlTag.close()
                } catch (e: IOException) {
                    Log.e(TAG, "Error closing tag...", e)
                }
            }
        }
        return null
    }



}