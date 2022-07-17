package com.example.androidel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.androidel.databinding.ActivityJoinBinding
import com.example.androidel.databinding.ActivityLoginBinding
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {
    val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        var clickState = false
//        var clickAgainState = false
//
//        binding.btnHide.setOnClickListener {
//            if(clickState) {
//                clickState = false
//                binding.btnHide.setImageResource(R.drawable.ic_hide)
//                binding.editPw.transformationMethod = PasswordTransformationMethod.getInstance()
//                binding.editPw.setSelection(binding.editPw.text.length)
//            } else {
//                clickState = true
//                binding.btnHide.setImageResource(R.drawable.ic_unhide)
//                binding.editPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                binding.editPw.setSelection(binding.editPw.text.length)
//            }
//        }
//
//        binding.btnHideAgain.setOnClickListener {
//            if(clickAgainState) {
//                clickAgainState = false
//                binding.btnHideAgain.setImageResource(R.drawable.ic_hide)
//                binding.editPwCheck.transformationMethod = PasswordTransformationMethod.getInstance()
//                binding.editPwCheck.setSelection(binding.editPwCheck.text.length)
//            } else {
//                clickAgainState = true
//                binding.btnHideAgain.setImageResource(R.drawable.ic_unhide)
//                binding.editPwCheck.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                binding.editPwCheck.setSelection(binding.editPwCheck.text.length)
//            }
//        }
//
//        binding.btnJoin.setOnClickListener {
//            var editId = binding.editId.text.toString()
//            var editPw = binding.editPw.text.toString()
//            var editName = binding.edtName.text.toString()
//            var editNick = binding.edtNick.text.toString()
//            var editNumber = binding.edtNumber.text.toString()
//
//            var list = arrayListOf(editId, editPw, editName, editNick, editNumber)
//
//            for (i in list.indices) {
//                if (list[i].isEmpty()) {
//                    Toast.makeText(applicationContext, "모두 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show()
//                    return@setOnClickListener
//                }
//            }
//
//            if (editPw != binding.editPwCheck.text.toString()) {
//                Toast.makeText(applicationContext, "두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//                binding.editPwCheck.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (!Pattern.matches("^[가-힣]*\$", editName)) {
//                Toast.makeText(applicationContext, "올바른 이름 형식이 아닙니다.", Toast.LENGTH_SHORT).show()
//                binding.edtName.requestFocus()
//                return@setOnClickListener
//            }
//
//            if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", editNumber)) {
//                Toast.makeText(applicationContext, "올바른 전화번호가 아닙니다.", Toast.LENGTH_SHORT).show()
//                binding.edtNumber.requestFocus()
//                return@setOnClickListener
//            }
//
//            User.name = binding.edtName.text.toString()
//            val intent = Intent(applicationContext, BasicActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(0, 0)
//        }
//
//        binding.btnLogin.setOnClickListener {
//            val intent = Intent(applicationContext, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//            overridePendingTransition(0, 0)
//        }

        // 유효성 검사 완료 -> 보류
        binding.btnJoin.setOnClickListener {
            val intent = Intent(applicationContext, BasicActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}