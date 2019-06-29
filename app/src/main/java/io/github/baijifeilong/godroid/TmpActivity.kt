package io.github.baijifeilong.godroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.JustifyContent
import org.jetbrains.anko.backgroundColorResource

/**
 * Created by BaiJiFeiLong@gmail.com at 2019-06-29 14:03
 */
class TmpActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FlexboxLayout(this).apply {
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.STRETCH
            addView(Button(this@TmpActivity).apply {
                text = "Hello Flex"
                backgroundColorResource = android.R.color.holo_green_light
                layoutParams = FlexboxLayout.LayoutParams(0, 0).apply {
                    flexGrow = 2.0f
                }
            })
            addView(TextView(this@TmpActivity).apply {
                text = "Click Me"
                backgroundColorResource = android.R.color.holo_orange_light
                gravity = Gravity.CENTER
                layoutParams = FlexboxLayout.LayoutParams(0, 0).apply {
                    flexGrow = 1.0f
                }
            })
        })
    }
}