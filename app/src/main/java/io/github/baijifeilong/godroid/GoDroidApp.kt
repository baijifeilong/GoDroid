package io.github.baijifeilong.godroid

import android.app.Application
import android.app.Notification
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


class GoDroidApp : Application(), AnkoLogger {
    var listener: IListener? = null

    override fun onCreate() {
        super.onCreate()
        UMConfigure.init(
            this,
            "5cd3a15d0cafb2a30a0010d7",
            "UMeng",
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
            }

            override fun onFailure(p0: String?, p1: String?) {
                runOnUiThread {
                    "友盟设备注册失败".apply {
                        info { this }
                        toast(this)
                    }
                }
            }
        })
        pushAgent.messageHandler = object : UmengMessageHandler() {
            override fun getNotification(p0: Context?, p1: UMessage): Notification {
                runOnUiThread {
                    "收到友盟推送消息: ${p1.title}:${p1.text}".apply {
                        info { this }
                        toast(this)
                        listener?.onNotify(p1.title, p1.text)
                    }
                }
                return super.getNotification(p0, p1)
            }
        }
    }

    interface IListener {
        fun onNotify(title: String, content: String)
    }
}