package com.cyl.abdc

import android.os.Bundle
import com.cyl.abdc.databinding.ActivityViewBindingTestBinding
import com.cyl.vb.BaseViewBindingActivity

class ViewBindingTestActivity : BaseViewBindingActivity<ActivityViewBindingTestBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_binding_test)
    }

    override fun initialize() {
        
    }
}