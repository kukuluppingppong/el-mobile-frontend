package com.example.androidel.signUp

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.BasicActivity
import com.example.androidel.MyApplication
import com.example.androidel.R
import com.example.androidel.databinding.ActivityJoinBinding
import com.example.androidel.login.LoginActivity
import com.example.androidel.signUp.model.SignUpBody
import com.example.androidel.signUp.model.SignUpResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var clickState = false
        var clickAgainState = false

        binding.btnHide.setOnClickListener {
            if(clickState) {
                clickState = false
                binding.btnHide.setImageResource(R.drawable.ic_hide)
                binding.editPw.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.editPw.setSelection(binding.editPw.text.length)
            } else {
                clickState = true
                binding.btnHide.setImageResource(R.drawable.ic_unhide)
                binding.editPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.editPw.setSelection(binding.editPw.text.length)
            }
        }

        binding.btnHideAgain.setOnClickListener {
            if(clickAgainState) {
                clickAgainState = false
                binding.btnHideAgain.setImageResource(R.drawable.ic_hide)
                binding.editPwCheck.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.editPwCheck.setSelection(binding.editPwCheck.text.length)
            } else {
                clickAgainState = true
                binding.btnHideAgain.setImageResource(R.drawable.ic_unhide)
                binding.editPwCheck.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.editPwCheck.setSelection(binding.editPwCheck.text.length)
            }
        }
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
        binding.btnLogin.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }

        // 유효성 검사 완료 -> 보류
        binding.btnJoin.setOnClickListener {
            val idStr = binding.editId.text.toString()
            val pwStr = binding.editPw.text.toString()
            val name = binding.edtName.text.toString()
            val phoneNumber = binding.edtNumber.text.toString()

            val call: Call<SignUpResponse> = MyApplication.signUpService.signUp(SignUpBody(idStr, name, pwStr, phoneNumber))
            call?.enqueue(object: Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    var result = response.body()

                    if (result == null) {
                        var jsonObject = JSONObject(response.errorBody()!!.string())
                        val message = jsonObject.getString("message")
                        Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
                    }
                    if (response.isSuccessful) {
                        val intent = Intent(applicationContext, BasicActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.e("태그", t.toString())
                }
            })
        }
    }
}