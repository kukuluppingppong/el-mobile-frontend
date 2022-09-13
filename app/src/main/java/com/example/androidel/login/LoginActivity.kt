package com.example.androidel.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidel.JoinActivity
import com.example.androidel.MyApplication
import com.example.androidel.RecordActivity
import com.example.androidel.SendActivity
import com.example.androidel.databinding.ActivityLoginBinding
import com.example.androidel.login.models.LoginResponse
import com.example.androidel.login.models.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.joinButton.setOnClickListener {
            val intent = Intent(applicationContext, JoinActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnGoogle.setOnClickListener {
            var intent = Intent(applicationContext, SendActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        binding.btnFace.setOnClickListener {
            var intent = Intent(applicationContext, RecordActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }


        binding.btnLogin.setOnClickListener {
            val idStr = binding.editId.text.toString()
            val pwStr = binding.editPw.text.toString()

            val call: Call<LoginResult> = MyApplication.networkService.login(LoginResponse(idStr, pwStr))

            call?.enqueue(object: Callback<LoginResult> {
                override fun onResponse(
                    call: Call<LoginResult>,
                    response: Response<LoginResult>,
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        MyApplication.prefs.token = response.body()!!.data.token
                        Log.e("태그", response.toString())
                        Log.e("태그", MyApplication.prefs.token!!)
                    } else {
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.e("태그", "${t.localizedMessage}")
                }
            })
        }
    }
}