package io.github.baijifeilong.godroid

import android.app.Application
import android.content.Context
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import com.umeng.message.UmengMessageHandler
import com.umeng.message.entity.UMessage
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class GoDroidApp : Application(), AnkoLogger {
    var listener: IListener? = null
    private val filename = "MESSAGES.txt"
    private lateinit var openFileOutput: FileOutputStream

    override fun onCreate() {
        super.onCreate()

        openFileOutput = openFileOutput(filename, Context.MODE_APPEND)
        UMConfigure.init(
            this,
            "5cd3a15d0cafb2a30a0010d7",
            null,
            UMConfigure.DEVICE_TYPE_PHONE,
            "c628a7d37215f6e64192b4cf4c1c1ec6"
        )
        val pushAgent = PushAgent.getInstance(this);
        pushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                runOnUiThread {
                    "友盟设备注册成功, Token: $deviceToken".apply {
                        info { this }
                        toast(this)
                    }
                }
                pushAgent.setAlias("4", "USER_ID") { p0, p1 ->
                    runOnUiThread {
                        info("注册别名的结果: $p0=>$p1")
                        toast("注册别名的结果: $p0=>$p1")
                    }
                }
            }

            override fun onFailure(p0: String?, p1: String?) {
                runOnUiThread {
                    "友盟设备注册失败: $p0 => $p1".apply {
                        info { this }
                        toast(this)
                    }
                }
            }
        })
        pushAgent.messageHandler = object : UmengMessageHandler() {
            override fun handleMessage(p0: Context, p1: UMessage) {
                super.handleMessage(p0, p1)
                val now = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())
                val str = "[$now] ${p1.title} => ${p1.text}"
                openFileOutput.write("$str\n".toByteArray())
                info { "正在写入文件: $str\n" }
                runOnUiThread {
                    "收到友盟推送消息: ${p1.title}:${p1.text}".apply {
                        info { this }
                        toast(this)
                        listener?.onNotify(str)
                    }
                }
            }
        }
    }

    override fun onTerminate() {
        openFileOutput.close()
        super.onTerminate()
    }

    interface IListener {
        fun onNotify(str: String)
    }
}