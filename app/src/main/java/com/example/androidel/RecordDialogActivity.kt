package com.example.androidel

import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.databinding.ActivityRecordDialogBinding

class RecordDialogActivity(private val context : AppCompatActivity) {
    private lateinit var binding : ActivityRecordDialogBinding
    private val dlg = Dialog(context)

    fun show() {
        binding = ActivityRecordDialogBinding.inflate(context.layoutInflater)

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        binding.btnOk.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }
}